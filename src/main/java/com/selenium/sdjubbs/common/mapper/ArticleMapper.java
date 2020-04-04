package com.selenium.sdjubbs.common.mapper;

import com.selenium.sdjubbs.common.bean.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ArticleMapper {

    Integer addArticle(Article article);

    Article getArticleById(int id);

    List<Article> getAllArticleByBlockId(int blockId);

    List<Article> getAllArticleByBlockIdAndSearch(@Param("blockId") int blockId, @Param("search") String search);

    List<Article> getAllArticle();

    List<Article> getAllArticleBySearch(String search);

    Integer updateArticle(Article article);

    Integer addArticleSeeNum(int id);

    Integer deleteArticle(Integer id);

    Integer deleteArticleByBatch(List<Integer> ids);


}
