package Util;

import java.util.*;

/**
 * @author： Wang Zhe
 * @date： 2021/10/27 20:01
 * @description： sort
 * @modifiedBy：
 * @version: 1.0
 */
public class SortUtil {

    /**
     * Implemented a comparator for sorting by myself
     * @param map
     * @param <K>
     * @param <V>
     * @return
     */
    public  <K, V extends Comparable<? super V>> Map<K, V> sortByValueDescending(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            @Override
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                int compare = (o1.getValue()).compareTo(o2.getValue());
                return -compare;
            }
        });

        Map<K, V> result = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
}
