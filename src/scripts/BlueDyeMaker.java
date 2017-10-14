package scripts;

import org.powerbot.script.*;
import org.powerbot.script.rt4.*;
import org.powerbot.script.rt4.ClientContext;
import tasks.Walker;

import java.awt.*;
import java.util.concurrent.Callable;

/**
 * Created by Ben on 10/7/2017.
 */

@Script.Manifest(name="DyeMaker", description="makes dyes", properties = "author=Yo; topic=999; client=4")


public class BlueDyeMaker extends PollingScript<ClientContext> implements PaintListener {

    public static final Tile[] pathToDye = {new Tile(3093, 3243, 0),
            new Tile(3093, 3246, 0), new Tile(3094, 3249, 0),
            new Tile(3097, 3252, 0), new Tile(3100, 3254, 0),
            new Tile(3102, 3257, 0), new Tile(3103, 3260, 0),
            new Tile(3100, 3262, 0), new Tile(3097, 3262, 0),
            new Tile(3094, 3260, 0), new Tile(3091, 3259, 0),
            new Tile(3088, 3258, 0), new Tile(3085, 3259, 0)};

    private final Walker walker = new Walker(ctx);

    private int aggieId = 4284;
    private int dyeId = 1767;
    private int leafId = 1793;
    private int dyesMade = 0;
    private int dyePrice = 230;
    private int dyeCost = 60;

    @Override
    public void start() {
        //creates the TilePath instances to walk from bank to trader, and from trader to bank
        //the arguments are the context instance, and a Tile[] of the tiles that make up the path

    }

    @Override
    public void poll() {
        //if the user does not have enough gold to buy a fur, stop the script
//        if (ctx.backpack.moneyPouchCount() < KEBAB_COST) {
//            ctx.controller.stop();
//        }

        final State state = getState();
        if (state == null) {
            return;
        }
        switch (state) {
            case PICK_UP_LEAF:
                System.out.println("Pickign up");
                GroundItem leaf = ctx.groundItems.select().id(leafId).nearest().poll();
                leaf.interact("Take", "Leaf");
                Condition.sleep(Random.nextInt(1000, 2500));
                break;
            case WALK_TO_TRADER:

                if(ctx.movement.energyLevel() > Random.nextInt(35, 55)){
                    ctx.movement.running(true);
                }

                //traverse() finds the next tile in the path, and returns if it successfully stepped towards that tile
                walker.walkPath(pathToDye);
                Condition.sleep(Random.nextInt(2000, 2500));
                break;
            case BUY_DYE:
                //chatting() returns if the player is currently chatting with something/someone
                //if the user is not chatting...
                if (!ctx.chat.chatting() || (ctx.chat.chatting() && !ctx.chat.canContinue())) {

                    System.out.println("In here");
                    ctx.inventory.select().id(leafId).poll().click();
                    Condition.wait(new Callable<Boolean>() {
                        @Override
                        public Boolean call() throws Exception {
                            return ctx.inventory.selectedItem().valid();
                        }
                    }, 250, 10);

                    //... talk to the trader
                    final Npc trader = ctx.npcs.select().id(aggieId).nearest().poll();
                    if (trader.interact("Use")) {
                        //wait until the chat interface comes up (prevents spam clicking) for a maximum of 2500ms (250*10)
                        //Condition.wait(condition to wait for, how long to sleep for each iteration, how many iterations to go through)
                        //sleeps until call() returns true, then wait() returns true;
                        //or times out and returns false
                        Condition.wait(new Callable<Boolean>() {
                            @Override
                            public Boolean call() throws Exception {
                                return ctx.chat.chatting();
                            }
                        }, 250, 10);
                    }
                }
                //queryContinue() returns if there is a "continue" button available in the chatting interface
                //if there is a continue button...
                else if (ctx.chat.canContinue()) {
                    //...continue through it
                    //clickContinue() accepts a boolean argument
                    //pass 'true' to use a key (press Enter), pass 'false' to use the mouse
                    ctx.chat.clickContinue(true);
                    //sleep for 350-500ms
                    Condition.sleep(Random.nextInt(350, 500));
                }
                break;
            case WALK_TO_BANK:

                if(ctx.movement.energyLevel() > Random.nextInt(35, 55)){
                    ctx.movement.running(true);
                }

                walker.walkPathReverse(pathToDye);
                break;
            case BANK:
                //opened() returns if the bank is currently open
                //if the bank is not open...
                if (!ctx.bank.opened()) {
                    //... open it
                    ctx.bank.open();
                }
                //if the user has blue dye in their inventory
                else if (!ctx.inventory.select().id(dyeId).isEmpty()) {
                    //click the "deposit inventory" button in the bank
                    ctx.bank.depositAllExcept("Coins", "Woad leaf");
                    dyesMade+= 26;
                } else {
                    //close the bank when we're done
                    ctx.bank.close();
                }
                break;
        }
    }

    private State getState() {

        GroundItem leaf = ctx.groundItems.select().id(leafId).nearest().poll();

        System.out.println(leaf.toString());

        if(leaf.valid()){
            return State.PICK_UP_LEAF;
        }
        else if (ctx.bank.opened()) {
            return State.BANK;
        } else if (ctx.inventory.select().count() < 28) {
            if (!ctx.npcs.select().id(aggieId).within(4).isEmpty()) {
                return State.BUY_DYE;
            } else {
                return State.WALK_TO_TRADER;
            }
        } else if (!ctx.bank.inViewport()) {
            return State.WALK_TO_BANK;
        } else if (ctx.bank.nearest().tile().distanceTo(ctx.players.local()) < 10) {
            return State.BANK;
        }
        return null;
    }

    private enum State {
        WALK_TO_TRADER, BUY_DYE, PICK_UP_LEAF, WALK_TO_BANK, BANK
    }


    public static final Font TAHOMA = new Font("Tahoma", Font.PLAIN, 12);

    @Override public void repaint(Graphics graphics) {
        final Graphics2D g = (Graphics2D) graphics;
        g.setFont(TAHOMA);

        final int revenue = dyePrice * dyesMade;
        final int profit = (dyePrice - dyeCost) * dyesMade;
        final int profitHr = (int) ((profit * 3600000D) / getRuntime());
        final int dyesHr = (int) ((dyesMade * 3600000D) / getRuntime());

        g.setColor(Color.WHITE);
        g.fillRect(5, 5, 220, 80);

        g.setColor(Color.BLACK);

        g.drawString(String.format("Dyes banked: %,d (%,d)", dyesMade, dyesHr), 10, 20);
        g.drawString(String.format("Profit: %,d (%,d)", profit, profitHr), 10, 40);
        long seconds = getRuntime() / 1000 %60;
        long minutes =getRuntime() /60000 %60;
        long hours =getRuntime() /3600000;
        g.drawString("Time : " + seconds + "s " + minutes +"m " + hours + "h" , 10, 60 );
    }
}
