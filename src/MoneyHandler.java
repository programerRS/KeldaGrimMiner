import com.rsbuddy.script.methods.Players;
import com.rsbuddy.script.task.LoopTask;
import com.rsbuddy.script.wrappers.Item;
import org.rsbuddy.net.GeItem;
import org.rsbuddy.tabs.Inventory;

import java.util.logging.Logger;

class MoneyHandler extends LoopTask implements Paths {

    private final int adamantOreGuidedPrice;
    private final int runiteOreGuidedPrice;
    private final int goldOreGuidedPrice;
    private final int sapphireGuidedPrice;
    private final int emeraldGuidedPrice;
    private final int rubyGuidedPrice;
    private final int diamondGuidedPrice;
    private int moneyMade = 0;
    private int totalMoneyMade = 0;

    MoneyHandler() {
        Logger log = Logger.getLogger(Main.class.getName());
        log.warning("Loading prices...");
        adamantOreGuidedPrice = GeItem.lookup(449).getGuidePrice();
        runiteOreGuidedPrice = GeItem.lookup(451).getGuidePrice();
        goldOreGuidedPrice = GeItem.lookup(444).getGuidePrice();
        sapphireGuidedPrice = GeItem.lookup(1623).getGuidePrice();
        emeraldGuidedPrice = GeItem.lookup(1621).getGuidePrice();
        rubyGuidedPrice = GeItem.lookup(1619).getGuidePrice();
        diamondGuidedPrice = GeItem.lookup(1617).getGuidePrice();
        log.info("Runite ore: " + runiteOreGuidedPrice);
        log.info("Adamant ore: " + adamantOreGuidedPrice);
        log.info("Gold ore: " + goldOreGuidedPrice);
        log.info("Uncut sapphire: " + sapphireGuidedPrice);
        log.info("Uncut emerald: " + emeraldGuidedPrice);
        log.info("Uncut ruby: " + rubyGuidedPrice);
        log.info("Uncut diamond: " + diamondGuidedPrice);
    }


    @Override
    public int loop() {
        if (Paths.miningArea.contains(Players.getLocal().getLocation())) {
            moneyMade = calculateMoneyMade();
        } else {
            totalMoneyMade += moneyMade;
            moneyMade = 0;
        }
        return 0;
    }

    public int getTotalMoneyMade() {
        return this.totalMoneyMade;
    }

    private int calculateMoneyMade() {
        int inventoryValue = 0;
        for (Item item : Inventory.getItems()) {
            switch (item.getId()) {
                case 449:
                    inventoryValue += adamantOreGuidedPrice;
                    break;
                case 451:
                    inventoryValue += runiteOreGuidedPrice;
                    break;
                case 444:
                    inventoryValue += goldOreGuidedPrice;
                    break;
                case 1623:
                    inventoryValue += sapphireGuidedPrice;
                    break;
                case 1621:
                    inventoryValue += emeraldGuidedPrice;
                    break;
                case 1619:
                    inventoryValue += rubyGuidedPrice;
                    break;
                case 1617:
                    inventoryValue += diamondGuidedPrice;
                    break;
            }
        }
        return inventoryValue;
    }
}
