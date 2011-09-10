import com.rsbuddy.script.methods.Mouse;
import com.rsbuddy.script.methods.Players;
import com.rsbuddy.script.methods.Walking;
import com.rsbuddy.script.util.Random;
import com.rsbuddy.script.util.Timer;
import com.rsbuddy.script.wrappers.Area;
import com.rsbuddy.script.wrappers.Tile;
import org.rsbuddy.tabs.Inventory;

class MiningStrategy extends Strategy {

    private final Area miningArea = new Area(new Tile(2880, 10238), new Tile(2860, 10262));
    private final OreTask oreTask;

    public MiningStrategy(OreTask oreTask) {
        this.oreTask = oreTask;
    }

    @Override
    public boolean isValid() {
        return !Inventory.isFull() && miningArea.contains(Players.getLocal().getLocation());
    }

    @Override
    public void execute() {
        try {
            if (this.playerIsNotBusy(Random.nextInt(1000, 1500))) {
                if (!oreTask.nextRock().isOnScreen() && !Players.getLocal().isMoving()) {
                    Walking.getTileOnMap(oreTask.nextRock().getLocation()).randomize(1, 1).clickOnMap();
                } else {
                    oreTask.nextRock().interact("Mine");
                }
            } else {
                if (Mouse.isPresent()) {
                    Mouse.moveOffScreen();
                }
            }
        } catch (NullPointerException ignored) {

        } catch (ArrayIndexOutOfBoundsException ignored) {

        }
    }

    @Override
    public String toString() {
        return "Mining";
    }

    boolean playerIsNotBusy(long milliseconds) throws NullPointerException {
        Timer t = new Timer(milliseconds);
        while (t.isRunning()) {
            if (!Players.getLocal().isIdle()) {
                return false;
            }
        }
        return true;
    }
}
