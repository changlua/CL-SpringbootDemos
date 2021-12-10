package com.changlu.springboot.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.changlu.springboot.Exception.CommonEnum;
import com.changlu.springboot.Exception.OwnException;
import com.changlu.springboot.config.GiteeConstant;
import com.changlu.springboot.domain.Basic.ResultBody;
import com.changlu.springboot.domain.Request.BasicRequest;
import com.changlu.springboot.utils.GiteeImgBedUtil;
import com.changlu.springboot.utils.WebTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @ClassName GiteeController
 * @Author ChangLu
 * @Date 2021/12/1 0:05
 * @Description
 */

@RestController
@Slf4j
public class GiteeController {

    @Value("${gitee.upload.path}")
    private String MEMBERS_UPLOAD_PATH;

    /**
     * 上传文件
     *
     * @param multipartFile 文件对象
     * @return
     * @throws IOException
     */
    @PostMapping("/gitee/upload")
    public ResultBody uploadFile(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        log.info("uploadFile()请求已来临...");
        //根据文件名生成指定的请求url
        String originalFilename = multipartFile.getOriginalFilename();
        if (originalFilename == null) {
            log.info("服务器接收文件失败！");
            throw new OwnException(CommonEnum.IMAGE_EXIST_ERROR);
        }
        //Gitee请求：发送上传文件请求
        String JSONResult = GiteeImgBedUtil.uploadFile(MEMBERS_UPLOAD_PATH, originalFilename, multipartFile.getBytes());
        //解析响应JSON字符串
        JSONObject jsonObj = JSONUtil.parseObj(JSONResult);
        //请求失败
        if (jsonObj == null || jsonObj.getObj(GiteeConstant.RESULT_BODY_COMMIT) == null) {
            log.info("上传文件失败！");
            return ResultBody.fail(CommonEnum.IMAGE_UPLOAD_ERROR);
        }
        //请求成功：返回下载地址
        JSONObject content = JSONUtil.parseObj(jsonObj.getObj(GiteeConstant.RESULT_BODY_CONTENT));
        log.info("上传成功，下载地址为：" + content.getObj(GiteeConstant.RESULT_BODY_DOWNLOAD_URL));
        return ResultBody.success(content.getObj(GiteeConstant.RESULT_BODY_DOWNLOAD_URL));
    }

    /**
     * 删除文件
     *
     * @param request
     * @return
     */
    @PostMapping("/gitee/del")
    public ResultBody delFile(@RequestBody BasicRequest request) {
        //1、解析取得原始上传路径
        String url = request.getUrl();
        if (WebTools.isNotEmpty(url) && !url.contains("master/")) {
            log.info("url：" + url + " 无法解析路径！");
            throw new OwnException(CommonEnum.URL_PARSE_FAILED);
        }
        String path = url.substring(url.indexOf("master/") + 7);
        log.info("解析取得待删除路径：" + path);
        if (!WebTools.isEmpty(path)) {
            //2、Gitee请求：获取sha
            String shaResult = GiteeImgBedUtil.getSha(path);
            JSONObject jsonObj = JSONUtil.parseObj(shaResult);
            if (jsonObj == null) {
                log.info("delFile中获取sha失败！");
                return ResultBody.fail(CommonEnum.DEL_FILE_FAILED);
            }
            String sha = jsonObj.getStr(GiteeConstant.RESULT_BODY_SHA);
            //3、Gitee请求：发送删除请求
            String JSONResult = GiteeImgBedUtil.deleteFile(path, sha);
            jsonObj = JSONUtil.parseObj(JSONResult);
            if (jsonObj == null || jsonObj.getObj(GiteeConstant.RESULT_BODY_COMMIT) == null) {
                log.info("删除文件失败！");
                return ResultBody.fail(CommonEnum.DEL_FILE_FAILED);
            }
        }
        log.info("文件路径为：" + path + " 删除成功！");
        return ResultBody.success("删除成功！");
    }

}