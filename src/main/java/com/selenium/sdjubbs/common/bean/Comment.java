package com.selenium.sdjubbs.common.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Comment {
    private Integer id;
    private String content;
    private String createTime;
    private Integer articleId;
    private Integer userId;
    private String userName;

    public Comment() {

    }
}
