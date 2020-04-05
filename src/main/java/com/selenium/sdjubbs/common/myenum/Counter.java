package com.selenium.sdjubbs.common.myenum;

import lombok.Data;


public enum Counter {
    USER("用户数量"), ARTICLE("文章数量"), BLOCK("板块数量"), COMMENT("评论数量"), MESSAGE("留言数量"), REPLEY("回复数量");
    private String name;

    Counter(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
