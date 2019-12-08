package com.wing.java.util.exception;

/**
 * 全局统一返回码
 */
public class ExceptionConstant {

    //失败
    public static final int ERROR = 0;

    //成功
    public static final int SUCCESS = 1;

    public static final String SUCCESS_MESSAGE = "success";

    //token过期
    public static final int TOKEN_EXPIRE = 1001;

    //签名错误
    public static final int SIGN_ERR = 1002;

    //时间戳过期
    public static final int TIMESTAMP_EXPIRE = 1003;

    //权限不够，没有足够权限
    public static final int AUTH_ERR = 1004;

    //参数不能为空
    public static final int NOT_NULL = 1101;

    //参数类型错误
    public static final int CLASS_CAST_EXCEPTION = 1102;

    //乐观锁版本号错误
    public static final int UPDATE_LOCK_VER_EXCEPTION = 1201;

    //枚举列不存在
    public static final int ENUM_COL_NOT_EXISTS = 1202;

    //枚举CODE不存在
    public static final int ENUM_CODE_NOT_EXISTS = 1203;


    /*kakfa-code*/
    public static final int KAFKA_SEND_ERROR_CODE = 2001;
    public static final int KAFKA_NO_RESULT_CODE = 2002;
    public static final int KAFKA_NO_OFFSET_CODE = 2003;

    /*kakfa-message*/
    public static final String KAFKA_SEND_ERROR_MES = "send message timeout";
    public static final String KAFKA_NO_RESULT_MES = "no result message";
    public static final String KAFKA_NO_OFFSET_MES = "no result offset";


}
