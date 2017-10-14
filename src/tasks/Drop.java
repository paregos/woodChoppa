package tasks;

import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Item;
import scripts.WoodChoppa;

import java.util.concurrent.Callable;

/**
 * Created by Ben on 9/26/2017.
 */
public class Drop extends Task{

    private int treeLogId = 0;


    public Drop(ClientContext ctx) {
        super(ctx);
    }

    public Drop(ClientContext ctx, int treeLogIds) {
        super(ctx);
        treeLogId = treeLogIds;
    }

    @Override public boolean activate() {
        return ctx.inventory.select().count() == 28 && ctx.players.local().animation() == -1;
    }

    @Override public void execute() {
        WoodChoppa.setTask("Dropping");
        System.out.println("Should be dropping");

        for(Item i : ctx.inventory.select().id(treeLogId)){

            if(ctx.controller.isStopping()){
                break;
            }

            final int logsInInventory = ctx.inventory.select().id(treeLogId).count();
            i.interact("Drop", "Logs");
            Condition.wait(new Callable<Boolean>() {
                @Override public Boolean call() throws Exception {
                    return ctx.inventory.select().id(treeLogId).count()<logsInInventory;
                }
            }, 25, 20);

        }

    }
}
