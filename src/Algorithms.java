import Util.SortUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author： Wang Zhe
 * @date： 2021/10/27 19:57
 * @description： implement TF-IDF
 * @modifiedBy：
 * @version: 1.0
 */
public class Algorithms {

    /**
     * calculate tf value of each word
     * @param map processed by mapReduce
     * @return OuterLayerMap -> key:userId, value: InnerLayerMap
     *         InnerLayerMap -> key:word, value: tf value
     */
    public Map<String, Map<String, Double>> tf(Map<String, List<String>> map) {
        Map<String, Map<String, Double>> tfMap = new HashMap<>();
        for (Map.Entry<String, List<String>> entry1 : map.entrySet()) {
            String userId = entry1.getKey();
            List<String> bodyList = entry1.getValue();
            StringBuilder totalWordEachUserStr = new StringBuilder();
            //Splice all posts from each user into String
            for (String s : bodyList) {
                totalWordEachUserStr.append(s);
            }
            Map<String, Double> wordFrequencyMap = eachWordtfCal(totalWordEachUserStr.toString());
            tfMap.put(userId, wordFrequencyMap);
        }
        return tfMap;
    }

    /**
     * calculate tf value of each word
     * @param wordAll all posts of every user
     * @return Map<String, Float> key是单词 value是tf值
     */
    public static Map<String, Double> eachWordtfCal(String wordAll) {
        //This map(dictMap) is used to store word and count
        HashMap<String, Integer> dictMap = new HashMap<String, Integer>();
        //This map(tfMap) is used to store word and tf value
        HashMap<String, Double> tfMap = new HashMap<String, Double>();
        int wordCount = 0;

        for (String word : wordAll.split(" ")) {
            wordCount++;
            if (dictMap.containsKey(word)) {
                dictMap.put(word, dictMap.get(word) + 1);
            } else {
                dictMap.put(word, 1);
            }
        }

        for (Map.Entry<String, Integer> entry : dictMap.entrySet()) {
            //tf = Number of times this word appears / Total number of words
            double wordTf = (double) entry.getValue() / wordCount;
            tfMap.put(entry.getKey(), wordTf);
        }
        return tfMap;
    }

    /**
     * calculate the idf value of each
     * @param originalMap parsed from txt file
     * @param tfMap tf value of each word
     * @return OuterLayerMap -> key:userId, value: InnerLayerMap
     *         InnerLayerMap -> key:word, value: tf value
     */
    public Map<String, Map<String, Double>> tfidf(Map<String, List<String>> originalMap, Map<String, Map<String, Double>> tfMap) {
        Map<String, Map<String, Double>> tfidfMap = new HashMap<>();
        SortUtil sortUtil = new SortUtil();
        for (Map.Entry<String, List<String>> entry : originalMap.entrySet()) {
            Map<String, Double> idfMap = new HashMap<>();
            List<String> list = entry.getValue();
            //dataCount = Number of posts per user
            int dataCount = list.size();
            Map<String, Double> singleWordMap = tfMap.get(entry.getKey());
            for (Map.Entry<String, Double> singleMap : singleWordMap.entrySet()) {
                int count = 0;
                String a = singleMap.getKey();
                for (String s : list) {
                    if (s.contains(a)) {
                        count++;
                    }
                }
                //idf = log((number of posts + 1) / )
                double idf = Math.log((dataCount+1) / (count + 1));
                double tfidf = singleMap.getValue() * idf;
                idfMap.put(singleMap.getKey(), tfidf);
            }
            //Sort the values of tf-idf in descending order
            Map<String, Double> finalIdfMap= sortUtil.sortByValueDescending(idfMap);
            tfidfMap.put(entry.getKey(), finalIdfMap);
        }
        return tfidfMap;
    }
}
