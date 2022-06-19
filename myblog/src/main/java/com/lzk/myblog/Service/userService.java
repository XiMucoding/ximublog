package com.lzk.myblog.Service;

import com.lzk.myblog.Pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface userService {
    public User findUser(String userName,String password);
    public User register(String userName,String password,String nickName);
    public User queryTheUser(int uid);
    public int updateUser(int uid,String password,String nickName);
    public int changeAvatar(int uid, String avatarUrl);
    List<User> queryUser(int index,int num);
    List<User> queryUserByKW(String keyword,int index,int num);
    int sumOfUser();
    int sumOfUserByKW(String keyword);
    int changeStatus(int uid,int status);
    int deleteUser(int uid);
}
