import com.rsbuddy.script.methods.Widgets;
import com.rsbuddy.script.task.Task;
import com.rsbuddy.script.util.Random;
import com.rsbuddy.script.util.Timer;
import com.rsbuddy.script.wrappers.Widget;
import org.rsbuddy.tabs.Inventory;
import org.rsbuddy.widgets.Bank;


class BankingStrategy extends Strategy {
    @Override
    public boolean isValid() {
        return Bank.isOpen();
    }

    @Override
    public void execute() {
        int inventoryCount = Inventory.getCount();
        if (Inventory.isFull()) {
            if (this.waitForBankWidget(Random.nextInt(2000, 2500))) {
                Bank.depositAll();
                this.waitForInventoryChange(inventoryCount);
            }
        }
    }

    void waitForInventoryChange(int count) {
        int ms = Random.nextInt(500, 2000);
        for (int i = 0; i < ms; i += 20) {
            if (Inventory.getCount() != count) {
                break;
            }
            Task.sleep(20);
        }
    }

    @Override
    public String toString() {
        return "Banking";
    }


    boolean waitForBankWidget(long milliseconds) {
        Timer t = new Timer(milliseconds);
        Widget w = Widgets.get(org.rsbuddy.widgets.Bank.WIDGET);
        while (t.isRunning()) {
            if (w != null && w.isValid()) {
                return true;
            }
        }
        return false;
    }
}
