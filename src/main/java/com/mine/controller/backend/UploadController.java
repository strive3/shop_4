package com.mine.controller.backend;

import com.mine.common.ServerResponse;
import com.mine.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 杜晓鹏
 * @create 2019-01-10 11:15
 */
@Controller
@RequestMapping("/manager/product")
public class UploadController {

    @Autowired
    ProductService productService;


    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse upload(@RequestParam(value = "upload_file",required = false) MultipartFile file){

//         String path="D:\\ftpfile";
        String path="D:\\ftpfile";
        return productService.upload(file,path); //逻辑视图     前缀+逻辑视图+后缀  --》 /WEB-INF/jsp/upload.jsp
    }
}
