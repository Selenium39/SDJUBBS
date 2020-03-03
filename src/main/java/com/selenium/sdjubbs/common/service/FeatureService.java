package com.selenium.sdjubbs.common.service;

import com.selenium.sdjubbs.common.bean.Feature;
import com.selenium.sdjubbs.common.mapper.FeatureMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FeatureService implements FeatureMapper {
    @Autowired
    private FeatureMapper featureMapper;

    @Transactional(readOnly = true)
    @Override
    public List<Feature> getAllFeature() {
        return featureMapper.getAllFeature();
    }
}
