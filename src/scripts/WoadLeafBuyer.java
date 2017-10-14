package scripts;

import org.powerbot.script.*;
import org.powerbot.script.rt4.ClientContext;
import tasks.BuyLeafs;
import tasks.CastSpellOnDemon;
import tasks.Task;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ben on 10/7/2017.
 */

@Script.Manifest(name="WoadLeafBuyer", description="Buys woad leafs", properties = "author=Yo; topic=999; client=4")

public class WoadLeafBuyer extends PollingScript<ClientContext> implements MessageListener, PaintListener {

    private List<Task> tasks = new ArrayList<Task>();
    public static final int LEAF = 1793;
    private int leavesBought = 0;
    private int leafPrice = 40;
    private int leafCost = 10;

    @Override
    public void start() {

        tasks.add(new BuyLeafs(ctx));
    }


    @Override public void poll() {

        for( Task t : tasks){
            if(t.activate()){
                t.execute();
                break;
            }
        }

    }


    @Override
    public void messaged(MessageEvent e) {
        final String msg = e.text().toLowerCase();
        //when we receive a message that says "20 coins have been removed..."
        if (msg.equals("wyson gives you a pair of woad leaves.")) {
            //that means we bought a kebab; increment the count.
            leavesBought+=2;
        }
    }

    public static final Font TAHOMA = new Font("Tahoma", Font.PLAIN, 12);

    @Override public void repaint(Graphics graphics) {

        final Graphics2D g = (Graphics2D) graphics;
        g.setFont(TAHOMA);

        final int profit = (leafPrice - leafCost) * leavesBought;
        final int profitHr = (int) ((profit * 3600000D) / getRuntime());
        final int leafHr = (int) ((leavesBought * 3600000D) / getRuntime());

        g.setColor(Color.WHITE);
        g.fillRect(5, 5, 220, 80);

        g.setColor(Color.BLACK);

        g.drawString(String.format("Woad Leaves: %,d (%,d)", leavesBought, leafHr), 10, 20);
        g.drawString(String.format("Profit: %,d (%,d)", profit, profitHr), 10, 40);
        long seconds = getRuntime() / 1000 %60;
        long minutes =getRuntime() /60000 %60;
        long hours =getRuntime() /3600000;
        g.drawString("Time : " + seconds + "s " + minutes +"m " + hours + "h" , 10, 60 );

    }
}
