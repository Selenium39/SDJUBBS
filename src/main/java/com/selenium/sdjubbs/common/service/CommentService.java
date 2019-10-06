package com.selenium.sdjubbs.common.service;

import com.selenium.sdjubbs.common.bean.Comment;
import com.selenium.sdjubbs.common.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService implements CommentMapper {
    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<Comment> getCommentByArticleId(Integer id) {
        return commentMapper.getCommentByArticleId(id);
    }
}
