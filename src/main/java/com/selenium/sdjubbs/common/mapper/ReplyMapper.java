package com.selenium.sdjubbs.common.mapper;

import com.selenium.sdjubbs.common.bean.Reply;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReplyMapper {
    Integer addReply(Reply reply);
}
