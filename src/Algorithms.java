import Util.SortUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author： Wang Zhe
 * @date： 2021/10/27 19:57
 * @description： TODO
 * @modifiedBy：
 * @version: 1.0
 */
public class Algorithms {

    public Map<String, Map<String, Double>> tf(Map<String, List<String>> map) {
        Map<String, Map<String, Double>> tfMap = new HashMap<>();
        for (Map.Entry<String, List<String>> entry1 : map.entrySet()) {
            Map<String, Double> wordFrequencyMap = new HashMap<>();
            String userId = entry1.getKey();
            List<String> bodyList = entry1.getValue();
            StringBuilder totalWordEachUserStr = new StringBuilder();
            for (String s : bodyList) {
                totalWordEachUserStr.append(" ").append(s);
            }
            wordFrequencyMap = eachWordtfCal(totalWordEachUserStr.toString());
            tfMap.put(userId, wordFrequencyMap);
        }
        return tfMap;
    }

    /**
     * 计算每个文档的tf值
     *
     * @param wordAll
     * @return Map<String, Float> key是单词 value是tf值
     */
    public static Map<String, Double> eachWordtfCal(String wordAll) {
        //存放（单词，单词数量）
        HashMap<String, Integer> dict = new HashMap<String, Integer>();
        //存放（单词，单词词频）
        HashMap<String, Double> tf = new HashMap<String, Double>();
        int wordCount = 0;

        /**
         * 统计每个单词的数量，并存放到map中去
         * 便于以后计算每个单词的词频
         * 单词的tf=该单词出现的数量n/总的单词数wordCount
         */
        for (String word : wordAll.split(" ")) {
            wordCount++;
            if (dict.containsKey(word)) {
                dict.put(word, dict.get(word) + 1);
            } else {
                dict.put(word, 1);
            }
        }

        for (Map.Entry<String, Integer> entry : dict.entrySet()) {
            double wordTf = (double) entry.getValue() / wordCount;
            tf.put(entry.getKey(), wordTf);
        }
        return tf;
    }

    public Map<String, Map<String, Double>> idf(Map<String, List<String>> originalMap, Map<String, Map<String, Double>> tfMap) {
        Map<String, Map<String, Double>> tfidfMap = new HashMap<>();
        SortUtil sortUtil = new SortUtil();
        for (Map.Entry<String, List<String>> entry : originalMap.entrySet()) {
            Map<String, Double> idfMap = new HashMap<>();
            List<String> list = entry.getValue();
            //单个用户总文档数
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
                double idf = Math.log((dataCount+1) / (count + 1));
                double tfidf = singleMap.getValue() * idf;
                idfMap.put(singleMap.getKey(), tfidf);
            }
            Map<String, Double> finalIdfMap= sortUtil.sortByValueDescending(idfMap);
            tfidfMap.put(entry.getKey(), finalIdfMap);
        }
        return tfidfMap;
    }
}
