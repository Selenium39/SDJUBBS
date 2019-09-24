package com.selenium.sdjubbs.common.service;

import com.selenium.sdjubbs.common.bean.Article;
import com.selenium.sdjubbs.common.mapper.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
