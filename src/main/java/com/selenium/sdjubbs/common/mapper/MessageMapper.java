package com.selenium.sdjubbs.common.mapper;

import com.selenium.sdjubbs.common.bean.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MessageMapper {

    List<Message> getAllMessage();

    //后端获取留言可根据其它字段排序，前端获取留言只能根据time排序
    List<Message> getAllMessageForAdmin();

    Integer addMessage(Message message);

    Integer updateMessage(Message message);

    Integer deleteMessage(Integer id);

    Integer deleteMessageByBatch(List<Integer> ids);
}
