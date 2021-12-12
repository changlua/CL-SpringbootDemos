package com.changlu.springboot.utils;

/**
 * @ClassName WebTools
 * @Author ChangLu
 * @Date 2021/10/5 23:35
 * @Description 常用工具
 */
public class WebTools {

    /** 空字符串 */
    private static final String NULLSTR = "";

    /**
     * * 判断一个对象是否为空
     *
     * @param object Object
     * @return true：为空 false：非空
     */
    public static boolean isNull(Object object)
    {
        return object == null;
    }


    /**
     * * 判断一个字符串是否为空串
     *
     * @param str String
     * @return true：为空 false：非空
     */
    public static boolean isEmpty(String str)
    {
        return isNull(str) || NULLSTR.equals(str.trim());
    }

    /**
     * * 判断多个字符串是否为空串
     *
     * @param str String
     * @return true：为空 false：非空
     */
    public static boolean areEmpty(String...strs)
    {
        for (String str : strs) {
            if(isEmpty(str)){
                return true;
            }
        }
        return false;
    }

    /**
     * * 判断一个字符串是否为非空串
     *
     * @param str String
     * @return true：非空串 false：空串
     */
    public static boolean isNotEmpty(String str)
    {
        return !isEmpty(str);
    }


}