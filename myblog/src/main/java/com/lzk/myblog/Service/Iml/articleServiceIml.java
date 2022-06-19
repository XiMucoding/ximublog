package com.lzk.myblog.Service.Iml;

import com.lzk.myblog.Mapper.articleMapper;
import com.lzk.myblog.Pojo.Article;
import com.lzk.myblog.Pojo.Comment;
import com.lzk.myblog.Pojo.SearchItem;
import com.lzk.myblog.Service.articleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class articleServiceIml implements articleService {
    @Autowired
    private articleMapper articleMapper;

    @Override
    public Article queryArticleByAid(int aid) {
        return articleMapper.queryArticleByAid(aid);
    }

    @Override
    public  List<Article> queryArticleByUid(int uid) {
        return articleMapper.queryArticleByUid(uid);
    }

    @Override
    public List<Article> query4ArticleByUid(int uid,int start, int end) {
        return articleMapper.query4ArticleByUid(uid,start,end);
    }

    @Override
    public List<SearchItem> search(String keyWord) {
        return articleMapper.search(keyWord);
    }

    @Override
    public List<Article> queryAllArticle() {
        return articleMapper.queryAllArticle();
    }

    @Override
    public int addArticle(int uid, String title, String content, String summary, String time, int status) {
        return articleMapper.addArticle(uid,title,content,summary,time,status);
    }

    @Override
    public int updateArticle(int aid, String title, String content, String summary, int status) {
        return articleMapper.updateArticle(aid,title,content,summary,status);
    }

    @Override
    public int getArticleSumOfUser(int uid) {
        return articleMapper.getArticleSumOfUser(uid);
    }

    @Override
    public int deleteArticle(int aid) {
        return articleMapper.deleteArticle(aid);
    }

    @Override
    public int addComment(int aid, int uid, String remark, String time, int replayCid, String replayName) {
        return articleMapper.addComment(aid,uid,remark,time,replayCid,replayName);
    }

    @Override
    public List<Comment> getCommentByAid(int aid) {
        return articleMapper.getCommentByAid(aid);
    }
}
