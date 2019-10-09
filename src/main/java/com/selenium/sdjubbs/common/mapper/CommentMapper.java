package com.selenium.sdjubbs.common.mapper;

import com.selenium.sdjubbs.common.bean.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
    List<Comment> getCommentByArticleId(Integer id);
    Integer addComment(Comment comment);
}
