package com.lzk.myblog.Service;

import com.lzk.myblog.Pojo.*;

import java.util.List;

public interface adminService {
    int addNotice(int uid,String title,String context,int status,String time);
    List<Notice> getAllNotice();
    Notice getNewNotice();
    List<Notice> getNotice1();
    List<NoticeUser>getLeaveMessage();
    int deleteOneNotice(int nid);
    int addMessage(int uid,String context,int replayUid,String time);
    List<MessageUser> getMessageByUid(int uid);
    List<Message> getMessageByrUid(int uid,int replayUid);
    List<Hot> getAllHts();
}
