package com.lzk.myblog.Service.Iml;

import com.lzk.myblog.Mapper.adminMapper;
import com.lzk.myblog.Pojo.*;
import com.lzk.myblog.Service.adminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class adminServiceIml implements adminService {

    @Autowired
    private adminMapper adminMapper;

    @Override
    public int addNotice(int uid, String title, String context, int status, String time) {
        return adminMapper.addNotice(uid,title,context,status,time);
    }

    @Override
    public List<Notice> getAllNotice() {
        return adminMapper.getAllNotice();
    }

    @Override
    public Notice getNewNotice() {
        return adminMapper.getNewNotice();
    }

    @Override
    public List<Notice> getNotice1() {
        return adminMapper.getNotice1();
    }

    @Override
    public List<NoticeUser> getLeaveMessage() {
        return adminMapper.getLeaveMessage();
    }

    @Override
    public int deleteOneNotice(int nid) {
        return adminMapper.deleteOneNotice(nid);
    }

    @Override
    public int addMessage(int uid, String context, int replayUid, String time) {
        return adminMapper.addMessage(uid,context,replayUid,time);
    }

    @Override
    public List<MessageUser> getMessageByUid(int uid) {
        return adminMapper.getMessageByUid(uid);
    }

    @Override
    public List<Message> getMessageByrUid(int uid, int replayUid) {
        return adminMapper.getMessageByrUid(uid,replayUid);
    }

    @Override
    public List<Hot> getAllHts() {
        return adminMapper.getAllHts();
    }
}
