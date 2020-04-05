package com.selenium.sdjubbs.common.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 内存信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemoryInfo {
    private Long total;
    private Long used;
    private Long free;
}
