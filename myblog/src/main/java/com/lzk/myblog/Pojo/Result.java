package com.lzk.myblog.Pojo;

import lombok.Data;

import java.io.Serializable;

@Data
/**
 * 结果统一封装
 */
public class Result  implements Serializable {
    private int code;
    private String msg;
    private Object data;

    //    成功
    public static Result succ(Object data){
        return Result.succ(200,"ok",data);
    }

    public static Result succ(int code,String msg,Object data){
        Result r= new Result();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }
    //    失败
    public static Result fail(String msg){
        return Result.fail(msg,null);
    }
    public static Result fail(String msg,Object data){
        return Result.fail(400,msg,data);
    }
    public static Result fail(int code,String msg,Object data){
        Result r= new Result();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }
}
