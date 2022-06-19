package com.lzk.myblog.Mapper;

import com.lzk.myblog.Pojo.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface adminMapper {
    //添加通知/公告
    @Insert("insert into notice(uid,title,context,status,time) values(#{uid},#{title},#{context},#{status},#{time}) ")
    int addNotice(@Param("uid")int uid, @Param("title")String title, @Param("context")String context,
                  @Param("status")int status,@Param("time")String time);
    //查询所有通知
    @Select("select * from notice where status=0 or status=1")
    List<Notice> getAllNotice();
    //获得最新的公告
    @Select("select * from notice where status=0 ORDER BY nid DESC limit 0,1")
    Notice getNewNotice();
    //获得所有通知
    @Select("select * from notice where status=1")
    List<Notice> getNotice1();
    //获得所有留言
    @Select("select nid,context,avatar,nickName,time from notice join user on user.uid=notice.uid where notice.status=2")
    List<NoticeUser>getLeaveMessage();
    //删除某一篇通知
    @Delete("delete from notice where nid=#{nid}")
    int deleteOneNotice(int nid);
    //添加消息
    @Insert("insert into message(uid,context,replayUid,time) values(#{uid},#{context},#{replayUid},#{time})")
    int addMessage(@Param("uid")int uid,@Param("context")String context,@Param("replayUid")int replayUid,@Param("time")String time);
    //查询某用户联系过的消息
    @Select("select nickName,user.uid,mid,context,avatar,message.status from message join user on user.uid=message.replayUid where message.uid=#{uid} group by message.replayUid")
    List<MessageUser> getMessageByUid(int uid);
    //获得和某个用户的历史交流信息
    @Select("select * from message  where (uid =#{uid} and replayUid=#{replayUid}) or (uid =#{replayUid} and replayUid=#{uid}) order by mid asc")
    List<Message>getMessageByrUid(@Param("uid") int uid,@Param("replayUid") int replayUid);
    //获得所有热点
    @Select("select * from hot")
    List<Hot> getAllHts();
}
