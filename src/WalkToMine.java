import com.rsbuddy.script.methods.Players;
import org.rsbuddy.tabs.Inventory;

class WalkToMine extends Strategy implements Paths {

    @Override
    public boolean isValid() {

        return !miningArea.contains(Players.getLocal().getLocation()) && !Inventory.isFull();
    }

    @Override
    public void execute() {
        pathToMine.traverse();
    }

    @Override
    public String toString() {
        return "Walking to mine";
    }
}
