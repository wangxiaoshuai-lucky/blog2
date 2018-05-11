package cn.wzy.util;

import org.cn.wzy.util.MapperingGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author wzy
 * @Date 2018/4/10 11:48
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class Main {

    @Autowired
    private  MapperingGenerator mapperingGenerator;

    @Test
    public void run() {
        MapperingGenerator.oldPath = "F:\\blog2\\src\\main\\resources\\mapper";
        MapperingGenerator.implPath = MapperingGenerator.oldPath + "\\impl";
        MapperingGenerator.sql = MapperingGenerator.oldPath + "\\sql";
        mapperingGenerator.run();
    }
    @Test
    public void test() {
        String u = "http://www.wangzhengyu.cn/blog2/user/checkOnline.do";
        String u2 = "http://www.wangzhengyu.cn/blog2/pic/getPic.do";
        for (int i = 0; i < 1000; i++) {
            Thread now = new Thread(()->{
                try {
                    Random random = new Random();
                    int j = random.nextInt(2);
                    URL url = new URL(j == 0? u : u2);
                    //打开和url之间的连接
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    PrintWriter out = null;
                    //请求方式
                    conn.setRequestMethod("GET");
//           //设置通用的请求属性
                    conn.setRequestProperty("accept", "*/*");
                    conn.setRequestProperty("Cookie","JSESSIONID=4C00B95719231DC34998BECB6BF9EB72");
                    conn.setRequestProperty("connection", "Keep-Alive");
                    conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
                    //设置是否向httpUrlConnection输出，设置是否从httpUrlConnection读入，此外发送post请求必须设置这两个
                    //最常用的Http请求无非是get和post，get请求可以获取静态页面，也可以把参数放在URL字串后面，传递给servlet，
                    //post与get的 不同之处在于post的参数不是放在URL字串里面，而是放在http请求的正文内。
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    //获取URLConnection对象对应的输出流
                    out = new PrintWriter(conn.getOutputStream());
                    //发送请求参数即数据
                    Map<String, String> map = new HashMap<>();
                    map.put("t","909090");
                    out.print(map);
                    //缓冲数据
                    out.flush();
                    //获取URLConnection对象对应的输入流
                    InputStream is = conn.getInputStream();
                    //构造一个字符流缓存
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    String str = "";
                    while ((str = br.readLine()) != null) {
                        System.out.println(str);
                    }
                    //关闭流
                    is.close();
                    //断开连接，最好写上，disconnect是在底层tcp socket链接空闲时才切断。如果正在被其他线程使用就不切断。
                    //固定多线程的话，如果不disconnect，链接会增多，直到收发不出信息。写上disconnect后正常一些。
                    conn.disconnect();
                    System.out.println("完整结束");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            now.start();
        }
    }
}
