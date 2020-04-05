package com.selenium.sdjubbs.common.service;

import com.selenium.sdjubbs.common.bean.Message;
import com.selenium.sdjubbs.common.mapper.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MessageService implements MessageMapper {
    @Autowired
    private MessageMapper messageMapper;

    @Transactional(readOnly = true)
    @Override
    public List<Message> getAllMessage() {
        return messageMapper.getAllMessage();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Message> getAllMessageForAdmin() {
        return messageMapper.getAllMessageForAdmin();
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer addMessage(Message message) {
        return messageMapper.addMessage(message);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer updateMessage(Message message) {
        return messageMapper.updateMessage(message);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer deleteMessage(Integer id) {
        return messageMapper.deleteMessage(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer deleteMessageByBatch(List<Integer> ids) {
        return messageMapper.deleteMessageByBatch(ids);
    }

    @Transactional(readOnly = true)
    @Override
    public Integer getNewMessageCount() {
        return messageMapper.getNewMessageCount();
    }

    @Override
    @Transactional(readOnly = true)
    public Integer getMessageCount() {
        return messageMapper.getMessageCount();
    }
}
