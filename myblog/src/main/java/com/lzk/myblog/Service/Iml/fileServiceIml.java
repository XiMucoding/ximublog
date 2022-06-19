package com.lzk.myblog.Service.Iml;

import com.lzk.myblog.Mapper.fileMapper;
import com.lzk.myblog.Pojo.File;
import com.lzk.myblog.Service.fileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author lzk
 * @Email 1801290586@qq.com
 * @Description <类说明>
 * @Date 19:24 2022/6/17
 */
@Service
public class fileServiceIml implements fileService {
    @Autowired
    private fileMapper fileMapper;
    @Override
    public List<File> getAllFile() {
        return fileMapper.getAllFile();
    }

    @Override
    public int updateFile(String name, int status, String url) {
        return fileMapper.updateFile(name,status,url);
    }
}
