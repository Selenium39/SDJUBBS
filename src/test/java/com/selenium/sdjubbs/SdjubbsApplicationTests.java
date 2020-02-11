package com.selenium.sdjubbs;

import com.selenium.sdjubbs.common.bean.Article;
import com.selenium.sdjubbs.common.bean.Comment;
import com.selenium.sdjubbs.common.bean.Reply;
import com.selenium.sdjubbs.common.bean.User;
import com.selenium.sdjubbs.common.service.ArticleService;
import com.selenium.sdjubbs.common.service.CommentService;
import com.selenium.sdjubbs.common.service.ReplyService;
import com.selenium.sdjubbs.common.service.UserService;
import com.selenium.sdjubbs.common.util.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SdjubbsApplicationTests {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private ReplyService replyService;
    @Autowired
    private UserService userService;

    @Test
    public void insertArticle() {
        for (int i = 0; i < 20; i++) {
            Article article = new Article();
            article.setTitle("文章标题" + UUID.randomUUID().toString().substring(0, 6));
            article.setContent("文章内容" + UUID.randomUUID().toString().substring(0, 10));
            article.setAuthorId(16);
            article.setAuthorName("selenium");
            article.setBlockId(5);
            article.setBlockName("1111");
            article.setCreateTime(TimeUtil.getTime());
            article.setPriority(0);
            articleService.addArticle(article);

        }
    }

    @Test
    public void insertReply() {
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

    @Test
    public void createVerifyCode() throws IOException {
        VerifyCodeUtil.drawVerifyCode(200, 50, System.getProperty("user.dir") + "\\src\\main\\resources\\static\\common\\images\\verifycode", "test");
    }

    @Test
    public void updateUser() {
        User user = new User();
        user.setId(50);
        user.setStatus(0);
        userService.updateUser(user);
    }

    @Test
    public void deleteUserByBatch() {
        List<Integer> ids = new ArrayList<>();
        ids.add(41);
        userService.deleteUserByBatch(ids);
    }

    @Test
    public void addUser() {
        for (int i = 0; i < 1000; i++) {
            User user = new User();
            user.setUsername(UUID.randomUUID().toString().substring(0, 7));
            user.setStatus(0);
            user.setRole(0);
            user.setPassword("8888888888888");
            user.setSalt("888888");
            user.setLastLoginTime(TimeUtil.getTime());
            user.setRegisterTime(TimeUtil.getTime());
            user.setEmail(UUID.randomUUID().toString().substring(0, 7) + "@qq.com");
            user.setAge(0);
            user.setHeadPicture(Constant.DEFAULT_HEAD_PICTURE);
            userService.addUser(user);
        }
    }

    @Test
    public void spider() {
        SpiderUtil.getIndexNews();
    }

}
