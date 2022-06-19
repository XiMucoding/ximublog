package com.lzk.myblog.Controller;

import com.lzk.myblog.Pojo.*;
import com.lzk.myblog.Service.adminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class adminController {
    @Autowired
    private adminService adminService;

    //添加通知
    @RequestMapping("/addNotice")
    public Result addNotice(@RequestParam("uid")int uid, @RequestParam("title")String title, @RequestParam("context")String context, @RequestParam("status")int status){
        Date date=new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(date.getTime());
        int r=adminService.addNotice(uid,title,context,status,time);
        if(r==0)return Result.fail("发送失败！");
        return Result.succ(null);
    }
    //获得所有通知
    @RequestMapping("/getAllNotice")
    public Result getAllNotice(){
        List<Notice> noticeList=adminService.getAllNotice();
        return Result.succ(noticeList);
    }
    //获得最新公告
    @RequestMapping("/getNewNotice")
    public Result getNewNotice(){
        Notice notice=adminService.getNewNotice();
        return Result.succ(notice);
    }
    //获得通知
    @RequestMapping("/getNotice1")
    public Result getNotice1(){
        List<Notice> noticeList=adminService.getNotice1();
        return Result.succ(noticeList);
    }
    //获得所有留言
    @RequestMapping("/getLeaveMessage")
    public Result getLeaveMessage(){
        List<NoticeUser> noticeList=adminService.getLeaveMessage();
        return Result.succ(noticeList);
    }
    //删除某一条通知
    @RequestMapping("/deleteOneNotice")
    public Result deleteOneNotice(int nid){
        int r=adminService.deleteOneNotice(nid);
        if (r==0)return Result.fail("删除失败！");
        return Result.succ(null);
    }
    //添加消息
    @RequestMapping("/addMessage")
    public Result addMessage(@RequestParam("uid")int uid,@RequestParam("context")String context,@RequestParam("replayUid")int replayUid){
        Date date=new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(date.getTime());
        int r=adminService.addMessage(uid,context,replayUid,time);
        if(r==0)return Result.fail("发送失败！");
        return Result.succ(null);
    }
    //获得所有私信过的用户信息
    @RequestMapping("/getMessageByUid")
    public Result getMessageByUid(int uid){
        List<MessageUser> messageList=adminService.getMessageByUid(uid);
        return Result.succ(messageList);
    }
    //获取与某一用户之间的历史信息
    @RequestMapping("/getMessageByrUid")
    public Result getMessageByrUid(@RequestParam("uid") int uid,@RequestParam("replayUid") int replayUid){
        List<Message> messageList=adminService.getMessageByrUid(uid,replayUid);
        return Result.succ(messageList);
    }
    //获得所有热点
    @RequestMapping("/getAllHots")
    public Result getAllHots(){
        List<Hot> hotList=adminService.getAllHts();
        return Result.succ(hotList);
    }
}
