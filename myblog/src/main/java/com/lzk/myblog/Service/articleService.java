package com.lzk.myblog.Service;

import com.lzk.myblog.Pojo.Article;
import com.lzk.myblog.Pojo.Comment;
import com.lzk.myblog.Pojo.SearchItem;

import java.util.List;

public interface articleService {
    public Article queryArticleByAid(int aid);
    public List<Article> queryArticleByUid(int uid);
    List<Article> query4ArticleByUid(int uid,int start, int end);
    public List<SearchItem> search(String keyWord);
    public List<Article> queryAllArticle();
    public int addArticle(int uid,String title,String content,String summary,String time,int status);
    public int updateArticle(int aid,String title,String content,String summary,int status);
    int getArticleSumOfUser(int uid);
    int deleteArticle(int aid);
    int addComment(int aid,int uid,String remark,String time, int replayCid,String replayName);
    List<Comment> getCommentByAid(int aid);
}
