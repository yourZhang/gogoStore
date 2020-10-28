
package com.store.file.controller;


import com.store.entity.Result;
import com.store.file.util.FastDFSClient;
import com.store.file.util.FastDFSFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("file")
public class FileController {

    /**
     * 功能描述: <br>
     * 〈文件上传〉
     *
     * @Param: [file]
     * @return: com.store.entity.Result
     * @Author: xiaozhang666
     * @Date: 2020/10/28 17:06
     */
    @RequestMapping(value = "upload", method = RequestMethod.POST)
    public Result upload(@RequestParam("file") MultipartFile file) {

        try {
            //1.获取文件名称 a.jpg
            String fileName = file.getOriginalFilename();
            //2.获取文件内容
            byte[] content = file.getBytes();
            //3.获取文件扩展名
            String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
            System.out.println("文件扩展名： " + ext);
            FastDFSFile fastDFSFile = new FastDFSFile(fileName, content, ext);
            String[] uploadResults = FastDFSClient.upload(fastDFSFile);
            //获取组名
            String groupName = uploadResults[0];
            //获取文件存储路径
            String remoteFileName = uploadResults[1];
            String fileUrl = FastDFSClient.getTrackerUrl() + groupName + "/" + remoteFileName;
            System.out.println("文件上传成功后的路径： " + fileUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Result();
    }
}
