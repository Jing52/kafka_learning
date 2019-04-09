package com.cxy.kafka.common;

import lombok.Getter;
import lombok.Setter;

/**
 * @Auther: cxy
 * @Date: 2019/4/9
 * @Description:
 */
@Getter
@Setter
public class Response {
    private int code;
    private String message;

    public Response(int code, String message){
        this.code = code;
        this.message = message;
    }
}
