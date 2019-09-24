package com.selenium.sdjubbs.common.util;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wantao
 * @date 2019-02-13 21:53
 * @description:json的通用返回类
 */
@Data
@NoArgsConstructor
public class Result {
    // 200 成功 201 失败
    private Integer code;
    //具体的错误码
    private Integer errorCode=0;
    private String msg="";
    private String reason="";
    // 服务器要返回给浏览器的数据
    private Map<String, Object> data = new HashMap<String, Object>();

    public static Result success() {
        Result result = new Result();
        result.setCode(Constant.SUCCESS_CODE);
        result.setMsg(Constant.SUCCESS);
        return result;
    }

    public static Result failure() {
        Result result = new Result();
        result.setCode(Constant.FAILURE_CODE);
        result.setMsg(Constant.FAILURE);
        return result;
    }

    public static Result failure( String reason) {
        Result result = new Result();
        result.setCode(Constant.FAILURE_CODE);
        result.setMsg(Constant.FAILURE);
        result.setReason(reason);
        return result;
    }

    public static Result failure(Integer code, String reason) {
        Result result = new Result();
        result.setCode(Constant.FAILURE_CODE);
        result.setErrorCode(code);
        result.setMsg(Constant.FAILURE);
        result.setReason(reason);
        return result;
    }
    public static Result failure(Integer code, Integer errorCode,String reason) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(Constant.FAILURE);
        result.setReason(reason);
        result.setErrorCode(errorCode);
        return result;
    }

    public Result add(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

}
