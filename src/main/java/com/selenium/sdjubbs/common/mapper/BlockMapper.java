package com.selenium.sdjubbs.common.mapper;

import com.selenium.sdjubbs.common.bean.Block;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BlockMapper {
    List<Block> getAllBlock();

    List<Block> getAllBlockBySearch(String search);

    List<Block> getAllBlockForUser();

    Block getBlockById(int id);

    Integer updateBlock(Block block);

    Integer addBlock(Block block);

    Integer getBlockCount();
}
