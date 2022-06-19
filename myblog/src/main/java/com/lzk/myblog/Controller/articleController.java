package com.lzk.myblog.Controller;

import com.lzk.myblog.Pojo.*;
import com.lzk.myblog.Service.articleService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/article")
public class articleController {
    @Autowired
    private articleService articleService;

    /**
     * 查询某人的文章
     * @param uid
     * @return
     */
    @RequestMapping("/queryArticleByUid")
    public Result queryArticleByUid(int uid){
        List<Article> articles = articleService.queryArticleByUid(uid);
        return Result.succ(articles);
    }

    /**
     * 查询某人的四篇文章
     * @param uid
     * @param start
     * @param end
     * @return
     */
    @RequestMapping("/query4ArticleByUid")
    public Result query4ArticleByUid(@RequestParam("uid")int uid, @RequestParam("start")int start, @RequestParam("end") int end){
        List<Article> articles = articleService.query4ArticleByUid(uid,start,end);
        return Result.succ(articles);
    }

    /**
     * 添加或是修改
     * @param uid
     * @param title
     * @param content
     * @param summary
     * @param status
     * @return
     */
    @RequestMapping("/addArticle")
    public Result addArticle(@RequestParam("uid")int uid,@RequestParam("title")String title,@RequestParam("content") String content,
                             @RequestParam("summary")String summary,@RequestParam("status")int status,@RequestParam("aid")int aid){
        Date date=new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(date.getTime());
        if (aid>0)
            //修改内容
            articleService.updateArticle(aid,title,content,summary,status);
        else
            //添加内容
            articleService.addArticle(uid,title,content,summary,time,status);
        return Result.succ(null);
    }

    /**
     * 查询谋篇文章
     * @param aid
     * @return
     */
    @RequestMapping("/queryArticleByAid")
    public Result queryArticleByAid(int aid){
        Article article = articleService.queryArticleByAid(aid);
        if (article!=null)
            return Result.succ(article);
        return Result.fail("没有该文章~");
    }

    /**
     * 搜索
     * @param keyWord
     * @return
     */
    @RequestMapping("/search")
    public Result search(String keyWord){
        keyWord="%"+keyWord+"%";
        List<SearchItem> search = articleService.search(keyWord);
        if(search.size()==0)return Result.fail("无匹配内容");
        return Result.succ(search);
    }

    /**
     * 获取所有博文
     * @return
     */
    @RequestMapping("/queryAllArticle")
    public Result queryAllArticle(){
        List<Article> articles = articleService.queryAllArticle();
        if(articles.size()==0)return Result.succ(null);
        List<Item> allArticle=new ArrayList<>();
        for (Article article : articles) {
            Item item=new Item(article.getTitle(),article.getAid(),article.getSummary());
            allArticle.add(item);
        }
        return Result.succ(allArticle);
    }

    /**
     * 查询用户的文章数
     * @param uid
     * @return
     */
    @RequestMapping("/getArticleSumOfUser")
    public Result getArticleSumOfUser(int uid){
        int articleSumOfUser = articleService.getArticleSumOfUser(uid);
        if (articleSumOfUser>0)articleSumOfUser=articleSumOfUser%4>0?articleSumOfUser/4+1:articleSumOfUser/4;
        return Result.succ(articleSumOfUser);
    }

    /**
     * 删除文章
     * @param aid
     * @return
     */
    @RequestMapping("/deleteArticle")
    public Result deleteArticle(int aid){
        return Result.succ(articleService.deleteArticle(aid));
    }

    /**
     * 添加评论
     * @param aid
     * @param uid
     * @param remark
     * @param replayCid
     * @param replayName
     * @return
     */
    @RequestMapping("/addComment")
    public Result addComment(@RequestParam("aid")int aid,@RequestParam("uid")int uid,@RequestParam("remark")String remark,
                             @RequestParam("replayCid")int replayCid,@RequestParam("replayName")String replayName){
        Date date=new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(date.getTime());
        int i = articleService.addComment(aid, uid, remark, time, replayCid, replayName);
        if (i>0)
        return Result.succ(null);
        return Result.fail("评论失败");
    }

    /**
     * 获取评论
     * @param aid
     * @return
     */
    @RequestMapping("/getCommentByAid")
    public Result getCommentByAid(int aid){
        List<Comment> commentByAid = articleService.getCommentByAid(aid);
        if (commentByAid.size()==0) Result.fail(null);
        return Result.succ(commentByAid);
    }
}
