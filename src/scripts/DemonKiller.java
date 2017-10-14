package scripts;

import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.rt4.ClientContext;
import tasks.CastSpellOnDemon;
import tasks.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ben on 10/7/2017.
 */

@Script.Manifest(name="DemonKiller", description="kills demons", properties = "author=Yo; topic=999; client=4")

public class DemonKiller extends PollingScript<ClientContext> {

    private List<Task> tasks = new ArrayList<Task>();

    @Override
    public void start() {

    tasks.add(new CastSpellOnDemon(ctx));

    }

    @Override public void poll() {

        for( Task t : tasks){
            if(t.activate()){
                t.execute();
                break;
            }
        }

    }
}
