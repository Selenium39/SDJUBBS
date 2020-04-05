package com.selenium.sdjubbs.common.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CPU信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CPUInfo {
    //可能有多个cpu
    private Integer id;
    private String userUsed;
    private String systemUsed;
    private String wait;
    private String error;
    private String free;
}

