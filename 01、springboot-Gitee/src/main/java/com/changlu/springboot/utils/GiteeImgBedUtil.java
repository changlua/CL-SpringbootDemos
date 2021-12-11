package com.changlu.springboot.utils;

import cn.hutool.core.codec.Base64;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @ClassName UploadGiteeImgBedUtil
 * @Author ChangLu
 * @Date 2021/12/10 23:41
 * @Description TODO
 */
public class GiteeImgBedUtil {


    /**
     * 码云私人令牌
     */
    private static final String ACCESS_TOKEN = "";  //这里不展示我自己的了，需要你自己补充

    /**
     * 码云个人空间名
     */
    private static final String OWNER = "";

    /**
     * 上传指定仓库
     */
    private static final String REPO = "";


    /**
     * 默认上传时指定存放图片路径
     */
    public static final String PATH = "test1/";

    //API
    /**
     * 新建(POST)、获取(GET)、删除(DELETE)文件：()中指的是使用对应的请求方式
     * %s =>仓库所属空间地址(企业、组织或个人的地址path)  (owner)
     * %s => 仓库路径(repo)
     * %s => 文件的路径(path)
     */
    private static final String API_CREATE_POST = "https://gitee.com/api/v5/repos/%s/%s/contents/%s";


    /**
     * 生成创建(获取、删除)的指定文件路径
     * @param originalFilename 原文件名
     * @param path 存储文件路径
     * @return
     */
    private static String createUploadFileUrl(String originalFilename,String path){
        String targetPath = path == null ? GiteeImgBedUtil.PATH : path;
        //获取文件后缀
        String suffix = FileUtils.getFileSuffix(originalFilename);
        //拼接存储的图片名称
        String fileName = System.currentTimeMillis()+"_"+ UUID.randomUUID().toString()+suffix;
        //填充请求路径
        String url = String.format(GiteeImgBedUtil.API_CREATE_POST,
                GiteeImgBedUtil.OWNER,
                GiteeImgBedUtil.REPO,
                targetPath + fileName);
        return url;
    }

    private static String createDelFileUrl(String path){
        //填充请求路径
        String url = String.format(GiteeImgBedUtil.API_CREATE_POST,
                GiteeImgBedUtil.OWNER,
                GiteeImgBedUtil.REPO,
                path);
        return url;
    }

    private static String createGetUrl(String path){
        String targetPath = path == null ? GiteeImgBedUtil.PATH : path;
        //填充请求路径
        String url = String.format(GiteeImgBedUtil.API_CREATE_POST,
                GiteeImgBedUtil.OWNER,
                GiteeImgBedUtil.REPO,
                targetPath);
        return url;
    }

    /**
     * 获取创建文件的请求体map集合：access_token、message、content
     * @param multipartFile 文件字节数组
     * @return 封装成map的请求体集合
     */
    private static Map<String,Object> getUploadBodyMap(byte[] multipartFile){
        HashMap<String, Object> bodyMap = new HashMap<>(3);
        bodyMap.put("access_token", GiteeImgBedUtil.ACCESS_TOKEN);
        bodyMap.put("message", "add file!");
        bodyMap.put("content", Base64.encode(multipartFile));
        return bodyMap;
    }

    /**
     * 创建普通携带请求体集合内容
     * @param map 额外参数
     * @param message 请求信息
     * @return
     */
    private static Map<String,Object> getCommonBodyMap(HashMap map, String message){
        HashMap<String, Object> bodyMap = new HashMap<>(2);
        bodyMap.put("access_token", GiteeImgBedUtil.ACCESS_TOKEN);
        bodyMap.put("message", message);
        if (map != null){
            bodyMap.putAll(map);
        }
        return bodyMap;
    }

    /**
     * **********封装好的实际调用方法*******************
     */

    //超时
    private static int TIMEOUT = 10 * 1000;

    /**
     * 上传文件
     * @param filename 文件名称
     * @param path 路径
     * @param sha 必备参数from 获取仓库具体路径下的内容
     * @return
     */
    public static String uploadFile(String path, String originalFilename, byte[] data){
        String targetURL = GiteeImgBedUtil.createUploadFileUrl(originalFilename,path);
        //请求体封装
        Map<String, Object> uploadBodyMap = GiteeImgBedUtil.getUploadBodyMap(data);
        return HttpUtil.post(targetURL, uploadBodyMap);
    }


    /**
     * 删除指定path路径下的文件
     * @param filename 文件名称
     * @param path 路径
     * @param sha 必备参数from 获取仓库具体路径下的内容
     * @return
     */
    public static String deleteFile(String path,String sha){
        String delFileUrl = createDelFileUrl(path);
        HashMap<String, Object> needMap = new HashMap<>(1);
        needMap.put("sha",sha);//添加sha参数
        return HttpUtil.createRequest(Method.DELETE, delFileUrl)
                .form(getCommonBodyMap(needMap,"del file!"))  //构建请求表单
                .timeout(TIMEOUT)
                .execute().body();
    }

    /**
     * 获取仓库具体路径下的内容，主要是获取 sha
     * @param path
     * @return
     */
    public static String getSha(String path){
        String getShaUrl = createDelFileUrl(path);
        return HttpUtil.createRequest(Method.GET, getShaUrl)
                .form(getCommonBodyMap(null, "get sha!"))
                .timeout(TIMEOUT)
                .execute().body();
    }

}