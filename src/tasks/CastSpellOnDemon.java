package tasks;

import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;
import org.powerbot.script.rt4.Npc;
import scripts.WoodChoppa;

import java.util.concurrent.Callable;

/**
 * Created by Ben on 10/7/2017.
 */
public class CastSpellOnDemon extends Task {

    private int demonId = 2005;

    public CastSpellOnDemon(ClientContext ctx) {
        super(ctx);
    }

    @Override public boolean activate() {

        return ((ctx.players.local().animation() == -1 && !ctx.players.local().inCombat()) && !ctx.npcs.select().id
                (demonId).isEmpty());
    }

    @Override public void execute() {

        Npc demon = ctx.npcs.select().id(demonId).nearest().poll();

        System.out.println("Trying to");
        if(!ctx.players.local().inCombat()) {
            if (demon.inViewport()) {
                demon.interact("Attack");
                Condition.wait(new Callable<Boolean>() {
                    @Override public Boolean call() throws Exception {
                        return ctx.players.local().inCombat();
                    }
                }, 800, 20);

            } else {
                ctx.camera.turnTo(demon);
            }
        }

    }
}
