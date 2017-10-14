package scripts;

import org.powerbot.script.*;
import org.powerbot.script.GeItem;
import org.powerbot.script.rt4.*;
import org.powerbot.script.rt4.ClientContext;
import tasks.Walker;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.Callable;

/**
 * Created by Ben on 10/12/2017.
 */

@Script.Manifest(name="WildyLooter", description="Loots wilderness", properties = "author=Yo; topic=999; client=4")


public class WildernessLooter extends PollingScript<ClientContext>  {


    public static final Tile[] pathFromLumbridgeToEdgeVille =  {new Tile(3222, 3218, 0),
            new Tile(3226, 3218, 0), new Tile(3230, 3218, 0),
            new Tile(3234, 3218, 0), new Tile(3234, 3222, 0),
            new Tile(3237, 3225, 0), new Tile(3241, 3225, 0),
            new Tile(3245, 3225, 0), new Tile(3249, 3225, 0),
            new Tile(3253, 3225, 0), new Tile(3257, 3227, 0),
            new Tile(3259, 3231, 0), new Tile(3259, 3235, 0),
            new Tile(3259, 3239, 0), new Tile(3259, 3243, 0),
            new Tile(3257, 3247, 0), new Tile(3255, 3251, 0),
            new Tile(3252, 3254, 0), new Tile(3252, 3258, 0),
            new Tile(3249, 3261, 0), new Tile(3246, 3264, 0),
            new Tile(3244, 3268, 0), new Tile(3242, 3272, 0),
            new Tile(3241, 3276, 0), new Tile(3241, 3280, 0),
            new Tile(3239, 3284, 0), new Tile(3239, 3288, 0),
            new Tile(3239, 3292, 0), new Tile(3239, 3296, 0),
            new Tile(3239, 3300, 0), new Tile(3239, 3304, 0),
            new Tile(3234, 3307, 0), new Tile(3230, 3307, 0),
            new Tile(3226, 3310, 0), new Tile(3223, 3313, 0),
            new Tile(3220, 3316, 0), new Tile(3220, 3320, 0),
            new Tile(3217, 3324, 0), new Tile(3215, 3328, 0),
            new Tile(3214, 3332, 0), new Tile(3211, 3335, 0),
            new Tile(3208, 3338, 0), new Tile(3206, 3342, 0),
            new Tile(3206, 3346, 0), new Tile(3206, 3350, 0),
            new Tile(3204, 3354, 0), new Tile(3202, 3358, 0),
            new Tile(3201, 3362, 0), new Tile(3197, 3361, 0),
            new Tile(3193, 3363, 0), new Tile(3192, 3367, 0),
            new Tile(3190, 3371, 0), new Tile(3187, 3375, 0),
            new Tile(3184, 3378, 0), new Tile(3180, 3381, 0),
            new Tile(3177, 3384, 0), new Tile(3174, 3388, 0),
            new Tile(3171, 3391, 0), new Tile(3170, 3395, 0),
            new Tile(3167, 3398, 0), new Tile(3166, 3402, 0),
            new Tile(3163, 3405, 0), new Tile(3160, 3408, 0),
            new Tile(3157, 3411, 0), new Tile(3153, 3412, 0),
            new Tile(3150, 3415, 0), new Tile(3147, 3418, 0),
            new Tile(3144, 3421, 0), new Tile(3142, 3425, 0),
            new Tile(3139, 3429, 0), new Tile(3136, 3432, 0),
            new Tile(3136, 3436, 0), new Tile(3133, 3439, 0),
            new Tile(3131, 3443, 0), new Tile(3130, 3447, 0),
            new Tile(3130, 3451, 0), new Tile(3130, 3455, 0),
            new Tile(3130, 3459, 0), new Tile(3133, 3463, 0),
            new Tile(3136, 3466, 0), new Tile(3136, 3470, 0),
            new Tile(3136, 3474, 0), new Tile(3136, 3478, 0),
            new Tile(3136, 3482, 0), new Tile(3134, 3486, 0),
            new Tile(3132, 3490, 0), new Tile(3130, 3494, 0),
            new Tile(3130, 3498, 0), new Tile(3130, 3502, 0),
            new Tile(3131, 3506, 0), new Tile(3132, 3510, 0),
            new Tile(3133, 3514, 0), new Tile(3129, 3516, 0),
            new Tile(3125, 3516, 0), new Tile(3121, 3514, 0),
            new Tile(3118, 3511, 0), new Tile(3115, 3508, 0),
            new Tile(3111, 3507, 0), new Tile(3107, 3505, 0),
            new Tile(3104, 3502, 0), new Tile(3101, 3499, 0),
            new Tile(3097, 3497, 0), new Tile(3093, 3495, 0)};


    public static final Tile[] pathToWilderness = {new Tile(3093, 3494, 0),
            new Tile(3093, 3497, 0), new Tile(3093, 3500, 0),
            new Tile(3093, 3503, 0), new Tile(3090, 3506, 0),
            new Tile(3090, 3509, 0), new Tile(3090, 3512, 0),
            new Tile(3089, 3515, 0), new Tile(3089, 3518, 0),
            new Tile(3089, 3522, 0), new Tile(3089, 3525, 0),
            new Tile(3089, 3528, 0), new Tile(3089, 3531, 0),
            new Tile(3089, 3534, 0)};

    public static Area lootArea = new Area(new Tile(3067, 3550, 0),
                                           new Tile(3099, 3525, 0));

    public static ArrayList<Integer> whiteListItems = new ArrayList<Integer>();

    private final Walker walker = new Walker(ctx);
    private int jugId = 1993;


    @Override
    public void start() {
        //creates the TilePath instances to walk from bank to trader, and from trader to bank
        //the arguments are the context instance, and a Tile[] of the tiles that make up the path
        whiteListItems.add(890);
    }

    @Override
    public void poll() {
        //if the user does not have enough gold to buy a fur, stop the script
//        if (ctx.backpack.moneyPouchCount() < KEBAB_COST) {
//            ctx.controller.stop();
//        }

        if(ctx.controller.isStopping()){
            return;
        }

        final WildernessLooter.State state = getState();
        if (state == null) {
            return;
        }
        switch (state) {
            case PICK_UP_ITEMS:
                //Toggling run

                System.out.println("looting start");
                if(ctx.movement.energyLevel() > Random.nextInt(35, 55)){
                    ctx.movement.running(true);
                }
                if(!ctx.players.local().inMotion()) {

                    for(GroundItem g : ctx.groundItems.select().nearest()) {
                        GeItem geLootItem = new org.powerbot.script.rt4.GeItem(g.id());
                        if(geLootItem.price > 50 || whiteListItems.contains(g.id())){
                            g.interact("Take", g.name());
                            break;
                        }
                    }

                }
                System.out.println("looting end");

                break;
            case WALK_TO_WILDERNESS:

                System.out.println("Walk to wilderness start");

                if(ctx.movement.energyLevel() > Random.nextInt(35, 55)){
                    ctx.movement.running(true);
                }

                //traverse() finds the next tile in the path, and returns if it successfully stepped towards that tile

                if (!ctx.players.local().inMotion() || ctx.movement.destination().equals(Tile.NIL) || ctx.movement.destination().distanceTo(ctx.players.local()) < 5) {
                    walker.walkPath(pathToWilderness);
                    Condition.sleep(Random.nextInt(2000, 2500));
                    System.out.println("Walk to wilderness end");
                }

                break;
            case WALK_TO_BANK:
                System.out.println("Walk to bank start");
                if(ctx.movement.energyLevel() > Random.nextInt(35, 55)){
                    ctx.movement.running(true);
                }

                walker.walkPathReverse(pathToWilderness);
                System.out.println("Walk to bank end");

                break;
            case BANK:
                System.out.println("bank start");
                //opened() returns if the bank is currently open
                //if the bank is not open...
                if (!ctx.bank.opened()) {
                    //... open it
                    ctx.bank.open();
                }
                //if the user has something in their inventory that isnt jugs
                else if (ctx.inventory.select().size() != 0) {
                    //click the "deposit inventory" button in the bank
                    ctx.bank.depositInventory();
                    ctx.bank.withdraw(jugId, 5);
                } else {
                    //close the bank when we're done
                    ctx.bank.close();
                }
                System.out.println("bank end");
                break;
        }
    }

    private WildernessLooter.State getState() {
        if (ctx.bank.opened()) {
            return WildernessLooter.State.BANK;
        } else if (ctx.inventory.select().count() < 28) {
            //Looting

            //If im near the wilderness
            if (lootArea.contains(ctx.players.local())) {
                return WildernessLooter.State.PICK_UP_ITEMS;
            } else {
                System.out.println("not in area");
                return WildernessLooter.State.WALK_TO_WILDERNESS;
            }
        } else if (!ctx.bank.inViewport()) {
            return WildernessLooter.State.WALK_TO_BANK;
        } else if (ctx.bank.nearest().tile().distanceTo(ctx.players.local()) < 10) {
            return WildernessLooter.State.BANK;
        }
        return null;
    }

    private enum State {
        WALK_TO_WILDERNESS, PICK_UP_ITEMS, WALK_TO_BANK, BANK
    }


    public static final Font TAHOMA = new Font("Tahoma", Font.PLAIN, 12);

//    @Override public void repaint(Graphics graphics) {
//        final Graphics2D g = (Graphics2D) graphics;
//        g.setFont(TAHOMA);
//
//        final int revenue = dyePrice * dyesMade;
//        final int profit = (dyePrice - dyeCost) * dyesMade;
//        final int profitHr = (int) ((profit * 3600000D) / getRuntime());
//        final int dyesHr = (int) ((dyesMade * 3600000D) / getRuntime());
//
//        g.setColor(Color.WHITE);
//        g.fillRect(5, 5, 220, 80);
//
//        g.setColor(Color.BLACK);
//
//        g.drawString(String.format("Dyes banked: %,d (%,d)", dyesMade, dyesHr), 10, 20);
//        g.drawString(String.format("Profit: %,d (%,d)", profit, profitHr), 10, 40);
//        long seconds = getRuntime() / 1000 %60;
//        long minutes =getRuntime() /60000 %60;
//        long hours =getRuntime() /3600000;
//        g.drawString("Time : " + seconds + "s " + minutes +"m " + hours + "h" , 10, 60 );
//    }

}
