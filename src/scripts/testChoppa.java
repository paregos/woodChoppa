package scripts;

import Tasks.*;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.rt4.ClientContext;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

@Script.Manifest(name="testChoppa", description="chops trees", properties = "author=Yo; topic=999; client=4")


/**
 * Created by Ben on 9/26/2017.
 */
public class testChoppa extends PollingScript<ClientContext> {

    List<Task> tasks = new ArrayList<Task>();

    @Override
    public void start() {

        String[] userOptions = {"Bank", "PowerChop"};
        String userChoice = ""+(String)JOptionPane.showInputDialog(null,
           "Bank or PowerChop", "woodChoppa", JOptionPane.PLAIN_MESSAGE,
           null, userOptions, userOptions[1]);

        if(userChoice.equals("Bank")) {
            tasks.add(new Bank(ctx));
        }

        String[] locationOptions = {"Tree South Ge", "Tree West Lumbridge Castle", "Oak Draynor Bank", "Willow " +
                "Draynor Bank", "Willow Lumbridge River (Far)", "Willow Lumbridge River (Close)", "Yew West Lumbridge" +
                "Castle", "Yew East Ge"};
        String locationChoice = ""+(String)JOptionPane.showInputDialog(null,
           "Select a location", "woodChoppa", JOptionPane.PLAIN_MESSAGE,
           null, locationOptions, locationOptions[1]);

        if (locationChoice.equals("Tree South Ge")) {
            tasks.add(new Walk(ctx, ScriptConstants.treeSouthGeBankGe));
            if(userChoice.equals("PowerChop")){tasks.add(new Drop(ctx, ScriptConstants.treeItemId));}
            tasks.add(new Chop(ctx, ScriptConstants.treeIds));
        } else if (locationChoice.equals("Tree West Lumbridge Castle")){
            tasks.add(new Walk(ctx, ScriptConstants.treeWestLumbridgeBankLumbridge));
            if(userChoice.equals("PowerChop")){tasks.add(new Drop(ctx, ScriptConstants.treeItemId));}
            tasks.add(new Chop(ctx, ScriptConstants.treeIds));

        } else if (locationChoice.equals("Oak Draynor Bank")){
            tasks.add(new Walk(ctx, ScriptConstants.oakEastDraynorBankDraynor));
            if(userChoice.equals("PowerChop")){tasks.add(new Drop(ctx, ScriptConstants.oakItemId));}
            tasks.add(new Chop(ctx, ScriptConstants.oakIds));

        } else if (locationChoice.equals("Willow Draynor Bank")){
            tasks.add(new Walk(ctx, ScriptConstants.willowDraynorBankDraynor));
            if(userChoice.equals("PowerChop")){tasks.add(new Drop(ctx, ScriptConstants.willowItemId));}
            tasks.add(new Chop(ctx, ScriptConstants.willowIds));
        } else if (locationChoice.equals("Willow Lumbridge River (Far)")){
            tasks.add(new Walk(ctx, ScriptConstants.willowLumbridgeRiverFarBankLumbridge));
            if(userChoice.equals("PowerChop")){tasks.add(new Drop(ctx, ScriptConstants.willowItemId));}
            tasks.add(new Chop(ctx, ScriptConstants.willowIds));
        } else if (locationChoice.equals("Willow Lumbridge River (Close)")){
            tasks.add(new Walk(ctx, ScriptConstants.willowLumbridgeRiverCloseBankLumbridge));
            if(userChoice.equals("PowerChop")){tasks.add(new Drop(ctx, ScriptConstants.willowItemId));}
            tasks.add(new Chop(ctx, ScriptConstants.willowIds));


        } else if (locationChoice.equals("Yew West Lumbridge Castle")){
            tasks.add(new Walk(ctx, ScriptConstants.yewWestLumbridgeBankLumbridge));
            if(userChoice.equals("PowerChop")){tasks.add(new Drop(ctx, ScriptConstants.yewItemId));}
            tasks.add(new Chop(ctx, ScriptConstants.yewIds));
        } else if (locationChoice.equals("Yew East Ge")){
            tasks.add(new Walk(ctx, ScriptConstants.yewEastGeBankGe));
            if(userChoice.equals("PowerChop")){tasks.add(new Drop(ctx, ScriptConstants.yewItemId));}
            tasks.add(new Chop(ctx, ScriptConstants.yewIds));
        }

        System.out.println("Size of tasks is" + tasks.size());
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
