package com.lzk.myblog.Controller;

import com.lzk.myblog.Pojo.Result;
import com.lzk.myblog.Service.fileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Author lzk
 * @Email 1801290586@qq.com
 * @Description <类说明>
 * @Date 19:16 2022/6/17
 */
@RestController
@RequestMapping("/file")
public class FileController {
    @Autowired
    private fileService fileService;

    @Autowired
    private minioController minioController;
    //获取所有文件
    @RequestMapping("/getAllList")
    public Result getAllList() {
        return Result.succ(fileService.getAllFile());
    }

    @RequestMapping("/updateFile")
    public Result updateFile(@RequestParam("name")String name,@RequestParam("status")int status,
                             @RequestParam("url")String url) {
        int i = fileService.updateFile(name, status, url);
        if(i==0){
            //删除存储的文件
            String substring = url.substring(url.lastIndexOf("/"), url.length() - url.lastIndexOf("/"));
            minioController.delete(substring);
            return Result.fail("上传失败");
        }
        return Result.succ("上传成功");
    }
}
