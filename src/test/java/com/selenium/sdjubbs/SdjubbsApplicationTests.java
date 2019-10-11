package com.selenium.sdjubbs;

import com.selenium.sdjubbs.common.bean.Article;
import com.selenium.sdjubbs.common.bean.Comment;
import com.selenium.sdjubbs.common.bean.Reply;
import com.selenium.sdjubbs.common.service.ArticleService;
import com.selenium.sdjubbs.common.service.CommentService;
import com.selenium.sdjubbs.common.service.ReplyService;
import com.selenium.sdjubbs.common.util.TimeUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Time;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SdjubbsApplicationTests {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private ReplyService replyService;

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

    @Test
    public void insertReply(){
        Reply reply = new Reply();
        reply.setContent("111");
        reply.setCommentId(1);
        reply.setCreateTime(TimeUtil.getTime());
        reply.setReceiverUserId(16);
        reply.setReceiverUserName("selenium");
        reply.setSendUserId(37);
        reply.setSendUserName("zhangxiya");
        replyService.addReply(reply);
    }
}
