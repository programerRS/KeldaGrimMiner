import com.rsbuddy.script.methods.Menu;
import com.rsbuddy.script.methods.Objects;
import com.rsbuddy.script.methods.Players;
import com.rsbuddy.script.methods.Walking;
import org.rsbuddy.tabs.Inventory;
import org.rsbuddy.widgets.Bank;

class WalkToBank extends Strategy implements Paths {
    @Override
    public boolean isValid() {
        return !Bank.isOpen() && Inventory.isFull();
    }

    @Override
    public void execute() {
        if (Objects.getNearest(6084) != null) {
            try {
                if (!Objects.getNearest(6084).isOnScreen()) {
                    Walking.getTileOnMap(Objects.getNearest(6084).getLocation()).randomize(1, 1).clickOnMap();
                } else {
                    if (!Players.getLocal().isMoving()) {
                        Objects.getNearest(6084).interact("Use-quickly");
                    }
                }
            } catch (ArrayIndexOutOfBoundsException ignored) {
                if (Menu.isOpen()) Menu.close();
            } catch (NullPointerException ignored) {
            }
        } else {
            pathToBank.randomize(2, 2).traverse();
        }
    }

    @Override
    public String toString() {
        return "Walking to bank";
    }


}
