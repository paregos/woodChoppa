package Tasks;

import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Item;

import java.util.concurrent.Callable;

/**
 * Created by Ben on 9/26/2017.
 */
public class Drop extends Task{

    private int[] logId = {1511};

    public Drop(ClientContext ctx) {
        super(ctx);
    }

    @Override public boolean activate() {
        return ctx.inventory.select().count() == 28 && ctx.players.local().animation() == -1;
    }

    @Override public void execute() {
        System.out.println("Should be dropping");

        for(Item i : ctx.inventory.select().id(logId)){

            if(ctx.controller.isStopping()){
                break;
            }

            final int logsInInventory = ctx.inventory.select().id(logId).count();
            i.interact("Drop", "Logs");
            Condition.wait(new Callable<Boolean>() {
                @Override public Boolean call() throws Exception {
                    return ctx.inventory.select().id(logId).count()<logsInInventory;
                }
            }, 25, 20);

        }

    }
}
