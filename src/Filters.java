import com.rsbuddy.script.util.Filter;
import com.rsbuddy.script.wrappers.GameObject;

interface Filters {
    Filter<GameObject> RUNITE = new Filter<GameObject>() {

        public boolean accept(GameObject gameObject) {
            return gameObject.getId() == 45069 || gameObject.getId() == 45070;
        }
    };
    Filter<GameObject> ADAMANTITE = new Filter<GameObject>() {

        public boolean accept(GameObject gameObject) {
            return gameObject.getId() == 29233 || gameObject.getId() == 29235;
        }
    };
    Filter<GameObject> GOLD = new Filter<GameObject>() {

        public boolean accept(GameObject gameObject) {
            return gameObject.getId() == 45067 || gameObject.getId() == 45068;
        }
    };
}
