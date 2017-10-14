package scripts;

import org.powerbot.script.PaintListener;
import org.powerbot.script.rt4.Constants;
import tasks.*;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.rt4.ClientContext;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Script.Manifest(name="WoodChoppa", description="chops trees", properties = "author=Yo; topic=999; client=4")


/**
 * Created by Ben on 9/26/2017.
 */
public class WoodChoppa extends PollingScript<ClientContext> implements PaintListener {

    private List<Task> tasks = new ArrayList<Task>();
    private int startExp = 0;
    public static String currentTask = "";

    @Override
    public void start() {

        startExp = ctx.skills.experience(Constants.SKILLS_WOODCUTTING);

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
            if(userChoice.equals("Bank")){tasks.add(new Walk(ctx, ScriptConstants.treeSouthGeBankGe));}
            if(userChoice.equals("PowerChop")){tasks.add(new Drop(ctx, ScriptConstants.treeItemId));}
            tasks.add(new Chop(ctx, ScriptConstants.treeIds));
        } else if (locationChoice.equals("Tree West Lumbridge Castle")){
            if(userChoice.equals("Bank")){tasks.add(new Walk(ctx, ScriptConstants.treeWestLumbridgeBankLumbridge));}
            if(userChoice.equals("PowerChop")){tasks.add(new Drop(ctx, ScriptConstants.treeItemId));}
            tasks.add(new Chop(ctx, ScriptConstants.treeIds));

        } else if (locationChoice.equals("Oak Draynor Bank")){
            if(userChoice.equals("Bank")){tasks.add(new Walk(ctx, ScriptConstants.oakEastDraynorBankDraynor));}
            if(userChoice.equals("PowerChop")){tasks.add(new Drop(ctx, ScriptConstants.oakItemId));}
            tasks.add(new Chop(ctx, ScriptConstants.oakIds));

        } else if (locationChoice.equals("Willow Draynor Bank")){
            if(userChoice.equals("Bank")){tasks.add(new Walk(ctx, ScriptConstants.willowDraynorBankDraynor));}
            if(userChoice.equals("PowerChop")){tasks.add(new Drop(ctx, ScriptConstants.willowItemId));}
            tasks.add(new Chop(ctx, ScriptConstants.willowIds));
        } else if (locationChoice.equals("Willow Lumbridge River (Far)")){
            if(userChoice.equals("Bank")){tasks.add(new Walk(ctx, ScriptConstants
                    .willowLumbridgeRiverFarBankLumbridge));}
            if(userChoice.equals("PowerChop")){tasks.add(new Drop(ctx, ScriptConstants.willowItemId));}
            tasks.add(new Chop(ctx, ScriptConstants.willowIds));
        } else if (locationChoice.equals("Willow Lumbridge River (Close)")){
            if(userChoice.equals("Bank")){tasks.add(new Walk(ctx, ScriptConstants
                    .willowLumbridgeRiverCloseBankLumbridge));}
            if(userChoice.equals("PowerChop")){tasks.add(new Drop(ctx, ScriptConstants.willowItemId));}
            tasks.add(new Chop(ctx, ScriptConstants.willowIds));


        } else if (locationChoice.equals("Yew West Lumbridge Castle")){
            if(userChoice.equals("Bank")){tasks.add(new Walk(ctx, ScriptConstants.yewWestLumbridgeBankLumbridge));}
            if(userChoice.equals("PowerChop")){tasks.add(new Drop(ctx, ScriptConstants.yewItemId));}
            tasks.add(new Chop(ctx, ScriptConstants.yewIds));
        } else if (locationChoice.equals("Yew East Ge")){
            if(userChoice.equals("Bank")){tasks.add(new Walk(ctx, ScriptConstants.yewEastGeBankGe));}
            if(userChoice.equals("PowerChop")){tasks.add(new Drop(ctx, ScriptConstants.yewItemId));}
            tasks.add(new Chop(ctx, ScriptConstants.yewIds));
        }



        System.out.println("Size of tasks is" + tasks.size());
    }

    @Override
    public void poll() {
        if(startExp == 0){
            startExp = ctx.skills.experience(Constants.SKILLS_WOODCUTTING);
        }

        for( Task t : tasks){
            if(t.activate()){
                t.execute();
                break;
            }
        }
        System.out.println("Animation is "+ctx.players.local().animation());
//        ctx.inventory.select();
    }

    @Override
    public void repaint(Graphics graphics) {
        long milliseconds = this.getTotalRuntime();
        long seconds = (milliseconds / 1000) % 60;
        long minutes = (milliseconds / (1000*60) % 60);
        long hours = (milliseconds / (1000 * 60 * 60)) % 24;

        int expGained = ctx.skills.experience(Constants.SKILLS_WOODCUTTING)-startExp;

        Graphics2D g = (Graphics2D)graphics;

        g.setColor(new Color(163, 206, 242, 243));
        g.fillRect(0, 0, 230, 120);

        g.setColor(new Color(37, 37, 41));
        g.drawRect(0, 0, 230, 120);

        g.drawString("WoodChoppa", 20, 20);
        g.drawString("Running: " + String.format("%02d:%02d:%02d", hours, minutes, seconds), 20, 40);
        g.drawString("Exp/Hour " + ((int)(expGained * (3600000D / milliseconds))), 20, 60);
        g.drawString("Current task: "+currentTask, 20, 80);
        g.drawString("Version: 0.1", 20, 100);

    }

    public static void setTask(String task){
        currentTask = task;
    }

}
