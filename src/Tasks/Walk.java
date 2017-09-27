package Tasks;

import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;

public class Walk extends Task {

    public static final Tile[] pathToTree = {new Tile(3163, 3486, 0),
            new Tile(3163, 3482, 0), new Tile(3163, 3478, 0),
            new Tile(3163, 3474, 0), new Tile(3163, 3470, 0),
            new Tile(3163, 3466, 0), new Tile(3163, 3462, 0),
            new Tile(3159, 3462, 0), new Tile(3156, 3459, 0)};

    private final Walker walker = new Walker(ctx);

    public Walk(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        return ctx.inventory.select().count() == 28 || (ctx.inventory.select().count() == 0 && pathToTree[8].distanceTo(ctx.players.local()) > 6);
    }

    @Override
    public void execute() {

        if (!ctx.players.local().inMotion() || ctx.movement.destination().equals(Tile.NIL) || ctx.movement.destination().distanceTo(ctx.players.local()) < 5) {
            if(ctx.inventory.select().count() == 28) {
                walker.walkPathReverse(pathToTree);
            } else {
                walker.walkPath(pathToTree);
            }
        }

    }
}
