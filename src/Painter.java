import com.rsbuddy.event.listeners.PaintListener;
import com.rsbuddy.script.methods.Mouse;
import com.rsbuddy.script.methods.Skills;
import com.rsbuddy.script.methods.Widgets;
import com.rsbuddy.script.util.Timer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

class Painter implements PaintListener, MouseListener {

    private final Font FONT = new Font("Arial", Font.BOLD, 12);
    private int startXP = 0;
    private int startLvl = 0;
    private int XPgained = 0;
    private int XPHour = 0;
    private int totalMoneyMade = 0;
    private long startTime = 0;
    private long runTime;
    private Rectangle hideFrame;
    private boolean hidePaint;
    private Image mouseImage;
    private boolean fade = true;
    private int alpha = 255;


    Painter() {
        try {
            mouseImage = ImageIO.read(Main.class.getResourceAsStream("dragonPickaxe.png"));
        } catch (IOException ignored) {
        }
        startLvl = Skills.getCurrentLevel(Skills.MINING);
        startTime = System.currentTimeMillis();
        startXP = Skills.getCurrentExp(Skills.MINING);
    }

    public long getRunTime() {
        return runTime;
    }

    public int xPGained() {
        return XPgained;
    }

    public int xPPerHour() {
        return XPHour;
    }

    public void setTotalMoneyMade(int totalMoneyMade) {
        this.totalMoneyMade = totalMoneyMade;
    }


    @SuppressWarnings({"ConstantConditions"})
    public void onRepaint(Graphics g) {
        Color darkMine = new Color(20, 18, 18);
        Color lightBlue = new Color(125, 187, 196, alpha);
        Color stringLightBlue = new Color(125, 187, 196);
        int x = Widgets.getComponent(137, 0).getAbsLocation().x;
        int y = Widgets.getComponent(137, 0).getAbsLocation().y;
        int width = Widgets.getComponent(137, 0).getWidth();
        int height = Widgets.getComponent(137, 0).getHeight();
        hideFrame = new Rectangle(x, y, width, height);
        XPgained = Skills.getCurrentExp(Skills.MINING) - startXP;
        int currLevel = Skills.getCurrentLevel(Skills.MINING);
        int percentToLevel = Skills.getPercentToNextLevel(Skills.MINING);
        runTime = System.currentTimeMillis() - startTime;
        int XPTNL = Skills.getExpToNextLevel(Skills.MINING);
        XPHour = (int) ((XPgained) * 3600000.0 / runTime);
        long TTL = (long) ((double) XPTNL / (double) XPHour * 3600000);
        if (!hidePaint) {
            g.setColor(darkMine);
            g.drawRoundRect(x, y, width, height, 10, 10);
            g.fillRoundRect(x, y, width, height, 10, 10);
            g.setFont(FONT);
            g.setColor(stringLightBlue);
            if (totalMoneyMade > 1000) {
                g.drawString("Runtime : " + Timer.format(runTime) + " || Money made: " + (totalMoneyMade / 1000) + "." + ((totalMoneyMade % 1000) / 100) + "k", x + 10, y + 20);
            } else {
                g.drawString("Runtime : " + Timer.format(runTime) + " || Money made: " + totalMoneyMade, x + 10, y + 20);
            }
            g.drawString("Current Mining Level : " + currLevel + " (+" + (currLevel - startLvl) + ")", x + 10, y + 60);
            if (XPgained > 0) {
                g.drawString("TTL : " + Timer.format(TTL), x + 10, y + 40);
            } else {
                g.drawString("TTL : Waiting to mine..", x + 10, y + 40);
            }
            if (XPHour > 1000) {
                g.drawString("XP Gained : " + XPgained + " || " + (XPHour / 1000) + "." + ((XPHour % 1000) / 100) + "k/Hr", x + 10, y + 80);
            } else {
                g.drawString("XP Gained : " + XPgained + " || " + XPHour + "/Hr", x + 10, y + 80);
            }
            g.setColor(Color.BLACK);
            g.fillRoundRect(x + 10, y + 110, 100, FONT.getSize(), 2, 2);
            g.setColor(lightBlue);
            if (fade) {
                if (alpha >= 255 || alpha <= 255)
                    alpha -= 17;
                if (alpha <= 80)
                    fade = false;
            } else {
                if (!(alpha >= 255))
                    alpha += 17;
                else
                    fade = true;
            }
            g.fillRoundRect(x + 10, y + 110, percentToLevel, FONT.getSize(), 2, 2);
            g.setColor(stringLightBlue);
            g.drawRoundRect(x + 10, y + 110, 100, FONT.getSize(), 2, 2);
            g.drawString("XP To level : %" + percentToLevel + " (" + Skills.getExpToNextLevel(Skills.MINING) + ")", x + 10, y + 100);
        }
        g.drawImage(mouseImage, Mouse.getLocation().x, Mouse.getLocation().y, null);
    }


    public void mouseClicked(MouseEvent e) {
        if (hideFrame.contains(e.getPoint())) {
            hidePaint = !hidePaint;
        }
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
}
