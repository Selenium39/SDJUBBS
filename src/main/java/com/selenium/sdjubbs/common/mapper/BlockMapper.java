package com.selenium.sdjubbs.common.mapper;

import com.selenium.sdjubbs.common.bean.Block;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BlockMapper {
     List<Block> getAllBlock();
     Block getBlockById(int id);
}
