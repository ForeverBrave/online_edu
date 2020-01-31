package com.online.edu.eduservice.handler;

import com.online.edu.common.R;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author : Brave
 * @Version : 1.0
 * @Date : 2020/1/31 16:58
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    //1、对所有的异常进行相同的处理
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e){
        e.printStackTrace();
        return R.error().message("出现了异常！！！！！！");
    }

    //2、对特定的异常进行处理 ArithmeticException
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public R error(ArithmeticException e){
        e.printStackTrace();
        return R.error().message("0不能是为除数，出现了异常！！！！！！");
    }

    //3、自定义异常
    @ExceptionHandler(EduException.class)
    @ResponseBody
    public R error(EduException e){
        e.printStackTrace();
        return R.error().message(e.getMessage()).code(e.getCode());
    }
}
