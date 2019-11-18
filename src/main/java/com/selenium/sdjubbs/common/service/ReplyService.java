package com.selenium.sdjubbs.common.service;

import com.selenium.sdjubbs.common.bean.Reply;
import com.selenium.sdjubbs.common.mapper.ReplyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReplyService implements ReplyMapper {
    @Autowired
    ReplyMapper replyMapper;

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Integer addReply(Reply reply) {
        return replyMapper.addReply(reply);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Reply> getReplyByCommentId(Integer commentId) {
        return replyMapper.getReplyByCommentId(commentId);
    }
}
