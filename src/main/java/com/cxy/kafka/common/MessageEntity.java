package com.cxy.kafka.common;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @Auther: cxy
 * @Date: 2019/4/9
 * @Description:
 */
@Getter
@Setter
@EqualsAndHashCode
public class MessageEntity {
    private String title;
    private String body;

    @Override
    public String toString(){
        return "MessageEntity{" +
                "title='" + title + '\'' +
                ",body='" + body + '\'' +
                '}';
    }
}
