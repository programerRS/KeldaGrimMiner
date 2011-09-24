import com.rsbuddy.event.events.MessageEvent;
import com.rsbuddy.event.listeners.MessageListener;
import com.rsbuddy.event.listeners.PaintListener;
import com.rsbuddy.script.ActiveScript;
import com.rsbuddy.script.Manifest;
import com.rsbuddy.script.util.Random;
import com.rsbuddy.script.util.Timer;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;


@Manifest(authors = "programer", name = "KeldaGrimMiner", keywords = "Mining", version = 1.0)
public class Main extends ActiveScript implements PaintListener, MouseListener, MessageListener {

    private final List<Strategy> strategies = new ArrayList<Strategy>();
    private Painter painter;
    private MoneyHandler moneyHandler;
    private MiningStrategy miningStrategy;

    @Override
    public boolean onStart() {
        painter = new Painter();
        RockTask rockTask = new RockTask();
        moneyHandler = new MoneyHandler();
        this.getContainer().submit(rockTask);
        this.getContainer().submit(moneyHandler);
        strategies.add(new WalkToBank());
        strategies.add(new BankingStrategy());
        strategies.add(new WalkToMine());
        miningStrategy = new MiningStrategy(rockTask);
        strategies.add(miningStrategy);
        return true;
    }

    @Override
    public void onFinish() {
        log.info("Runtime: " + Timer.format(painter.getRunTime()));
        log.info("Money made: " + (moneyHandler.getTotalMoneyMade() / 1000) + "." + (moneyHandler.getTotalMoneyMade() % 1000) + "k");
        log.info("XP gained: " + painter.xPGained());
        log.info("XP/hr: " + painter.xPPerHour());
    }

    @Override
    public int loop() {
        for (Strategy currentStrategy : strategies) {
            if (currentStrategy.isValid()) {
                painter.setTotalMoneyMade(moneyHandler.getTotalMoneyMade());
                currentStrategy.execute();
            }
        }
        return Random.nextInt(300, 600);
    }

    public void onRepaint(Graphics graphics) {
        painter.onRepaint(graphics);
    }

    public void mouseClicked(MouseEvent e) {
        painter.mouseClicked(e);
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void messageReceived(MessageEvent messageEvent) {
        if (miningStrategy.isValid())
            miningStrategy.messageReceived(messageEvent);
        else
            miningStrategy.resetCurrentRock();
    }
}
