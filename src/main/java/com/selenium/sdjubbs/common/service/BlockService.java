package com.selenium.sdjubbs.common.service;

import com.selenium.sdjubbs.common.bean.Block;
import com.selenium.sdjubbs.common.mapper.BlockMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BlockService implements BlockMapper {

    @Autowired
    private BlockMapper blockMapper;

    @Override
    @Transactional(readOnly = true)
    public List<Block> getAllBlock() {
        return blockMapper.getAllBlock();
    }

    @Override
    @Transactional(readOnly = true)
    public Block getBlockById(int id) {
        return blockMapper.getBlockById(id);
    }


}
