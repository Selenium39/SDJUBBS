package com.selenium.sdjubbs.common.mapper;

import com.selenium.sdjubbs.common.bean.Feature;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FeatureMapper {
    List<Feature> getAllFeature();
}
