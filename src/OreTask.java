import com.rsbuddy.script.methods.Objects;
import com.rsbuddy.script.methods.Players;
import com.rsbuddy.script.task.LoopTask;
import com.rsbuddy.script.wrappers.GameObject;

import java.util.Arrays;

class OreTask extends LoopTask implements Paths, Filters {

    private GameObject[] rocksAvailable;

    @Override
    public int loop() {
        if (miningArea.contains(Players.getLocal().getLocation())) {
            rocksAvailable = this.concatAll(Objects.getLoaded(miningArea, RUNITE), Objects.getLoaded(miningArea, ADAMANTITE), Objects.getLoaded(miningArea, GOLD));
        }
        return 0;
    }

    public GameObject nextRock() throws ArrayIndexOutOfBoundsException {
        return rocksAvailable[0];
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

