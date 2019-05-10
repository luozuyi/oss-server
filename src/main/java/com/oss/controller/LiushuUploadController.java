package com.oss.controller;

import com.oss.utils.OssLiushuUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author luozuyi
 */
@RestController
public class LiushuUploadController {

    @PostMapping(value = "liushu-oss")
    public Map<String,Object> uploadToOss(String url) {
        Map<String,Object> res = new HashMap<>(16);
        if(url == null || url == ""){
            res.put("code",-1);
            res.put("msg","网络图片不能为空");
        }else{
            try {
                String ext = url.substring(url.lastIndexOf("."));
                String bucketName = "liushu-book-pic";
                String uuid = UUID.randomUUID().toString().replace("-","");
                String objectName = uuid+ext;
                OssLiushuUtils.inputStream(bucketName,url,objectName);
                OssLiushuUtils.setPrivate(bucketName,objectName);
                res.put("code",0);
                res.put("msg","成功");
                res.put("ossUrl",OssLiushuUtils.getBucketNameUrl()+"/"+objectName);
            } catch (IOException e) {
                res.put("code",-2);
                res.put("msg","流出错");
                e.printStackTrace();
            } catch (Exception e1){
                res.put("code",-3);
                res.put("msg","后台出错");
                e1.printStackTrace();
            }
        }
        return res;
    }
}
