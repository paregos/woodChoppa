package tasks;

import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;
import scripts.WoodChoppa;

import java.util.concurrent.Callable;

/**
 * Created by Ben on 9/26/2017.
 */
public class Chop extends Task{

    private int[] treeId = {};

    public Chop(ClientContext ctx) {
        super(ctx);
    }

    public Chop(ClientContext ctx, int[] treeIds) {
        super(ctx);
        treeId = treeIds;
    }

    @Override public boolean activate() {
        return (ctx.inventory.select().count()<28 && ctx.players.local().animation() == -1) &&
        !ctx.objects.select().id(treeId).select().isEmpty();
    }

    @Override public void execute() {

        GameObject tree = ctx.objects.select().id(treeId).nearest().poll();

        WoodChoppa.setTask("Chopping trees");
        System.out.println("Trying to chop");
        if(tree.inViewport()){
            tree.interact("Chop");
            Condition.wait(new Callable<Boolean>() {
                @Override public Boolean call() throws Exception {
                    return ctx.players.local().animation() != -1;
                }
            }, 200, 10);

        }else{
            ctx.movement.step(tree);
            ctx.camera.turnTo(tree);
        }

    }
}
