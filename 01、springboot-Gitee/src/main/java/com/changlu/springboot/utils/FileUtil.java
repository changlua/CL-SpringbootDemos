package com.changlu.springboot.utils;

/**
 * @ClassName FileUtils
 * @Author ChangLu
 * @Date 2021/8/1 18:18
 * @Description 文件工具类
 */
public class FileUtil {

    /**
     * 获取文件名的后缀，如：changlu.jpg => .jpg
     * @return 文件后缀名
     */
    public static String getFileSuffix(String fileName) {
        return fileName.contains(".") ? fileName.substring(fileName.indexOf('.')) : null;
    }
}