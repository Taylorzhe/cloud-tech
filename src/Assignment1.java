import Util.TextUtil;

import java.util.*;

/**
 * @author： Wang Zhe
 * @date： 2021/10/26 22:02
 * @description： main
 * @modifiedBy：
 * @version: 1.0
 */
public class Assignment1 {

    public static void main(String[] args) {
        TextUtil textUtil = new TextUtil();
        Algorithms algorithms = new Algorithms();
        List<String> textList = textUtil.loadText();    //load text
        Map<String, List<String>> map = mapReduce(textList);
        Map<String, Map<String, Double>> tfMap = algorithms.tf(map);
        Map<String, Map<String, Double>> idfMap = algorithms.tfidf(map, tfMap);
        printAll(idfMap);
    }


    /**
     * Store data parsed from txt file in MapReduce mode in a Map
     * @param list data parsed from txt file
     * @return map-> key:userId   value:all posts of this user
     */
    static Map<String, List<String>> mapReduce(List<String> list) {
        Map<String, List<String>> map = new HashMap<>();
        for (String s : list) {
            List<String> bodies = new ArrayList<>();
            String[] data = s.split(",");
            String userId = data[0];
            String body = data[1];
            if ((map.containsKey(userId))) {
                bodies = map.get(userId);
                bodies.add(body);
                map.put(userId, bodies);
            } else {
                bodies.add(body);
                map.put(userId, bodies);
            }
        }
        return map;
    }


    /**
     * print the first ten highest td-idf value of word each user
     * @param idfMap final result
     */
    static void printAll(Map<String, Map<String, Double>> idfMap){
        for (Map.Entry<String, Map<String, Double>> tf : idfMap.entrySet()) {
            Map<String, Double> singleMap = tf.getValue();
            System.out.println("userId:" + tf.getKey());
            int n = 0;
            for (Map.Entry<String, Double> single : singleMap.entrySet()) {
                System.out.println("word:" + single.getKey() + " ,tf_idf:" + single.getValue());
                n++;
                if (n == 10) break;
            }
            System.out.println("-------------------------------------------------");
        }
    }
}
