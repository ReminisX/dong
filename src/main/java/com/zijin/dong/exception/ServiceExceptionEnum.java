package com.zijin.dong.exception;

/**
 * 服务异常
 * <p>
 * 参考 <a href="https://www.kancloud.cn/onebase/ob/484204">...</a> 文章
 * <p>
 * 一共 10 位，分成四段
 * <p>
 * 第一段，1 位，类型
 *      1 - 业务级别异常
 *      2 - 系统级别异常
 * <p>
 * 第二段，3 位，系统类型
 *      001 - 用户系统
 *      002 - 商品系统
 *      003 - 订单系统
 *      004 - 支付系统
 *      005 - 优惠劵系统
 *      ... - ...
 * <p>
 * 第三段，3 位，模块
 *      不限制规则。
 *      一般建议，每个系统里面，可能有多个模块，可以再去做分段。以用户系统为例子：
 *          001 - OAuth2 模块
 *          002 - User 模块
 *          003 - MobileCode 模块
 * <p>
 * 第四段，3 位，错误码
 *       不限制规则。
 *       一般建议，每个模块自增。
 */
public enum ServiceExceptionEnum {

    // ===== 系统级别 =====
    SUCCESS("0", "成功"),
    SYS_ERROR("2001001000", "服务端发生异常"),
    MISSING_REQUEST_PARAM_ERROR("2001001001", "参数缺失"),

    // ==== 用户模块 =====
    USER_NOT_FOUND("1001002000", "用户不存在");


    private String code;

    private String message;

    ServiceExceptionEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

}
