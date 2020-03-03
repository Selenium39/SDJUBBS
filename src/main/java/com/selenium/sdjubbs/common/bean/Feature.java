package com.selenium.sdjubbs.common.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Feature {
    private Integer id;
    private String featureName;
    private String info;
    private String frontImagePath;
    private String backImagePath;
    private String path;
}
