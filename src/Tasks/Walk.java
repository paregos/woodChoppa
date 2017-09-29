package Tasks;

import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;

public class Walk extends Task {

    private static Tile[] pathToBank = {};
    private final Walker walker = new Walker(ctx);

    public Walk(ClientContext ctx) {
        super(ctx);
    }

    public Walk(ClientContext ctx, Tile[] path) {
        super(ctx);
        this.pathToBank = path;
    }

    @Override
    public boolean activate() {
        return ctx.inventory.select().count() == 28 || (ctx.inventory.select().count() < 28 && pathToBank[0]
                .distanceTo(ctx.players.local()) > 6);
    }

    @Override
    public void execute() {



        if (!ctx.players.local().inMotion() || ctx.movement.destination().equals(Tile.NIL) || ctx.movement.destination().distanceTo(ctx.players.local()) < 5) {
            if(ctx.inventory.select().count() == 28) {
                System.out.println("Walking to Bank");
                walker.walkPath(pathToBank);
            } else {
                System.out.println("Walking to trees");
                walker.walkPathReverse(pathToBank);
            }
        }

    }
}
