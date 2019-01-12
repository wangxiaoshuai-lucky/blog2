package cn.wzy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Create by Wzy
 * on 2018/6/21 14:44
 * 不短不长八字刚好
 */
public class Main {
    public static void main(String[] args) throws IOException {
        String command = "java -classpath /root/AdressQueryUtil AdressQuery 120.77.151.141";
        BufferedReader br;
            Process p = Runtime.getRuntime().exec(command);
            br = new BufferedReader(new InputStreamReader(p.getInputStream(),"GBK"));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            System.out.println(sb.toString());
    }
}
