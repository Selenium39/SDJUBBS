package com.selenium.sdjubbs.common.mapper;

import com.selenium.sdjubbs.common.bean.Article;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ArticleMapper {

    Integer addArticle(Article article);

    Article getArticleById(int id);

    List<Article> getAllArticleByBlockId(int blockId);

    List<Article> getAllArticle();

    Integer updateArticle(Article article);

    Integer deleteArticle(Integer id);

    Integer deleteArticleByBatch(List<Integer> ids);

}
