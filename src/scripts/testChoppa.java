package scripts;

import Tasks.*;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.rt4.ClientContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Script.Manifest(name="testChoppa", description="chops trees", properties = "author=Yo; topic=999; client=4")


/**
 * Created by Ben on 9/26/2017.
 */
public class testChoppa extends PollingScript<ClientContext> {

    List<Task> tasks = new ArrayList<Task>();

    @Override
    public void start() {

        tasks.add(new Bank(ctx));
        tasks.add(new Walk(ctx));
        tasks.add(new Chop(ctx));

    }

    @Override
    public void poll() {

        for( Task t : tasks){
            if(t.activate()){
                t.execute();
                break;
            }
        }
        System.out.println("Animation is "+ctx.players.local().animation());
//        ctx.inventory.select();
    }

}
