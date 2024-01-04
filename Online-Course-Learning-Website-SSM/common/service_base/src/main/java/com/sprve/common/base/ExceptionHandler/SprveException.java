package com.sprve.common.base.ExceptionHandler;
import com.sprve.common.utils.ResultCodeEnum;
import lombok.Data;
@Data
public class SprveException extends RuntimeException{
    private Integer code;
    private String msg;
    public SprveException(Integer code, String msg) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }
    public SprveException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMsg());
        this.msg = resultCodeEnum.getMsg();
        this.code = resultCodeEnum.getCode();
    }
}
