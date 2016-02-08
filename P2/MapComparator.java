import java.util.Comparator;
import java.util.Map;

class MapComparator implements Comparator {

    Map<Character, Integer> map;

    public MapComparator(Map<Character, Integer> map) {
        this.map = map;
    }

    @Override
    public int compare(Object a, Object b) {

        if (map.get(a) < map.get(b)) {
            return -1;
        } else if (map.get(a) > map.get(b)) {
            return 1;
        } else {
            if((Character) a < (Character) b) {
                return -1;
            } else if((Character) a > (Character) b) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}