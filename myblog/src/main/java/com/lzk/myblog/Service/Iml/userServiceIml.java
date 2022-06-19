package com.lzk.myblog.Service.Iml;

import com.lzk.myblog.Mapper.userMapper;
import com.lzk.myblog.Pojo.User;
import com.lzk.myblog.Service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class userServiceIml implements userService {
    @Autowired
    private userMapper userMapper;

    @Override
    public User findUser(String userName, String password) {
        return userMapper.findUser(userName,password);
    }

    @Override
    public User register(String userName, String password,String nickName) {
        User user=userMapper.findUserName(userName);
        if(user==null)userMapper.addUser(userName,password,nickName);
        return user;
    }

    @Override
    public User queryTheUser(int uid) {
        return userMapper.queryTheUser(uid);
    }

    @Override
    public int updateUser(int uid, String password, String nickName) {
        return userMapper.updateUser(uid,password,nickName);
    }

    @Override
    public int changeAvatar(int uid, String avatarUrl) {
        return userMapper.changeAvatar(uid, avatarUrl);
    }

    @Override
    public List<User> queryUser(int index, int num) {
        return userMapper.queryUser(index, num);
    }

    @Override
    public List<User> queryUserByKW(String keyword, int index, int num) {
        return userMapper.queryUserByKW(keyword,index,num);
    }

    @Override
    public int sumOfUser() {
        return userMapper.sumOfUser();
    }

    @Override
    public int sumOfUserByKW(String keyword) {
        return userMapper.sumOfUserByKW(keyword);
    }

    @Override
    public int changeStatus(int uid, int status) {
        return userMapper.changeStatus(uid,status);
    }

    @Override
    public int deleteUser(int uid) {
        return userMapper.deleteUser(uid);
    }
}
