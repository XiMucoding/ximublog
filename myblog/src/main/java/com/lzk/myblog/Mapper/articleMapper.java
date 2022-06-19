package com.lzk.myblog.Mapper;

import com.lzk.myblog.Pojo.Article;
import com.lzk.myblog.Pojo.Comment;
import com.lzk.myblog.Pojo.SearchItem;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface articleMapper {
    //查询谋篇文章
    @Select("select * from article where aid=#{aid}")
    Article queryArticleByAid(int aid);
    //查询某人的所有文章
    @Select("select * from article where uid=#{uid}")
    List<Article> queryArticleByUid(int uid);
    //查询某人的4篇文章
    @Select("select * from article where uid=#{uid} limit #{start},#{end}")
    List<Article> query4ArticleByUid(@Param("uid")int uid,@Param("start")int start,@Param("end") int end);
    @Select("select * from article where status=1")
    List<Article> queryAllArticle();
    //查询某人的文章
    @Select("select count(aid) from article where uid=#{uid}")
    int getArticleSumOfUser(int uid);
    //添加文章
    @Insert("Insert into article(uid,title,content,summary,time,status) values(#{uid},#{title},#{content},#{summary},#{time},#{status})")
    int addArticle(@Param("uid")int uid, @Param("title")String title,@Param("content") String content,
    @Param("summary")String summary, @Param("time")String time, @Param("status")int status);
    //更新文章
    @Update("update article set title=#{title},content=#{content},summary=#{summary},status=#{status} where aid=#{aid}")
    int updateArticle(@Param("aid") int aid, @Param("title")String title,@Param("content") String content,
                      @Param("summary")String summary, @Param("status")int status);
    @Select("select aid,title,summary,time,nickName from article JOIN user on article.uid=user.uid where (title like #{keyWord} or summary like #{keyWord}) and article.status=1")
    List<SearchItem> search(String keyWord);
    //删除谋篇文章
    @Delete("delete from article where aid=#{aid}")
    int deleteArticle(int aid);
    //添加评论
    @Insert("insert into comment(aid,uid,remark,time,replayCid,replayName) values(#{aid},#{uid},#{remark},#{time},#{replayCid},#{replayName})")
    int addComment(@Param("aid")int aid,@Param("uid")int uid,@Param("remark")String remark,@Param("time")String time,
                   @Param("replayCid")int replayCid,@Param("replayName")String replayName);
    //查询某篇文章的评论
    @Select("select cid,aid,user.uid,remark,time,replayCid,replayName,avatar,nickName from comment join user on comment.uid=user.uid where aid=#{aid}")
    List<Comment>getCommentByAid(int aid);
}
