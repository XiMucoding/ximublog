package com.lzk.myblog.Service;

import com.lzk.myblog.Pojo.File;

import java.util.List;

/**
 * @Author lzk
 * @Email 1801290586@qq.com
 * @Description <接口说明>
 * @Date 19:23 2022/6/17
 */
public interface fileService {
    List<File> getAllFile();
    int updateFile(String name,int status,String url);
}
