package com.selenium.sdjubbs.common.mapper;

import com.selenium.sdjubbs.common.bean.Reply;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReplyMapper {
    Integer addReply(Reply reply);
    List<Reply> getReplyByCommentId(Integer commentId);
    Integer getReplyCount();
}
