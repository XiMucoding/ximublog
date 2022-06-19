package com.lzk.myblog.Mapper;

import com.lzk.myblog.Pojo.File;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;


import java.util.List;

/**
 * @Author lzk
 * @Email 1801290586@qq.com
 * @Description <接口说明>
 * @Date 19:20 2022/6/17
 */
@Mapper
@Repository
public interface fileMapper {
    @Select("select * from minio")
    List<File> getAllFile();

    @Insert("insert into minio(name,status,url) values(#{name},#{status},#{url})")
    int updateFile(@Param("name")String name, @Param("status")int status, @Param("url")String url);
}
