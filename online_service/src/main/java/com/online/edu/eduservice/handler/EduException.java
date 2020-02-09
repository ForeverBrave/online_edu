package com.online.edu.eduservice.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Author : Brave
 * @Version : 1.0
 * @Date : 2020/1/31 17:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class EduException extends RuntimeException{

    //状态码
    private Integer code;
    //异常信息
    private String message;

}
