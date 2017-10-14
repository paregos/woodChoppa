package tasks;

import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;
import scripts.WoodChoppa;

import java.util.concurrent.Callable;

/**
 * Created by Ben on 9/27/2017.
 */
public class Bank extends Task {


    public Bank(ClientContext ctx) {
        super(ctx);
    }

    @Override public boolean activate() {
        return ctx.inventory.select().count() == 28 && ctx.bank.nearest().tile().distanceTo(ctx.players.local())<6  ;
    }

    @Override public void execute() {

        WoodChoppa.setTask("Banking");
        if(ctx.bank.opened()){
            if(ctx.bank.depositAllExcept("axe")){
                Condition.wait(new Callable<Boolean>() {
                    @Override public Boolean call() throws Exception {
                        return ctx.inventory.select().count() != 28;
                    }
                }, 220, 15);
            }
        }else{
            if(ctx.bank.inViewport()) {
                if(ctx.bank.open()){
                    Condition.wait(new Callable<Boolean>() {
                        @Override public Boolean call() throws Exception {
                            return ctx.bank.opened();
                        }
                    }, 200, 20);
                }
            }else{
                ctx.camera.turnTo(ctx.bank.nearest());
            }
        }
    }
}
