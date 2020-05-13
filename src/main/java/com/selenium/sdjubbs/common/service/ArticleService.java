package com.selenium.sdjubbs.common.service;

import com.selenium.sdjubbs.common.bean.Article;
import com.selenium.sdjubbs.common.bean.TopArticleInfo;
import com.selenium.sdjubbs.common.mapper.ArticleMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class ArticleService implements ArticleMapper {
    @Autowired
    private ArticleMapper articleMapper;

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Integer addArticle(Article article) {
        return articleMapper.addArticle(article);
    }

    @Override
    @Transactional(readOnly = true)
    public Article getArticleById(int id) {
        return articleMapper.getArticleById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Article> getAllArticleByBlockId(int blockId) {
        return articleMapper.getAllArticleByBlockId(blockId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Article> getAllArticleByBlockIdAndSearch(@Param("blockId") int blockId, @Param("search") String search) {
        return articleMapper.getAllArticleByBlockIdAndSearch(blockId, search);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Article> getAllArticle() {
        return articleMapper.getAllArticle();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Article> getAllArticleBySearch(String search) {
        return articleMapper.getAllArticleBySearch(search);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TopArticleInfo> getTopArticle(int top) {
        return articleMapper.getTopArticle(top);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Integer updateArticle(Article article) {
        return articleMapper.updateArticle(article);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Integer addArticleSeeNum(int id) {
        return articleMapper.addArticleSeeNum(id);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Integer deleteArticle(Integer id) {
        return articleMapper.deleteArticle(id);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Integer deleteArticleByBatch(List<Integer> ids) {
        return articleMapper.deleteArticleByBatch(ids);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer getArticleCount() {
        return articleMapper.getArticleCount();
    }


}
