package org.bank.constants;

/**
 * @BelongsProject: BankCloud
 * @BelongsPackage: org.bank.constants
 * @Author: lizongle
 * @Description: 常量
 * @Version: 1.0
 */
public class Constant {
    /**
     * 正常token
     */
    public static final String ACCESS_TOKEN="authorization";
    /**
     * 刷新token
     */
    public static final String REFRESH_TOKEN="refresh_token";

    /**
     * 创建时间
     */
    public static final String CREATED = "created";

    /**
     * 权限key
     */
    public static final String JWT_PERMISSIONS_KEY="jwt-permissions-key";

    /**
     * 用户名称 key
     */
    public static final String JWT_USER_NAME="jwt-user-name-key";

    /**
     * 角色key
     */
    public static final String JWT_ROLES_KEY="jwt-roles-key_";

    /**
     * 主动去刷新 token key(适用场景 比如修改了用户的角色/权限去刷新token)
     */
    public static final String JWT_REFRESH_KEY="jwt-refresh-key_";

    /**
     *  刷新状态(适用场景如：一个功能点要一次性请求多个接口，当第一个请求刷新接口时候 加入一个节点下一个接口请求进来的时候直接放行)
     */
    public static final String JWT_REFRESH_STATUS="jwt-refresh-status_";

    /**
     * 标记新的access_token
     */
    public static final String JWT_REFRESH_IDENTIFICATION="jwt-refresh-identification_";

    /**
     * access_token 主动退出后加入黑名单 key
     */
    public static final String JWT_ACCESS_TOKEN_BLACKLIST="jwt-access-token-blacklist_";

    /**
     * refresh_token 主动退出后加入黑名单 key
     */
    public static final String JWT_REFRESH_TOKEN_BLACKLIST="jwt-refresh-token-blacklist_";

    /**
     * 组织机构编码key
     */
    public static final String DEPT_CODE_KEY="dept-code-key_";

    /**
     * 菜单权限编码key
     */
    public static final String PERMISSION_CODE_KEY="permission-code-key_";

    /**
     * 标记用户是否已经删除
     */
    public static final String DELETED_USER_KEY="deleted-user-key_";

    /**
     * 标记用户是否已经被锁定
     */
    public static final String ACCOUNT_LOCK_KEY="account-lock-key_";

    /**
     * 用户权鉴缓存 key
     */
    public static final String IDENTIFY_CACHE_KEY="shiro-cache:org.bank.shiro.CustomRealm.authorizationCache:";


    /**
     * 获取订单号key
     */
    public static final String ORDERNO_CACHE_CONSUME="order_consume_key_";

    /**
     * 商品缓存
     */
    public static final String GOODS_CACHE_KEY="goods_key_";

    /**
     * 商品库存锁定缓存
     */
    public static final String GOODS_STOCK_LOCKED_KEY="goods_stock_locked_key_";


    /**
     * 订单模块商品库存锁定缓存
     */
    public static final String ORDER_GOODS_STOCK_KEY="order_goods_stock_key_";



    /**
     * 账户信息缓存
     */
    public static final String ACCOUNTNO_KEY="accountno_key_";




    //===================redis锁Key================

    /**
     * @description:
     * @author: lizongle
     * @param: 订单库存操作锁
     * @return:
     **/
    public static final String ORDER_STOCK_LOCK_KEY="orderStockLockForGoods_";

    /**
     * @description:
     * @author: lizongle
     * @param: 订单号锁
     * @return:
     **/
    public static final String ORDERNO_LOCK_KEY="orderNoLock_";

    /**
     * @description:
     * @author: lizongle
     * @param: 账户锁
     * @return:
     **/
    public static final String ACCOUNT_BALANCE_LOCK_KEY="account_balance_lock_";


    /**
     * @description:
     * @author: lizongle
     * @param:
     * @return:
     **/
    public static final String GOODS_STOCK_LOCK_KEY="goodsStockLock_";



    //==============rocketMQ===========

    //============mqgroup================
    public static final String ORDER_SERVER_MQ_GROUP="orderGroup";
    public static final String GOODS_SERVER_MQ_GROUP="goodsGroup";
    public static final String SYSTEM_SERVER_MQ_GROUP="systemGroup";

    //============mq  消息topic================

    public static final String ORDER_MQ_TOPIC_KEY="orderTopicMQ";



    public static final String ORDER_DELAY_PAY_TAG_KEY="orderDelayTagMQ";

    public static final String ORDER_PAY_ADD_TAG_KEY="orderPayAddTagMQ";

    public static final String ORDER_PAY_TAG_KEY="orderPayTagMQ";

    public static final String ORDER_CANCEL_TAG_KEY="orderCancelTagMQ";

}
