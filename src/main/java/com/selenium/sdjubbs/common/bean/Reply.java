package com.selenium.sdjubbs.common.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Reply implements Serializable {
    private Integer id;
    private String content;
    private String createTime;
    private Integer commentId;
    private Integer sendUserId;
    private String sendUserName;
    private Integer receiverUserId;
    private String receiverUserName;
    public Reply(){

    }
}
