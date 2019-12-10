package com.wing.java.util.exception;

/**
 * 全局统一返回信息
 */
public interface ExceptionConstant {

    /**
     * HTTP 请求 错误编码
     */
    int ERROR = 0;          //通用错误编码
    int SUCCESS = 1;        //成功编码
    int SC_CONTINUE = 100;
    int SC_SWITCHING_PROTOCOLS = 101;
    int SC_PROCESSING = 102;
    int SC_OK = 200;
    int SC_CREATED = 201;
    int SC_ACCEPTED = 202;
    int SC_NON_AUTHORITATIVE_INFORMATION = 203;
    int SC_NO_CONTENT = 204;
    int SC_RESET_CONTENT = 205;
    int SC_PARTIAL_CONTENT = 206;
    int SC_MULTI_STATUS = 207;
    int SC_MULTIPLE_CHOICES = 300;
    int SC_MOVED_PERMANENTLY = 301;
    int SC_MOVED_TEMPORARILY = 302;
    int SC_SEE_OTHER = 303;
    int SC_NOT_MODIFIED = 304;
    int SC_USE_PROXY = 305;
    int SC_TEMPORARY_REDIRECT = 307;
    int SC_BAD_REQUEST = 400;
    int SC_UNAUTHORIZED = 401;  //身份认证失败
    int SC_PAYMENT_REQUIRED = 402;
    int SC_FORBIDDEN = 403;     //权限不足
    int SC_NOT_FOUND = 404;
    int SC_METHOD_NOT_ALLOWED = 405;
    int SC_NOT_ACCEPTABLE = 406;
    int SC_PROXY_AUTHENTICATION_REQUIRED = 407;
    int SC_REQUEST_TIMEOUT = 408;
    int SC_CONFLICT = 409;
    int SC_GONE = 410;
    int SC_LENGTH_REQUIRED = 411;
    int SC_PRECONDITION_FAILED = 412;
    int SC_REQUEST_TOO_LONG = 413;
    int SC_REQUEST_URI_TOO_LONG = 414;
    int SC_UNSUPPORTED_MEDIA_TYPE = 415;
    int SC_REQUESTED_RANGE_NOT_SATISFIABLE = 416;
    int SC_EXPECTATION_FAILED = 417;
    int SC_INSUFFICIENT_SPACE_ON_RESOURCE = 419;
    int SC_METHOD_FAILURE = 420;
    int SC_UNPROCESSABLE_ENTITY = 422;
    int SC_LOCKED = 423;
    int SC_FAILED_DEPENDENCY = 424;
    int SC_INTERNAL_SERVER_ERROR = 500;
    int SC_NOT_IMPLEMENTED = 501;
    int SC_BAD_GATEWAY = 502;
    int SC_SERVICE_UNAVAILABLE = 503;
    int SC_GATEWAY_TIMEOUT = 504;
    int SC_HTTP_VERSION_NOT_SUPPORTED = 505;
    int SC_INSUFFICIENT_STORAGE = 507;

    /**
     * 错误提示
     */
    String SUCCESS_MSG = "success";
    String ERROR_MSG = "请求失败，请联系管理员";

    /**
     * 业务错误编码
     */
    int SIGN_ERROR = 100001;                   //签名错误
    int NOT_NULL = 100002;                    //参数不能为空
    int UPDATE_LOCK_VER_EXCEPTION = 100003;   //乐观锁版本号错误
    int ENUM_COL_NOT_EXISTS = 100004;         //枚举列不存在
    int ENUM_CODE_NOT_EXISTS = 100005;        //枚举CODE不存在

    int CLASS_CAST_EXCEPTION = 100101;        //参数类型错误

    /**
     * kafka
     */
    int KAFKA_SEND_ERROR_CODE = 100201;
    int KAFKA_NO_RESULT_CODE = 100202;
    int KAFKA_NO_OFFSET_CODE = 100203;

    String KAFKA_SEND_ERROR_MES = "send message timeout";
    String KAFKA_NO_RESULT_MES = "no result message";
    String KAFKA_NO_OFFSET_MES = "no result offset";

}
