package com.yin.weather.monitor.common;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 返回数据
 *
 * @author yin.weilong
 * @date 2018.11.09
 */
@Getter
@Setter
@Builder
public class BaseMessage implements Serializable {

    private Integer code;
    private String message;
    private Object data;

    public static BaseMessage success() {
        return BaseMessage.builder().code(0).message("操作成功").build();
    }

    public static BaseMessage success(String message) {
        return BaseMessage.builder().code(0).message(message).build();
    }

    public static BaseMessage success(Object data) {
        return BaseMessage.builder().code(0).message("操作成功").data(data).build();
    }

    public static BaseMessage success(String message, Object data) {
        return BaseMessage.builder().code(0).message(message).data(data).build();
    }


    public static BaseMessage error(String message) {
        return BaseMessage.builder().code(1).message(message).build();
    }
}
