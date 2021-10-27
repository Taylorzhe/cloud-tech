package Util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author： Wang Zhe
 * @date： 2021/10/27 13:11
 * @description： TODO
 * @modifiedBy：
 * @version: 1.0
 */
public class TextUtil {

    public List<String> loadText() {
        List<String> checkins = new ArrayList(); // List初始化
        try {
            String pathname = "src/taskdata.txt";
            File filename = new File(pathname);
            InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
            BufferedReader br = new BufferedReader(reader);
            String line = "";
            line = br.readLine();
            while (line != null) {
                checkins.add(line);
                line = br.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return checkins;
    }
}
