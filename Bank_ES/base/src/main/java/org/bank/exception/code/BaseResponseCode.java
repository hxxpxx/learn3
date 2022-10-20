package org.bank.exception.code;


/**
 * @BelongsProject: BankCloud
 * @BelongsPackage: org.bank.exception.code
 * @Author: lizongle
 * @Description:
 * @Version: 1.0
 */
public enum BaseResponseCode implements ResponseCodeInterface {
    /**
     * 这个要和前段约定好
     * 引导用户去登录界面的
     * code=401001 引导用户重新登录
     * code=401002 token 过期刷新token
     * code=401008 无权限访问
     */
    SUCCESS(0,"操作成功"),
    SYSTEM_BUSY(500001, "系统繁忙，请稍候再试"),
    OPERATION_ERRO(500002,"操作失败"),

    TOKEN_PARSE_ERROR(401001, "登录凭证已过期，请重新登录"),
    TOKEN_ERROR(401001, "登录凭证已过期，请重新登录"),
    ACCOUNT_ERROR(401001, "该账号异常，请联系运营人员"),
    ACCOUNT_ISEXIT(488888, "该账号已存在"),
    ACCOUNT_LOCK_ERROR(401001, "该用户已被锁定，请联系运营人员"),
    TOKEN_PAST_DUE(401002, "授权信息已过期，请刷新token"),
    DATA_ERROR(401003,"传入数据异常"),
    NOT_ACCOUNT(401004, "该用户不存在,请先注册"),
    USER_LOCK(401005, "该用户已被锁定，请联系运营人员"),
    PASSWORD_ERROR(401006,"用户名或密码错误"),
    METHODARGUMENTNOTVALIDEXCEPTION(401007, "方法参数校验异常"),
    UNAUTHORIZED_ERROR(401008, "权鉴校验不通过"),
    ROLE_PERMISSION_RELATION(401009, "该菜单权限存在子集关联，不允许删除"),
    OLD_PASSWORD_ERROR(401010,"旧密码不正确"),
    NOT_PERMISSION_DELETED_DEPT(401011,"该组织机构下还关联着用户，不允许删除"),
    OPERATION_MENU_PERMISSION_CATALOG_ERROR(401012,"操作后的菜单类型是目录，所属菜单必须为默认顶级菜单或者目录"),
    OPERATION_MENU_PERMISSION_MENU_ERROR(401013,"操作后的菜单类型是菜单，所属菜单必须为目录类型"),
    OPERATION_MENU_PERMISSION_BTN_ERROR(401013,"操作后的菜单类型是按钮，所属菜单必须为菜单类型"),
    OPERATION_MENU_PERMISSION_UPDATE(401014,"操作的菜单权限存在子集关联不允许变更"),
    OPERATION_MENU_PERMISSION_URL_NOT_NULL(401015,"菜单权限的url不能为空"),
    OPERATION_MENU_PERMISSION_URL_PERMS_NULL(401016,"菜单权限的标识符不能为空"),
    OPERATION_MENU_PERMISSION_URL_METHOD_NULL(401017,"菜单权限的请求方式不能为空"),
    OUTH_TOKEN_ERROR(800001, "access_token必须传递"),
    CLIENT_ISEXIT(600001, "该客户端已存在"),
    PERMISSION_ERROR(800002, "该权限不存在"),
    PERMISSIONS_ISEMPTY_ERROR(800003, "权限集合不可为空"),

    GOODS_ISEXIT(900001,"该商品已存在"),
    GOODS_ISNOTEXIT(900002,"该商品不存在"),
    GOODS_STOCK_IS_OUT(900003,"该商品库存不足"),

    GET_ORDERNO_ERRPR(910002,"获取订单号失败"),
    GET_ORDERNO_AWAIT(910003,"获取订单号失败,请重试"),
    GET_ORDERNO_ISEXIT(910004,"获取订单号已存在,请重新下单"),
    GET_ORDERNO_ISNOTEXIT(910004,"该订单不存在"),
    GET_ORDER_NUM_ERROR(910005,"该订单数量错误，请检查"),
    GET_ORDER_GOODS_ERROR(910006,"该订单商品错误，请检查"),
    GET_ORDER_ADDRESS_ERROR(910006,"该订单收货地址信息错误，请检查"),
    GET_ORDER_CREATE_ERROR(910007,"创建订单错误。"),
    ET_ORDERNO_STOCK_ERRPR(910008,"获取商品库存失败，请稍后重试！"),

    CANCEL_ORDERNO_ERRPR(910009,"取消订单失败，请稍后重试！"),
    PAY_ORDERNO_ERRPR(910010,"订单支付失败！"),
    PAY_ORDERNO_ORDERNO_ERRPR(910011,"订单支付失败，订单号信息错误！"),


    PAY_ORDERNO_INTEGRAL_OP_ERRPR(910012,"用户积分扣除失败，仅允许订单创建者操作！"),
    PAY_ORDERNO_INTEGRAL_SHORTAGE_ERRPR(910013,"用户积分扣除失败，该用户积分不足！"),
    PAY_ORDERNO_INTEGRAL_ERRPR(910014,"用户积分扣除失败,系统错误！"),
    PAY_ORDERNO_INTEGRAL_ADD_ERRPR(910015,"用户积分退还失败！"),
    PAY_ORDER_STOCK_DEDUCT_ERRPR(910016,"库存扣除失败！"),
    PAY_ORDER_STATUS_ERRPR(910017,"订单支付错误，仅允许未支付状态的订单操作！"),
    OP_ORDER_USER_ERROR(910018,"订单非本人不可操作。"),


    CANCLE_ORDER_STATUS_ERROR(910019,"仅支持未支付状态的订单操作。"),
    CANCLE_ORDER_ERROR(910020,"取消订单操作失败。"),




    ES_OPTION_ERROR(920000,"ES操作错误！"),

    ACCOUNT_ADD_ACCOUNTNO_ERROR(910100,"创建账户失败，该账户已存在。"),
    ACCOUNT_ADD_USER_ERROR(910101,"创建账户失败，仅允许给本人创建账户"),
    ACCOUNT_ISEXIT_ERROR(910102,"该账户信息不存在"),
    ACCOUNT_UPDATE_USER_ERROR(910103,"变更账户失败，仅允许给本人变更账户"),
    ACCOUNT_QUERY_USER_ERROR(910104,"查询参数用户信息不可为空"),

    ORDER_ACCOUNT_CHECK_ERROR(910301,"账户校验失败"),
    ORDER_ACCOUNT_BALANCE_DEFICIENCY(910302,"账户余额不足"),
    ORDER_ACCOUNT_GET_LOCK_ERROR(910303,"当前账户操作频繁，请稍后重试"),
    ORDER_ACCOUNT_OPERATION_ERROR(910304,"当前账户操作错误"),
    ORDER_ACCOUNT_BALANCE_DEDUCTION_ERROR(910305,"账户扣款错误，请稍后重试"),


    ACCOUNT_DETAIL_OPERATION_ERROR(9101401,"账户明细信息操作失败！"),
    ;

    /**
     * 错误码
     */
    private final int code;
    /**
     * 错误消息
     */
    private final String msg;

    BaseResponseCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
