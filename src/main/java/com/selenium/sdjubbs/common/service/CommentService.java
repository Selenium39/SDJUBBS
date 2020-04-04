package com.selenium.sdjubbs.common.service;

import com.selenium.sdjubbs.common.bean.Comment;
import com.selenium.sdjubbs.common.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentService implements CommentMapper {
    @Autowired
    private CommentMapper commentMapper;

    @Override
    @Transactional(readOnly = true)
    public List<Comment> getCommentByArticleId(Integer id) {
        return commentMapper.getCommentByArticleId(id);
    }

    @Override
    public Comment getCommentById(Integer id) {
        return commentMapper.getCommentById(id);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Integer addComment(Comment comment) {
        return commentMapper.addComment(comment);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Comment> getAllComment() {
        return commentMapper.getAllComment();
    }

    @Override
    public List<Comment> getAllCommentBySearch(String search) {
        return commentMapper.getAllCommentBySearch(search);
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public Integer deleteComment(Integer id) {
        return commentMapper.deleteComment(id);
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public Integer updateComment(Comment comment) {
        return commentMapper.updateComment(comment);
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public Integer deleteCommentByBatch(List<Integer> ids) {
        return commentMapper.deleteCommentByBatch(ids);
    }

    @Transactional(readOnly = true)
    @Override
    public Integer getReportedCommentCount() {
        return commentMapper.getReportedCommentCount();
    }

}
