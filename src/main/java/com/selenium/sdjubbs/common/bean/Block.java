package com.selenium.sdjubbs.common.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * 板块
 */
@Data
public class Block implements Serializable {
    private Integer id;
    private String BlockPicture;
    private String title;
    private Integer authorId;
    private String authorName;
    private Integer articleNum;
    private Integer saveNum;
    private String createTime;
}
