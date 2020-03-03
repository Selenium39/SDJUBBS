package com.selenium.sdjubbs.common.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private Integer id;
    private String content;
    private String time;
    private Integer status;
}
