import com.rsbuddy.script.methods.Walking;
import com.rsbuddy.script.wrappers.Area;
import com.rsbuddy.script.wrappers.Tile;
import com.rsbuddy.script.wrappers.TilePath;

interface Paths {
    final Area miningArea = new Area(new Tile(2880, 10238), new Tile(2860, 10262));
    final Tile[] TilesToMine = {new Tile(2845, 10217), new Tile(2858, 10218), new Tile(2867, 10224), new Tile(2870, 10236), new Tile(2870, 10247)};
    final Tile[] TilesToBank = {new Tile(2872, 10247), new Tile(2870, 10237), new Tile(2867, 10224), new Tile(2858, 10218), new Tile(2845, 10217), new Tile(2839, 10208)};
    final TilePath pathToMine = Walking.newTilePath(TilesToMine);
    final TilePath pathToBank = Walking.newTilePath(TilesToBank);
}
