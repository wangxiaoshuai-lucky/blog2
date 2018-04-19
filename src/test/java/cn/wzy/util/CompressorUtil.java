package cn.wzy.util;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 通过yuicompressor压缩JS|CSS文件工具类
 *
 * @author longwentao
 * @date 2016-12-17
 */
public class CompressorUtil {
    private static final String encoding = "utf-8";
    private static final String[] suffixArray = { ".js", ".css" };

    public static void main(String[] args) {
        String yuiPath = "D:\\download\\yuicompressor.jar";
        String filePath = "F:\\blog2\\src\\main\\webapp\\static\\js";

        compressFile(yuiPath, filePath);
    }

    /**
     * 压缩指定文件夹下所有的js/css
     *
     * @param yuiPath
     *            yuicompressor-2.4.7.jar文件路径
     * @param filePath
     *            要压缩的文件夹路径
     */
    public static void compressFile(String yuiPath, String filePath) {
        File file = new File(filePath);
        List<String> commondList = new ArrayList<String>();
        initCommondList(yuiPath, commondList, file);
        excuteCompress(commondList);
    }

    /**
     * 执行压缩命令
     * @param commondList
     */
    private static void excuteCompress(List<String> commondList) {
        Runtime runTime = Runtime.getRuntime();
        Date startTime = new Date();
        Long count = 0L;
        for (String cmd : commondList) {
            try {
                System.out.println(cmd);
                runTime.exec(cmd);
                count++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Date endTime = new Date();
        Long cost = endTime.getTime() - startTime.getTime();
        System.out.println("压缩完成，耗时：" + cost + "ms，共压缩文件个数：" + count);
    }

    /**
     * 初始化压缩命令
     * @param yuiPath
     * @param commondList
     * @param file
     */
    private static void initCommondList(String yuiPath,
                                        List<String> commondList, File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            // 如果某个文件夹是空文件夹，则跳过
            if (files == null) {
                return;
            }
            for (File f : files) {
                initCommondList(yuiPath, commondList, f);
            }
        } else {
            String fileName = file.getName();
            String suffix = fileName.substring(fileName.lastIndexOf("."),
                    fileName.length());

            List<String> suffixList = Arrays.asList(suffixArray);
            if (suffixList.contains(suffix)
                    && !fileName.endsWith("-min" + suffix)) {
                StringBuffer sb = new StringBuffer();
                sb.append("java -jar ");
                sb.append(yuiPath);
                sb.append(" --type ");
                sb.append(suffix.substring(suffix.indexOf(".") + 1));
                sb.append(" --charset ");
                sb.append(encoding).append(" ");
                sb.append(file.getPath()).append(" ");
                sb.append("-o").append(" ");
                sb.append(file.getPath().replace(suffix, "-min" + suffix));

                commondList.add(sb.toString());
            }

        }
    }
}