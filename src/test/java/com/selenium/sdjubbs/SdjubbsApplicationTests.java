package com.selenium.sdjubbs;

import com.selenium.sdjubbs.common.bean.Article;
import com.selenium.sdjubbs.common.service.ArticleService;
import com.selenium.sdjubbs.common.util.TimeUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SdjubbsApplicationTests {

    @Autowired
    private ArticleService articleService;

    @Test
    public void insertArticle(){
          for(int i=0;i<1000;i++){
              Article article=new Article();
              article.setTitle("文章标题"+UUID.randomUUID().toString().substring(0,6));
              article.setContent("文章内容"+UUID.randomUUID().toString().substring(0,10));
              article.setAuthorId(16);
              article.setAuthorName("selenium");
              article.setBlockId(1);
              article.setBlockName("1111");
              article.setCreateTime(TimeUtil.getTime());
              article.setPriority(0);
              articleService.addArticle(article);

          }
    }
}
