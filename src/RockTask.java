import com.rsbuddy.script.methods.Objects;
import com.rsbuddy.script.methods.Players;
import com.rsbuddy.script.task.LoopTask;
import com.rsbuddy.script.wrappers.GameObject;
import com.rsbuddy.script.wrappers.Tile;

import java.util.Arrays;
import java.util.Comparator;


class RockTask extends LoopTask implements Paths, Filters {

    private GameObject nextRock;
    private final DistanceComparator distanceComparator;

    private class DistanceComparator implements Comparator<GameObject> {
        public int compare(GameObject o1, GameObject o2) {
            Tile playerLoc = Players.getLocal().getLocation();
            if (o1.getLocation().distanceTo(playerLoc) > o2.getLocation().distanceTo(playerLoc))
                return 1;
            else if (o1.getLocation().distanceTo(playerLoc) == o2.getLocation().distanceTo(playerLoc))
                return 0;
            else
                return -1;
        }
    }

    RockTask() {
        distanceComparator = new DistanceComparator();
    }

    @Override
    public int loop() {
        if (miningArea.contains(Players.getLocal().getLocation())) {
            GameObject[] runiteRocksAvail = Objects.getLoaded(miningArea, RUNITE);
            Arrays.sort(runiteRocksAvail, distanceComparator);
            GameObject[] adamantRocksAvail = Objects.getLoaded(miningArea, ADAMANTITE);
            Arrays.sort(adamantRocksAvail, distanceComparator);
            GameObject[] goldRocksAvail = Objects.getLoaded(miningArea, GOLD);
            Arrays.sort(goldRocksAvail, distanceComparator);
            GameObject[] rocksAvailable = concatAll(runiteRocksAvail, adamantRocksAvail, goldRocksAvail);
            try {
                nextRock = rocksAvailable[0];
            } catch (ArrayIndexOutOfBoundsException ignored) {
            }
        }
        return 0;
    }

    public GameObject nextRock() throws NullPointerException {
        if (this.nextRock == null) throw new NullPointerException("No rock available");
        return this.nextRock;
    }

    private <T> T[] concatAll(T[] first, T[]... rest) {
        int totalLength = first.length;
        for (T[] array : rest) {
            totalLength += array.length;
        }
        T[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;
        for (T[] array : rest) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }
}