package com.lzk.myblog.Mapper;

import com.lzk.myblog.Pojo.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Mapper
public interface userMapper {
    //用户查询
    @Select("select * from user where userName=#{userName} and password=#{password}")
    User findUser(@Param("userName") String userName,@Param("password")String password);
    @Select("select * from user where uid=#{uid}")
    User queryTheUser(int uid);
    //账号查询
    @Select("select * from user where userName=#{userName}")
    User findUserName(@Param("userName") String userName);
    //添加用户
    @Insert("insert into user(userName,password,nickName) values(#{userName},#{password},#{nickName})")
    int addUser(@Param("userName") String userName,@Param("password")String password,@Param("nickName")String nickName);
    //修改用户信息
    @Update("update user set password=#{password},nickName=#{nickName} where uid=#{uid}")
    int updateUser(@Param("uid")int uid,@Param("password")String password,@Param("nickName")String nickName);
    @Update("update user set avatar=#{avatarUrl} where uid=#{uid}")
    int changeAvatar(@Param("uid")int uid,@Param("avatarUrl")String avatarUrl);
    //user的总数
    @Select("select count(uid) from user")
    int sumOfUser();
    //查询符合条件的user总数
    @Select("select count(uid) from user where nickName like #{keyword}")
    int sumOfUserByKW(String keyword);
    //前num个user
    @Select("select * from user limit #{index},#{num}")
    List<User> queryUser(@Param("index") int index, @Param("num")int num);
    ////查询符合条件的前num个user
    @Select("select * from user where nickName like #{keyword} limit #{index},#{num}")
    List<User> queryUserByKW(@Param("keyword") String keyword,@Param("index") int index, @Param("num")int num);
    //更改用户状态
    @Update("update user set status=#{status} where uid=#{uid}")
    int changeStatus(@Param("uid")int uid,@Param("status")int status);
    //删除指定用户
    @Delete("delete from user where uid=#{uid}")
    int deleteUser(int uid);
}
