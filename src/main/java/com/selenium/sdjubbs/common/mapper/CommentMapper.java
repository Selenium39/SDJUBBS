package com.selenium.sdjubbs.common.mapper;

import com.selenium.sdjubbs.common.bean.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
    List<Comment> getCommentByArticleId(Integer id);

    Comment getCommentById(Integer id);

    Integer addComment(Comment comment);

    List<Comment> getAllComment();

    List<Comment> getAllCommentBySearch(String search);

    Integer deleteComment(Integer id);

    Integer updateComment(Comment comment);

    Integer deleteCommentByBatch(List<Integer> ids);

    Integer getReportedCommentCount();

}
