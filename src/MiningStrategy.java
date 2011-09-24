import com.rsbuddy.event.events.MessageEvent;
import com.rsbuddy.event.listeners.MessageListener;
import com.rsbuddy.script.methods.Mouse;
import com.rsbuddy.script.methods.Players;
import com.rsbuddy.script.methods.Walking;
import com.rsbuddy.script.util.Random;
import com.rsbuddy.script.util.Timer;
import com.rsbuddy.script.wrappers.Area;
import com.rsbuddy.script.wrappers.GameObject;
import com.rsbuddy.script.wrappers.Tile;
import org.rsbuddy.tabs.Inventory;

class MiningStrategy extends Strategy implements MessageListener {

    private final Area miningArea = new Area(new Tile(2880, 10238), new Tile(2860, 10262));
    private final RockTask rockTask;
    private GameObject currentRock;

    public MiningStrategy(RockTask rockTask) {
        this.rockTask = rockTask;
    }

    @Override
    public boolean isValid() {
        return !Inventory.isFull() && miningArea.contains(Players.getLocal().getLocation());
    }

    @Override
    public void execute() {
        try {
            if (currentRock == null) {
                currentRock = rockTask.nextRock();
                if (!currentRock.isOnScreen() && !Players.getLocal().isMoving()) {
                    Walking.getTileOnMap(currentRock.getLocation()).randomize(1, 1).clickOnMap();
                } else {
                    currentRock.interact("Mine");
                }
            } else {
                if (playerIsNotBusy(Random.nextInt(300, 500))) {
                    currentRock = null;
                } else {
                    if (Mouse.isPresent()) {
                        Mouse.moveOffScreen();
                    }
                }
            }
        } catch (NullPointerException ignored) {
        }
    }

    @Override
    public String toString() {
        return "Mining";
    }

    public void resetCurrentRock() {
        currentRock = null;
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

    public void messageReceived(MessageEvent messageEvent) {
        if (messageEvent.getMessage().contains("You manage to mine some")
                || messageEvent.getMessage().contains("There is no ore currently available in this rock.")) {
            currentRock = null;
        }
    }
}
