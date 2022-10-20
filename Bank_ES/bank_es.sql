/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50727
 Source Host           : localhost:3306
 Source Schema         : bank_es

 Target Server Type    : MySQL
 Target Server Version : 50727
 File Encoding         : 65001

 Date: 01/08/2022 16:30:16
*/
use database bank_es;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '持卡人',
  `account_no` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '账号',
  `balance` decimal(16, 4) DEFAULT NULL COMMENT '余额',
  `createtime` datetime(0) DEFAULT NULL COMMENT '创建日期',
  `updatetime` datetime(0) DEFAULT NULL COMMENT '更新日期',
  `deleted` tinyint(2) DEFAULT 1 COMMENT '是否删除(1未删除；0已删除)删除标志',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '账户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES (1, 'fcf34b56-a7a2-4719-9236-867495e74c31', '643518441325', 70000.0000, '2022-07-26 17:36:14', '2022-08-01 15:18:17', 1);
INSERT INTO `account` VALUES (2, 'fcf34b56-a7a2-4719-9236-867495e74c31', '123', 0.0000, '2022-07-26 17:56:49', '2022-07-27 15:43:19', 0);
INSERT INTO `account` VALUES (3, 'fcf34b56-a7a2-4719-9236-867495e74c31', '26468415', 4535475.0000, '2022-07-28 15:15:40', '2022-08-01 15:19:13', 1);

-- ----------------------------
-- Table structure for account_detail
-- ----------------------------
DROP TABLE IF EXISTS `account_detail`;
CREATE TABLE `account_detail`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account_no` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '账号',
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '持卡人名称',
  `title` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '交易名称',
  `type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '交易类型(deduction:扣款；refund:退款)',
  `amount` decimal(16, 4) DEFAULT NULL COMMENT '交易金额',
  `balance` decimal(16, 4) DEFAULT NULL COMMENT '交易后余额',
  `order_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '交易单号',
  `createtime` datetime(0) DEFAULT NULL COMMENT '交易时间',
  `deleted` tinyint(2) DEFAULT 1 COMMENT '是否删除(1未删除；0已删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '交易明细表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for address
-- ----------------------------
DROP TABLE IF EXISTS `address`;
CREATE TABLE `address`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `contacts` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '联系人',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '联系电话',
  `address` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '地址',
  `deleted` tinyint(4) DEFAULT 1 COMMENT '是否删除(1未删除；0已删除)',
  `is_default` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '是否默认地址',
  `user_id` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '地址归属人',
  `createtime` datetime(0) DEFAULT NULL COMMENT '创建日期',
  `updatetime` datetime(0) DEFAULT NULL COMMENT '变更时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '收货地址表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of address
-- ----------------------------
INSERT INTO `address` VALUES (1, '李三', '1345546xxx1', '北京市东城区123街道', 1, '0', 'fcf34b56-a7a2-4719-9236-867495e74c31', '2022-07-16 14:06:44', '2022-07-16 14:16:22');
INSERT INTO `address` VALUES (2, '李四', '1245432153', '山东省济南市123', 1, '1', 'fcf34b56-a7a2-4719-9236-867495e74c31', '2022-07-16 14:14:20', '2022-07-16 14:20:29');
INSERT INTO `address` VALUES (3, '王五', '13246', 'ij', 0, '1', 'fcf34b56-a7a2-4719-9236-867495e74c31', '2022-07-16 14:16:22', '2022-07-16 14:20:24');

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods`  (
  `id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '商品名称',
  `describe` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '商品描述',
  `price` decimal(10, 2) DEFAULT NULL COMMENT '单价',
  `stock` int(10) DEFAULT NULL COMMENT '库存',
  `deleted` tinyint(4) DEFAULT 1 COMMENT '是否删除(1未删除；0已删除)',
  `createtime` datetime(0) DEFAULT NULL COMMENT '创建日期',
  `updatetime` datetime(0) DEFAULT NULL COMMENT '更新日期',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '商品表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES ('goods_56811ac9-f866-4cf1-9083-605dc384c89d', '苹果12 银白色 64G', '苹果12 银白色 64G 6英寸', 10000.00, 1, 1, '2022-07-16 15:56:59', '2022-08-01 15:01:12');
INSERT INTO `goods` VALUES ('goods_5860e383-8df1-4d7b-8c43-773c9cb211ba', '联系拯救者', '123', 50000.00, 15, 1, '2022-08-01 13:28:54', '2022-08-01 15:19:14');
INSERT INTO `goods` VALUES ('goods_64f164bf-0ee3-4d29-b9fe-4817925922f0', '苹果12 黑色 128G', '苹果12 黑色 128G 6英寸', 5000.00, 53, 1, '2022-07-16 16:01:38', '2022-08-01 13:40:20');

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`  (
  `order_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '订单号',
  `goods_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '商品ID',
  `account_no` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '付款账户',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '商品名称',
  `num` int(11) DEFAULT NULL COMMENT '数量',
  `price` decimal(10, 2) DEFAULT NULL COMMENT '单价',
  `total_amout` decimal(10, 2) DEFAULT NULL COMMENT '总价',
  `user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户ID',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '订单状态',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `createtime` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `paytime` datetime(0) DEFAULT NULL COMMENT '支付时间',
  `updatetime` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(4) DEFAULT 1 COMMENT '是否删除(1未删除；0已删除)',
  PRIMARY KEY (`order_no`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '订单信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for order_address
-- ----------------------------
DROP TABLE IF EXISTS `order_address`;
CREATE TABLE `order_address`  (
  `order_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '订单号',
  `contacts` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '联系人',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '联系电话',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '收货地址',
  `createtime` datetime(0) DEFAULT NULL COMMENT '创建日期',
  `updatetime` datetime(0) DEFAULT NULL COMMENT '更新日期',
  PRIMARY KEY (`order_no`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '订单收货地址' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for order_snapshot
-- ----------------------------
DROP TABLE IF EXISTS `order_snapshot`;
CREATE TABLE `order_snapshot`  (
  `order_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '订单号',
  `goods_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '商品ID',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '商品名称',
  `describe` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '商品描述',
  `price` decimal(10, 2) DEFAULT NULL COMMENT '单价',
  `createtime` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `updatetime` datetime(0) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`order_no`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '订单交易快照表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `dept_no` varchar(18) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '部门编号(规则：父级关系编码+自己的编码)',
  `name` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '部门名称',
  `pid` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '父级id',
  `status` tinyint(4) DEFAULT 1 COMMENT '状态(1:正常；0:弃用)',
  `relation_code` varchar(3000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '为了维护更深层级关系',
  `dept_manager_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '部门经理user_id',
  `manager_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '部门经理名称',
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '部门经理联系电话',
  `create_time` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(4) DEFAULT 1 COMMENT '是否删除(1未删除；0已删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES ('0cd7495e-cce4-408a-816b-736386b2cd9c', 'JKD000001', '测试', '0', 1, 'JKD000001', NULL, NULL, NULL, '2022-07-08 00:22:16', NULL, 1);
INSERT INTO `sys_dept` VALUES ('1790f6a5-9b8b-4697-bd0b-6e066e8d2cb8', 'JKD000004', '测试', '72a4f388-50f8-4019-8c67-530cd7c74e7a', 1, 'JKD000000JKD000004', NULL, NULL, NULL, '2022-07-05 17:40:05', '2022-07-05 18:48:41', 0);
INSERT INTO `sys_dept` VALUES ('1f90745a-7c02-41c3-98d9-bae1e4ba86eb', 'JKD000001', '测试1', '0', 1, 'JKD000001', NULL, NULL, NULL, '2022-07-06 16:28:19', '2022-07-06 16:29:39', 0);
INSERT INTO `sys_dept` VALUES ('4f86971a-ded1-40c5-bf87-e6b925db22cc', 'JKD000006', '上海部门', '0', 1, 'JKD000006', NULL, NULL, NULL, '2022-07-05 18:56:47', '2022-07-08 00:20:55', 1);
INSERT INTO `sys_dept` VALUES ('72a4f388-50f8-4019-8c67-530cd7c74e7a', 'JKD000000', '极客总部', '0', 1, 'JKD000000', NULL, '', '', '2019-11-07 22:43:33', '2022-07-06 21:24:56', 1);
INSERT INTO `sys_dept` VALUES ('7f0962f9-4f14-4b87-a752-aa786dabda8e', 'JKD000005', '测试', '72a4f388-50f8-4019-8c67-530cd7c74e7a', 1, 'JKD000000JKD000005', NULL, NULL, NULL, '2022-07-05 18:48:54', '2022-07-06 21:25:02', 1);
INSERT INTO `sys_dept` VALUES ('7f8a36ef-908c-4cfb-9531-93317d717d06', 'JKD000008', '上海分部2', '4f86971a-ded1-40c5-bf87-e6b925db22cc', 1, 'JKD000006JKD000008', NULL, NULL, NULL, '2022-07-05 18:57:15', '2022-07-06 22:07:14', 1);
INSERT INTO `sys_dept` VALUES ('a3bb4d53-8955-4d3d-a202-c508dd4c4163', 'JKD000007', '上海分部1', '4f86971a-ded1-40c5-bf87-e6b925db22cc', 1, 'JKD000006JKD000007', NULL, NULL, NULL, '2022-07-05 18:57:02', '2022-07-06 22:07:14', 1);
INSERT INTO `sys_dept` VALUES ('e53f3ece-3112-4454-9209-864a4da3a294', 'JKD000002', '测试办公室', '0cd7495e-cce4-408a-816b-736386b2cd9c', 1, 'JKD000001JKD000002', NULL, NULL, NULL, '2022-07-08 00:22:29', NULL, 1);
INSERT INTO `sys_dept` VALUES ('f548de25-6af8-48c9-9a7a-5cfb1a3fd357', 'JKD000009', '上海分部1办公室', 'a3bb4d53-8955-4d3d-a202-c508dd4c4163', 1, 'JKD000006JKD000007JKD000009', NULL, NULL, NULL, '2022-07-05 18:57:34', '2022-07-06 22:07:14', 1);

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户id',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户名',
  `operation` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户操作',
  `time` int(11) DEFAULT NULL COMMENT '响应时间',
  `method` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '请求方法',
  `params` varchar(5000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '请求参数',
  `ip` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'IP地址',
  `create_time` datetime(0) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '菜单权限编码',
  `name` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '菜单权限名称',
  `perms` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：sys:user:add,sys:user:edit)',
  `url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '访问地址URL',
  `method` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '资源请求类型',
  `pid` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '父级菜单权限名称',
  `order_num` int(11) DEFAULT 0 COMMENT '排序',
  `type` tinyint(4) DEFAULT NULL COMMENT '菜单权限类型(1:目录;2:菜单;3:按钮)',
  `status` tinyint(4) DEFAULT 1 COMMENT '状态1:正常 0：禁用',
  `create_time` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(4) DEFAULT 1 COMMENT '是否删除(1未删除；0已删除)',
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '链接地址',
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '图标',
  `component` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'vue视图路径',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES ('0905d6a1-247c-434b-915d-0413486a28b0', NULL, '编辑', 'goods:goods:update', 'VUE无需用到，如需使用请自行配置', 'post', '2441650e-3478-49b5-8517-2d4e1709a3d6', 1, 3, 1, '2022-07-13 10:01:25', '2022-07-13 13:42:35', 1, NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES ('0a453450-7a20-457e-9438-282abbaa7145', NULL, '新增', 'goods:goods:add', 'VUE无需用到，如需使用请自行配置', 'post', '2441650e-3478-49b5-8517-2d4e1709a3d6', 1, 3, 1, '2022-07-13 10:01:06', '2022-07-13 13:42:45', 1, NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES ('0f6baf79-845a-4ce2-a903-349734902a0a', NULL, '编辑', 'address:update', 'VUE无需用到，如需使用请自行配置', 'post', '6c3cd83e-c9ee-47a5-94d4-704d6c0ee360', 0, 3, 1, '2022-07-13 14:00:17', NULL, 1, NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES ('1a2ec857-e775-4377-9fb7-e3c77738b3e5', 'btn-role-add', '新增', 'sys:role:add', '/sys/role', 'POST', 'e0b16b95-09de-4d60-a283-1eebd424ed47', 0, 3, 1, '2019-09-22 16:00:59', NULL, 1, NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES ('2073345f-7344-43fe-9084-b7add56da652', 'btn-dept-deleted', '删除', 'sys:dept:deleted', '/sys/dept/*', 'DELETED', 'c038dc93-f30d-4802-a090-be352eab341a', 100, 3, 1, '2019-11-09 20:49:59', NULL, 1, NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES ('217673b8-9ba0-4dbc-9889-cf58996af841', NULL, '删除', 'account:deleted', 'VUE无需用到，如需使用请自行配置', 'post', 'f47f1740-09c8-498b-b91f-4bbd86ebc7b0', 0, 3, 1, '2022-07-26 16:57:15', NULL, 1, NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES ('219fa4e9-f737-4570-a3e9-a9e80da8698e', NULL, '新增', 'address:add', 'VUE无需用到，如需使用请自行配置', 'post', '6c3cd83e-c9ee-47a5-94d4-704d6c0ee360', 0, 3, 1, '2022-07-13 13:59:57', NULL, 1, NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES ('2441650e-3478-49b5-8517-2d4e1709a3d6', NULL, '商品列表', 'goods:goods:list', 'VUE无需用到，如需使用请自行配置', 'post', 'e86c9422-d76c-4279-b626-13b3b03f72e8', 1, 2, 1, '2022-07-13 09:59:39', '2022-07-16 15:50:47', 1, '/product', NULL, 'goods/goods/index');
INSERT INTO `sys_permission` VALUES ('2858c96b-086c-4c0a-9b1a-de35a4fa9762', NULL, '查看', 'order:detail', 'VUE无需用到，如需使用请自行配置', 'post', '9a1d235c-4beb-4326-9a3e-7696db5cd2d8', 0, 3, 1, '2022-07-15 17:18:35', NULL, 1, NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES ('2f1a81ab-90c0-498c-b015-b168f8c97ccd', NULL, '账户明细', NULL, 'VUE无需用到，如需使用请自行配置', 'post', '8842aa72-2e31-44e9-a808-3b7f6112c1f7', 0, 2, 1, '2022-07-30 22:07:21', NULL, 1, '/accountDeatil', NULL, 'account/accountDeatil/index');
INSERT INTO `sys_permission` VALUES ('3924c3a7-9f85-4d5a-b0ab-3bd3a8a672ea', NULL, '列表', 'order:cart:list', 'VUE无需用到，如需使用请自行配置', 'post', 'fbb8e094-0047-4b32-a32b-1a89956dff6a', 0, 3, 1, '2022-07-15 17:16:47', NULL, 1, NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES ('3a93a7e3-956a-408e-b2e4-108e9ece8f04', 'btn-dept-add', '新增', 'sys:dept:add', '/sys/dept', 'POST', 'c038dc93-f30d-4802-a090-be352eab341a', 100, 3, 1, '2019-11-07 22:42:49', '2019-11-09 20:51:08', 1, NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES ('3c390dfd-0d9a-46de-9a5b-1ed884febcb2', 'btn-user-role-update', '赋予角色', 'sys:user:role:update', '/sys/user/roles/*', 'POST', '78f8e29a-cccd-49e5-ada7-5af40dd95312', 100, 3, 1, '2019-11-09 20:39:09', NULL, 1, NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES ('3dac936c-c4e1-4560-ac93-905502f61ae0', NULL, '菜单权限管理', '', '/index/menus', 'GET', 'd6214dcb-8b6d-494b-88fa-f519fc08ff8f', 98, 2, 1, '2019-09-22 15:18:12', '2022-07-08 10:58:06', 1, 'menu', NULL, 'system/menu/index');
INSERT INTO `sys_permission` VALUES ('3ed79f23-90bf-4669-bc02-42ae392e75c1', 'btn-dept-list', '列表', 'sys:dept:list', '/sys/depts', 'POST', 'c038dc93-f30d-4802-a090-be352eab341a', 100, 3, 1, '2019-11-07 22:38:34', '2019-11-09 20:51:18', 1, '', '', NULL);
INSERT INTO `sys_permission` VALUES ('4018e179-e599-41d0-bac5-c5408e1d4bc6', 'btn-role-deleted', '删除', 'sys:role:deleted', '/sys/role/*', 'DELETED', 'e0b16b95-09de-4d60-a283-1eebd424ed47', 100, 3, 1, '2019-11-09 20:54:28', NULL, 1, NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES ('475b4c24-40fa-4823-863a-ba6d793b7610', 'btn-permission-detail', '详情', 'sys:permission:detail', '/sys/permission/*', 'GET', '3dac936c-c4e1-4560-ac93-905502f61ae0', 100, 3, 1, '2019-11-09 20:43:05', NULL, 1, NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES ('47d82131-f72d-4830-90b5-91022f9faf8d', NULL, '列表', 'goods:goods:list', 'VUE无需用到，如需使用请自行配置', 'post', '2441650e-3478-49b5-8517-2d4e1709a3d6', 1, 3, 1, '2022-07-13 10:00:49', '2022-07-13 13:42:56', 1, NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES ('49cad40d-1080-408b-bf14-0e476939bca9', NULL, '列表', 'account:list', 'VUE无需用到，如需使用请自行配置', 'post', '2f1a81ab-90c0-498c-b015-b168f8c97ccd', 0, 3, 1, '2022-07-30 22:07:52', NULL, 1, NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES ('58612968-d93c-4c21-8fdc-a825c0ab0275', 'btn-role-list', '列表', 'sys:role:list', '/sys/roles', 'POST', 'e0b16b95-09de-4d60-a283-1eebd424ed47', 0, 3, 1, '2019-09-22 16:04:33', NULL, 1, '', '', NULL);
INSERT INTO `sys_permission` VALUES ('5f82ee3a-4706-4215-9cc9-ceb3e75a8079', NULL, '编辑', 'order:cart:update', 'VUE无需用到，如需使用请自行配置', 'post', 'fbb8e094-0047-4b32-a32b-1a89956dff6a', 0, 3, 1, '2022-07-15 17:18:04', NULL, 1, NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES ('5fe8d210-1c78-4c70-ae19-d7c3d205b83e', NULL, '列表', 'address:list', 'VUE无需用到，如需使用请自行配置', 'post', '6c3cd83e-c9ee-47a5-94d4-704d6c0ee360', 0, 3, 1, '2022-07-13 14:00:45', NULL, 1, NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES ('60f73506-63f8-4e1a-a970-1570ed9c8fcb', NULL, '订单管理', '', 'VUE无需用到，如需使用请自行配置', 'post', '0', 1, 1, 1, '2022-07-13 14:03:23', '2022-07-13 14:03:35', 1, 'order', NULL, '');
INSERT INTO `sys_permission` VALUES ('63618cfc-330b-4182-9491-29a5f4246cd8', NULL, '新增', 'account:add', 'VUE无需用到，如需使用请自行配置', 'post', 'f47f1740-09c8-498b-b91f-4bbd86ebc7b0', 0, 3, 1, '2022-07-26 16:56:44', NULL, 1, NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES ('6c3cd83e-c9ee-47a5-94d4-704d6c0ee360', NULL, '地址管理', 'address:list', 'VUE无需用到，如需使用请自行配置', 'post', '800dde37-4b65-4603-998d-6bc99afae2dc', 0, 2, 1, '2022-07-13 13:59:31', '2022-07-13 15:50:22', 1, '/address', NULL, 'address/index');
INSERT INTO `sys_permission` VALUES ('761db494-833d-4a6c-94b4-3a7409fd9a78', 'btn-dept-detail', '详情', 'sys:dept:detail', '/sys/dept/*', 'GET', 'c038dc93-f30d-4802-a090-be352eab341a', 100, 3, 1, '2019-11-09 20:50:53', NULL, 1, NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES ('783aedd8-5d93-46b6-8c6d-e4d3f0f3f466', 'btn-user-list', '列表', 'sys:user:list', '/sys/users', 'POST', '78f8e29a-cccd-49e5-ada7-5af40dd95312', 100, 3, 1, '2020-01-01 19:44:37', NULL, 1, '', '', NULL);
INSERT INTO `sys_permission` VALUES ('78f8e29a-cccd-49e5-ada7-5af40dd95312', '', '用户管理', '', '/index/users', 'GET', 'd6214dcb-8b6d-494b-88fa-f519fc08ff8f', 100, 2, 1, '2020-01-01 19:30:30', '2022-07-08 10:56:26', 1, 'user', NULL, 'system/user/index');
INSERT INTO `sys_permission` VALUES ('7e20f49a-5360-4158-af3a-5f0597cbb82e', NULL, '删除', 'address:deleted', 'VUE无需用到，如需使用请自行配置', 'post', '6c3cd83e-c9ee-47a5-94d4-704d6c0ee360', 0, 3, 1, '2022-07-13 14:01:04', NULL, 1, NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES ('800dde37-4b65-4603-998d-6bc99afae2dc', NULL, '地址管理', NULL, 'VUE无需用到，如需使用请自行配置', 'post', '0', 0, 1, 1, '2022-07-13 13:58:01', NULL, 1, 'address', NULL, NULL);
INSERT INTO `sys_permission` VALUES ('817a58d1-ec82-4106-870a-bcc0bfaee0e7', 'btn-user-detail', '详情', 'sys:user:detail', '/sys/user/*', 'GET', '78f8e29a-cccd-49e5-ada7-5af40dd95312', 100, 3, 1, '2019-11-09 20:24:24', '2019-11-09 20:48:05', 1, NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES ('8623c941-5746-4667-9fb8-76f6f5059788', 'btn-permission-deleted', '删除', 'sys:permission:deleted', '/sys/permission/*', 'DELETED', '3dac936c-c4e1-4560-ac93-905502f61ae0', 100, 3, 1, '2019-11-07 22:35:50', '2019-11-09 20:44:44', 1, NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES ('8842aa72-2e31-44e9-a808-3b7f6112c1f7', NULL, '账户管理', NULL, 'VUE无需用到，如需使用请自行配置', 'post', '0', 0, 1, 1, '2022-07-26 16:51:04', '2022-07-26 17:13:14', 1, 'account', NULL, NULL);
INSERT INTO `sys_permission` VALUES ('992d1a8d-b5f8-44fc-9a48-4b3e60a7b15e', 'btn-role-update', '更新', 'sys:role:update', '/sys/role', 'PUT', 'e0b16b95-09de-4d60-a283-1eebd424ed47', 0, 3, 1, '2019-09-22 16:03:46', NULL, 1, NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES ('9a1d235c-4beb-4326-9a3e-7696db5cd2d8', NULL, '我的订单', 'order:list', 'VUE无需用到，如需使用请自行配置', 'post', '60f73506-63f8-4e1a-a970-1570ed9c8fcb', 1, 2, 1, '2022-07-13 14:04:03', '2022-07-13 15:43:30', 1, '/order', NULL, 'order/index');
INSERT INTO `sys_permission` VALUES ('a13cf1df-04d9-4ac7-9c7b-c18c349e738d', NULL, '新增', 'order:cart:add', 'VUE无需用到，如需使用请自行配置', 'post', 'fbb8e094-0047-4b32-a32b-1a89956dff6a', 0, 3, 1, '2022-07-15 17:17:18', NULL, 1, NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES ('a390845b-a53d-4bc9-af5d-331c37f34e6f', 'btn-dept-update', '更新', 'sys:dept:update', '/sys/dept', 'PUT', 'c038dc93-f30d-4802-a090-be352eab341a', 100, 3, 1, '2019-11-09 20:53:16', NULL, 1, NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES ('b01614ab-0538-4cca-bb61-b46f18c60aa4', 'btn-role-detail', '详情', 'sys:role:detail', '/sys/role/*', 'GET', 'e0b16b95-09de-4d60-a283-1eebd424ed47', 100, 3, 1, '2019-09-22 16:06:13', '2019-11-09 20:55:08', 1, NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES ('b180aafa-0d1a-4898-b838-bc20cd44356d', NULL, '编辑', 'sys:permission:update', '/sys/permission', 'PUT', '3dac936c-c4e1-4560-ac93-905502f61ae0', 100, 3, 1, '2019-11-07 22:27:22', '2019-11-09 20:48:44', 1, NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES ('b1a7ea73-8373-48b0-b19e-d8017e01f9c1', NULL, '列表', 'account:list', 'VUE无需用到，如需使用请自行配置', 'post', 'f47f1740-09c8-498b-b91f-4bbd86ebc7b0', 0, 3, 1, '2022-07-26 16:52:32', NULL, 1, NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES ('c038dc93-f30d-4802-a090-be352eab341a', '', '部门管理', '', '/index/depts', 'GET', 'd6214dcb-8b6d-494b-88fa-f519fc08ff8f', 100, 2, 1, '2019-11-07 22:37:20', '2022-07-08 10:57:45', 1, 'dept', NULL, 'system/dept/index');
INSERT INTO `sys_permission` VALUES ('c0a84726-47d8-4d7a-8d53-0736a4586647', 'btn-user-add', '新增', 'sys:user:add', '/sys/user', 'POST', '78f8e29a-cccd-49e5-ada7-5af40dd95312', 100, 3, 1, '2019-11-09 20:25:18', NULL, 1, NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES ('c30389e8-eb3e-4a0d-99c4-639e1893a05f', 'btn-permission-list', '列表', 'sys:permission:list', '/sys/permissions', 'POST', '3dac936c-c4e1-4560-ac93-905502f61ae0', 100, 3, 1, '2019-09-22 15:26:45', '2019-11-09 20:45:19', 1, '', '', NULL);
INSERT INTO `sys_permission` VALUES ('c30389e8-eb3e-4a0d-99c4-639e1893f50a', 'btn-permission-list', '新增', 'sys:permission:add', '/sys/permission', 'POST', '3dac936c-c4e1-4560-ac93-905502f61ae0', 100, 3, 1, '2019-09-22 15:26:45', '2019-11-09 20:45:25', 1, NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES ('d054f68c-8f1c-4d59-95e5-e7fe7d4d9d5a', NULL, '删除', 'goods:goods:deleted', 'VUE无需用到，如需使用请自行配置', 'post', '2441650e-3478-49b5-8517-2d4e1709a3d6', 1, 3, 1, '2022-07-13 10:01:50', '2022-07-13 13:43:05', 1, NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES ('d6214dcb-8b6d-494b-88fa-f519fc08ff8f', NULL, '组织管理', '', '', '', '0', 100, 1, 1, '2019-09-28 15:16:14', NULL, 1, 'system', NULL, NULL);
INSERT INTO `sys_permission` VALUES ('db2d31b7-fdcb-478e-bfde-a55eb8b0aa43', 'btn-user-role-detail', '拥有角色', 'sys:user:role:detail', '/sys/user/roles/*', 'GET', '78f8e29a-cccd-49e5-ada7-5af40dd95312', 100, 3, 1, '2019-11-09 20:29:47', NULL, 1, NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES ('e0b16b95-09de-4d60-a283-1eebd424ed47', '', '角色管理', '', '/index/roles', 'GET', 'd6214dcb-8b6d-494b-88fa-f519fc08ff8f', 99, 2, 1, '2019-09-22 15:45:45', '2022-07-08 10:57:53', 1, 'role', '', 'system/role/index');
INSERT INTO `sys_permission` VALUES ('e75de366-a28e-4ad8-8b0e-8274e905306a', NULL, '变更', 'account:update', 'VUE无需用到，如需使用请自行配置', 'post', 'f47f1740-09c8-498b-b91f-4bbd86ebc7b0', 0, 3, 1, '2022-07-26 16:57:00', NULL, 1, NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES ('e86c9422-d76c-4279-b626-13b3b03f72e8', NULL, '商品管理', NULL, 'VUE无需用到，如需使用请自行配置', 'post', '0', 1, 1, 1, '2022-07-13 09:58:02', '2022-07-13 09:58:21', 1, 'goods', NULL, NULL);
INSERT INTO `sys_permission` VALUES ('f21ed5e8-0756-45dc-91c5-f58a9463caaa', 'btn-user-update', '更新', 'sys:user:update', '/sys/user', 'PUT', '78f8e29a-cccd-49e5-ada7-5af40dd95312', 100, 3, 1, '2019-11-09 20:23:20', NULL, 1, NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES ('f28b9215-3119-482d-bdc1-1f4c3f7c0869', 'btn-user-deleted', '删除', 'sys:user:deleted', '/sys/user', 'DELETED', '78f8e29a-cccd-49e5-ada7-5af40dd95312', 100, 3, 1, '2019-11-09 20:26:45', NULL, 1, NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES ('f3d1e92b-7492-47ba-9d30-441077818980', NULL, '删除', 'order:cart:deleted', 'VUE无需用到，如需使用请自行配置', 'post', 'fbb8e094-0047-4b32-a32b-1a89956dff6a', 0, 3, 1, '2022-07-15 17:17:43', NULL, 1, NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES ('f47f1740-09c8-498b-b91f-4bbd86ebc7b0', NULL, '账户列表', '', 'VUE无需用到，如需使用请自行配置', 'post', '8842aa72-2e31-44e9-a808-3b7f6112c1f7', 0, 2, 1, '2022-07-26 16:51:46', '2022-07-26 17:13:22', 1, '/account', NULL, 'account/index');
INSERT INTO `sys_permission` VALUES ('fbb8e094-0047-4b32-a32b-1a89956dff6a', NULL, '购物列表', 'order:cart:list', 'VUE无需用到，如需使用请自行配置', 'post', '60f73506-63f8-4e1a-a970-1570ed9c8fcb', 0, 2, 1, '2022-07-15 17:16:25', '2022-07-22 19:13:10', 1, '/shoppingCart', NULL, 'order/shoppingCart/index');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '角色名称',
  `description` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `status` tinyint(4) DEFAULT 1 COMMENT '状态(1:正常0:弃用)',
  `create_time` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(4) DEFAULT 1 COMMENT '是否删除(1未删除；0已删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '超级管理员', '拥有所有权限-不能删除', 1, '2019-11-01 19:26:29', '2022-07-08 10:59:18', 1);
INSERT INTO `sys_role` VALUES ('a27900b8-6c70-43df-9bef-aafae89230b4', '测试', NULL, 1, '2022-07-07 14:49:23', '2022-07-08 00:02:11', 0);
INSERT INTO `sys_role` VALUES ('a7e9f299-e245-4566-8b68-b92bc7e2f907', '测试权限', '测试描述', 1, '2022-07-08 00:02:23', '2022-07-08 00:02:31', 1);
INSERT INTO `sys_role` VALUES ('b33aa59b-df2e-4ca5-b9fa-7e238f422bd9', '测试', '123', 1, '2022-07-07 14:40:57', '2022-07-07 14:47:10', 0);

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `role_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '角色id',
  `permission_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '菜单权限id',
  `create_time` datetime(0) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES ('01683d73-19fd-4b9f-b22f-ec794fbe66d3', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'c038dc93-f30d-4802-a090-be352eab341a', '2022-08-01 10:01:07');
INSERT INTO `sys_role_permission` VALUES ('01b896ec-26c3-4e6c-8ada-0e2f98dca20d', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '8842aa72-2e31-44e9-a808-3b7f6112c1f7', '2022-08-01 10:01:07');
INSERT INTO `sys_role_permission` VALUES ('197537c9-c3d1-4239-a875-1905bfa3c28f', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '63618cfc-330b-4182-9491-29a5f4246cd8', '2022-08-01 10:01:07');
INSERT INTO `sys_role_permission` VALUES ('1cf2c814-2187-4e72-b290-4a8aef822ad5', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'e75de366-a28e-4ad8-8b0e-8274e905306a', '2022-08-01 10:01:07');
INSERT INTO `sys_role_permission` VALUES ('1d3dc7d3-25b9-4b21-be94-3d190f3c3057', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '783aedd8-5d93-46b6-8c6d-e4d3f0f3f466', '2022-08-01 10:01:07');
INSERT INTO `sys_role_permission` VALUES ('1da770d8-b517-4b5c-ae0e-a005d032ef21', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '3a93a7e3-956a-408e-b2e4-108e9ece8f04', '2022-08-01 10:01:07');
INSERT INTO `sys_role_permission` VALUES ('28d55d5d-a6df-47c5-9369-3841b3cbebb4', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '3c390dfd-0d9a-46de-9a5b-1ed884febcb2', '2022-08-01 10:01:07');
INSERT INTO `sys_role_permission` VALUES ('2bb69a61-a8b8-4e36-b89e-3fcd830892a6', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'db2d31b7-fdcb-478e-bfde-a55eb8b0aa43', '2022-08-01 10:01:07');
INSERT INTO `sys_role_permission` VALUES ('2e3ce6b2-d88f-4551-9734-5609f4041608', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'e86c9422-d76c-4279-b626-13b3b03f72e8', '2022-08-01 10:01:07');
INSERT INTO `sys_role_permission` VALUES ('2f103d6c-7045-4a6a-8b8b-d72f63710655', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'f3d1e92b-7492-47ba-9d30-441077818980', '2022-08-01 10:01:07');
INSERT INTO `sys_role_permission` VALUES ('3035e4fc-c083-40f9-9af5-01001a162577', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '2f1a81ab-90c0-498c-b015-b168f8c97ccd', '2022-08-01 10:01:07');
INSERT INTO `sys_role_permission` VALUES ('308b0613-4e47-4e7c-af3d-46c7ff0e6845', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '2441650e-3478-49b5-8517-2d4e1709a3d6', '2022-08-01 10:01:07');
INSERT INTO `sys_role_permission` VALUES ('30ff5d93-d083-41c8-8860-fd39a250ef8a', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '992d1a8d-b5f8-44fc-9a48-4b3e60a7b15e', '2022-08-01 10:01:07');
INSERT INTO `sys_role_permission` VALUES ('31eceec1-8d18-4dcc-a8ac-e44505eb5b3c', 'a7e9f299-e245-4566-8b68-b92bc7e2f907', '783aedd8-5d93-46b6-8c6d-e4d3f0f3f466', '2022-07-08 00:08:12');
INSERT INTO `sys_role_permission` VALUES ('40f68f6c-a8c9-441b-9fc6-ab8aa4deef93', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'c0a84726-47d8-4d7a-8d53-0736a4586647', '2022-08-01 10:01:07');
INSERT INTO `sys_role_permission` VALUES ('423c4c37-ce9a-4238-9e6b-f030681f0dbe', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '761db494-833d-4a6c-94b4-3a7409fd9a78', '2022-08-01 10:01:07');
INSERT INTO `sys_role_permission` VALUES ('438ba058-ea6b-4361-bb87-75ba73066082', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'f28b9215-3119-482d-bdc1-1f4c3f7c0869', '2022-08-01 10:01:07');
INSERT INTO `sys_role_permission` VALUES ('4559ba62-00fc-4ae1-ab7a-577af92980bb', 'a7e9f299-e245-4566-8b68-b92bc7e2f907', '78f8e29a-cccd-49e5-ada7-5af40dd95312', '2022-07-08 00:08:12');
INSERT INTO `sys_role_permission` VALUES ('4ad0a3bb-9ad2-4e82-a5e6-4f8fd2679642', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '9a1d235c-4beb-4326-9a3e-7696db5cd2d8', '2022-08-01 10:01:07');
INSERT INTO `sys_role_permission` VALUES ('517f8afc-2c5c-4da9-803f-feef580fc68b', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'f21ed5e8-0756-45dc-91c5-f58a9463caaa', '2022-08-01 10:01:07');
INSERT INTO `sys_role_permission` VALUES ('51f576b5-0566-44c5-a2bb-c87ff1f82b47', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'a13cf1df-04d9-4ac7-9c7b-c18c349e738d', '2022-08-01 10:01:07');
INSERT INTO `sys_role_permission` VALUES ('5684db2f-9cbe-4c90-8d37-fb8f7abdf585', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'd054f68c-8f1c-4d59-95e5-e7fe7d4d9d5a', '2022-08-01 10:01:07');
INSERT INTO `sys_role_permission` VALUES ('5a06f76e-95dc-4c85-b3ea-691c27d29394', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '7e20f49a-5360-4158-af3a-5f0597cbb82e', '2022-08-01 10:01:07');
INSERT INTO `sys_role_permission` VALUES ('5aeec653-f926-408a-b13d-eea90b6feaac', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '8623c941-5746-4667-9fb8-76f6f5059788', '2022-08-01 10:01:07');
INSERT INTO `sys_role_permission` VALUES ('5ecf1e71-b76d-498d-80a8-ca3d3a52ad83', 'a7e9f299-e245-4566-8b68-b92bc7e2f907', '3dac936c-c4e1-4560-ac93-905502f61ae0', '2022-07-08 00:08:12');
INSERT INTO `sys_role_permission` VALUES ('65211b0e-0bc5-4a96-ad0a-dbe24b58cdb2', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'c30389e8-eb3e-4a0d-99c4-639e1893a05f', '2022-08-01 10:01:07');
INSERT INTO `sys_role_permission` VALUES ('68a4e410-289f-474a-8474-cf14a33b1564', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '800dde37-4b65-4603-998d-6bc99afae2dc', '2022-08-01 10:01:07');
INSERT INTO `sys_role_permission` VALUES ('68af55f0-bff0-4a38-a9c4-5a303db367c8', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '2858c96b-086c-4c0a-9b1a-de35a4fa9762', '2022-08-01 10:01:07');
INSERT INTO `sys_role_permission` VALUES ('74770b96-f4b2-4af8-9f61-46ce8b72aabd', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '1a2ec857-e775-4377-9fb7-e3c77738b3e5', '2022-08-01 10:01:07');
INSERT INTO `sys_role_permission` VALUES ('8d58be9a-3f2c-4760-9308-3e27674deff5', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'd6214dcb-8b6d-494b-88fa-f519fc08ff8f', '2022-08-01 10:01:07');
INSERT INTO `sys_role_permission` VALUES ('8fc54dfa-0f5b-4719-8235-3aba634d12f6', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '58612968-d93c-4c21-8fdc-a825c0ab0275', '2022-08-01 10:01:07');
INSERT INTO `sys_role_permission` VALUES ('8ffb9e5f-b851-4451-944c-a3cf056c8887', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '3924c3a7-9f85-4d5a-b0ab-3bd3a8a672ea', '2022-08-01 10:01:07');
INSERT INTO `sys_role_permission` VALUES ('97ec20ec-cb8b-4757-b6e0-8550e388dca7', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '0905d6a1-247c-434b-915d-0413486a28b0', '2022-08-01 10:01:07');
INSERT INTO `sys_role_permission` VALUES ('9a9139c7-72bc-4f2a-b3d2-ce5b6195a3b0', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '78f8e29a-cccd-49e5-ada7-5af40dd95312', '2022-08-01 10:01:07');
INSERT INTO `sys_role_permission` VALUES ('9d146291-5199-4991-af79-2cb557b7a295', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '5f82ee3a-4706-4215-9cc9-ceb3e75a8079', '2022-08-01 10:01:07');
INSERT INTO `sys_role_permission` VALUES ('9e9b4512-0907-454a-8fa9-21245abdcbbd', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'e0b16b95-09de-4d60-a283-1eebd424ed47', '2022-08-01 10:01:07');
INSERT INTO `sys_role_permission` VALUES ('a2034548-bc56-49c8-89c3-91b92d8806f9', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'b01614ab-0538-4cca-bb61-b46f18c60aa4', '2022-08-01 10:01:07');
INSERT INTO `sys_role_permission` VALUES ('aa5e356a-1d72-4c74-9325-53f62517f5c4', 'a7e9f299-e245-4566-8b68-b92bc7e2f907', '3ed79f23-90bf-4669-bc02-42ae392e75c1', '2022-07-08 00:08:12');
INSERT INTO `sys_role_permission` VALUES ('ab90e1a3-7c9e-4263-8c14-d1674863b2af', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'fbb8e094-0047-4b32-a32b-1a89956dff6a', '2022-08-01 10:01:07');
INSERT INTO `sys_role_permission` VALUES ('ad634959-d9f5-430f-af1c-116db0de37d5', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '47d82131-f72d-4830-90b5-91022f9faf8d', '2022-08-01 10:01:07');
INSERT INTO `sys_role_permission` VALUES ('ae111a94-f857-4f8f-8dea-bff60080ed79', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '219fa4e9-f737-4570-a3e9-a9e80da8698e', '2022-08-01 10:01:07');
INSERT INTO `sys_role_permission` VALUES ('afb55a83-a4f6-4b37-9dc0-eea56d47f2b1', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '0a453450-7a20-457e-9438-282abbaa7145', '2022-08-01 10:01:07');
INSERT INTO `sys_role_permission` VALUES ('b3f42290-fe0c-4edb-a3a8-03aa0fec9a81', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '2073345f-7344-43fe-9084-b7add56da652', '2022-08-01 10:01:07');
INSERT INTO `sys_role_permission` VALUES ('ba3c8ba0-8e83-46ba-9445-ebde1e5656ee', 'a7e9f299-e245-4566-8b68-b92bc7e2f907', 'e0b16b95-09de-4d60-a283-1eebd424ed47', '2022-07-08 00:08:12');
INSERT INTO `sys_role_permission` VALUES ('bca0e1e3-bbde-4f59-a625-5b7853f84fd6', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '60f73506-63f8-4e1a-a970-1570ed9c8fcb', '2022-08-01 10:01:07');
INSERT INTO `sys_role_permission` VALUES ('bf29e32b-3281-4e7c-b232-6819bdb0519d', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'a390845b-a53d-4bc9-af5d-331c37f34e6f', '2022-08-01 10:01:07');
INSERT INTO `sys_role_permission` VALUES ('c5d4a8d1-aae0-47ea-900a-1484b7d11a8b', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '3ed79f23-90bf-4669-bc02-42ae392e75c1', '2022-08-01 10:01:07');
INSERT INTO `sys_role_permission` VALUES ('c8b76ed3-0ad3-491e-9c7b-b109b14d472b', 'a7e9f299-e245-4566-8b68-b92bc7e2f907', 'd6214dcb-8b6d-494b-88fa-f519fc08ff8f', '2022-07-08 00:08:12');
INSERT INTO `sys_role_permission` VALUES ('cb6f0af8-23ba-4a60-9f1a-1363290e3da1', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '475b4c24-40fa-4823-863a-ba6d793b7610', '2022-08-01 10:01:07');
INSERT INTO `sys_role_permission` VALUES ('dcc4a8e0-269a-40a8-acb2-b5b141219c0a', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'b180aafa-0d1a-4898-b838-bc20cd44356d', '2022-08-01 10:01:07');
INSERT INTO `sys_role_permission` VALUES ('ddd6e372-005f-4912-9708-31c6a98c40b5', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '5fe8d210-1c78-4c70-ae19-d7c3d205b83e', '2022-08-01 10:01:07');
INSERT INTO `sys_role_permission` VALUES ('df39c048-a233-4dcb-8d34-627f8a8d6c43', 'a7e9f299-e245-4566-8b68-b92bc7e2f907', 'c30389e8-eb3e-4a0d-99c4-639e1893a05f', '2022-07-08 00:08:12');
INSERT INTO `sys_role_permission` VALUES ('dffa3572-ce07-4fbb-aac5-d3ca346eee09', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '4018e179-e599-41d0-bac5-c5408e1d4bc6', '2022-08-01 10:01:07');
INSERT INTO `sys_role_permission` VALUES ('e32ec508-d57a-48c6-ba2c-3bdccd5e4983', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'f47f1740-09c8-498b-b91f-4bbd86ebc7b0', '2022-08-01 10:01:07');
INSERT INTO `sys_role_permission` VALUES ('e673eb01-7698-463a-92df-312da0b48740', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '0f6baf79-845a-4ce2-a903-349734902a0a', '2022-08-01 10:01:07');
INSERT INTO `sys_role_permission` VALUES ('e76334af-458c-45f5-b472-0f5a179ebcf2', 'a7e9f299-e245-4566-8b68-b92bc7e2f907', 'c038dc93-f30d-4802-a090-be352eab341a', '2022-07-08 00:08:12');
INSERT INTO `sys_role_permission` VALUES ('ea205fd9-ddd8-4432-8cce-104d4edbefbf', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'b1a7ea73-8373-48b0-b19e-d8017e01f9c1', '2022-08-01 10:01:07');
INSERT INTO `sys_role_permission` VALUES ('eb2248fa-fe9e-4b01-a6a8-7dcff182d4f2', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '49cad40d-1080-408b-bf14-0e476939bca9', '2022-08-01 10:01:07');
INSERT INTO `sys_role_permission` VALUES ('ef4164d0-617e-449f-83a6-e1dfcdda26a2', 'a7e9f299-e245-4566-8b68-b92bc7e2f907', '58612968-d93c-4c21-8fdc-a825c0ab0275', '2022-07-08 00:08:12');
INSERT INTO `sys_role_permission` VALUES ('f10484bd-1847-4648-be77-e65079775058', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '6c3cd83e-c9ee-47a5-94d4-704d6c0ee360', '2022-08-01 10:01:07');
INSERT INTO `sys_role_permission` VALUES ('f6494993-5eff-47de-a040-d641f36b2d64', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '817a58d1-ec82-4106-870a-bcc0bfaee0e7', '2022-08-01 10:01:07');
INSERT INTO `sys_role_permission` VALUES ('f973e5e3-c0ea-4696-a7b6-ede99fd25c62', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', 'c30389e8-eb3e-4a0d-99c4-639e1893f50a', '2022-08-01 10:01:07');
INSERT INTO `sys_role_permission` VALUES ('fd64baea-5cda-42a0-b4f2-3bf1b2348c6d', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '217673b8-9ba0-4dbc-9889-cf58996af841', '2022-08-01 10:01:07');
INSERT INTO `sys_role_permission` VALUES ('ff3ff2d8-7fac-4199-a443-24e9e859f8f9', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '3dac936c-c4e1-4560-ac93-905502f61ae0', '2022-08-01 10:01:07');

-- ----------------------------
-- Table structure for sys_role_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_resource`;
CREATE TABLE `sys_role_resource`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL COMMENT '角色id',
  `resource_id` int(11) DEFAULT NULL COMMENT '资源id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色-资源表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '账户名称',
  `salt` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '加密盐值',
  `password` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户密码密文',
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '手机号码',
  `dept_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '部门id',
  `real_name` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '真实名称',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '邮箱(唯一)',
  `status` tinyint(4) DEFAULT 1 COMMENT '账户状态(1.正常 2.锁定 )',
  `sex` tinyint(4) DEFAULT 1 COMMENT '性别(1.男 2.女)',
  `deleted` tinyint(4) DEFAULT 1 COMMENT '是否删除(1未删除；0已删除)',
  `create_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人',
  `update_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '更新人',
  `create_where` tinyint(4) DEFAULT 1 COMMENT '创建来源(1.web 2.android 3.ios )',
  `create_time` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) DEFAULT NULL,
  `nick_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `integral` decimal(10, 2) DEFAULT NULL COMMENT '积分',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('220fcdf2-7a23-4522-afa3-e538ba3d377e', 'test', 'e32b61149f244876927d', 'e34b6b8911580d13f64ab8bb0d7f65e0', '15484xxxxx', '72a4f388-50f8-4019-8c67-530cd7c74e7a', NULL, '15485164@qq.com', 1, 1, 0, NULL, 'fcf34b56-a7a2-4719-9236-867495e74c31', 1, '2022-07-07 19:23:22', '2022-07-08 00:08:35', NULL, NULL);
INSERT INTO `sys_user` VALUES ('b8e22e7c-46a9-4404-bd9d-080ca1ba4790', 'ceshi', '324ce32d86224b00a02b', 'ac7e435db19997a46e3b390e69cb148b', '12454', '72a4f388-50f8-4019-8c67-530cd7c74e7a', NULL, '12125@qq.com', 1, 1, 1, NULL, NULL, 1, '2022-07-08 00:18:32', NULL, NULL, NULL);
INSERT INTO `sys_user` VALUES ('fcf34b56-a7a2-4719-9236-867495e74c31', 'admin', '324ce32d86224b00a02b', 'ac7e435db19997a46e3b390e69cb148b', '134xxxxxxxx', '72a4f388-50f8-4019-8c67-530cd7c74e7a', '系统管理员', '15123564@qq.com', 1, 1, 1, NULL, 'fcf34b56-a7a2-4719-9236-867495e74c31', 3, '2022-06-03 19:38:05', '2022-07-22 19:13:39', NULL, 280000.00);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `role_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '角色id',
  `create_time` datetime(0) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('53805d34-fcb6-4cf9-b929-4fe348d5be33', '220fcdf2-7a23-4522-afa3-e538ba3d377e', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '2022-07-07 19:23:30');
INSERT INTO `sys_user_role` VALUES ('80209c2f-4a9f-4872-a8f2-c0bc09c7a5a0', '018568b1-a34a-4a11-87ee-39a9a5c5326f', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '2022-07-07 19:22:15');
INSERT INTO `sys_user_role` VALUES ('863bcf7c-eee7-4afc-9f49-8e968fd8f6ae', 'b8e22e7c-46a9-4404-bd9d-080ca1ba4790', 'a7e9f299-e245-4566-8b68-b92bc7e2f907', '2022-07-08 00:18:32');
INSERT INTO `sys_user_role` VALUES ('a5fa28ed-4a40-437e-a0e9-d06d95add586', 'fcf34b56-a7a2-4719-9236-867495e74c31', '11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9', '2019-11-09 20:40:40');

SET FOREIGN_KEY_CHECKS = 1;
