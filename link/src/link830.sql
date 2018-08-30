/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50639
Source Host           : localhost:3306
Source Database       : link

Target Server Type    : MYSQL
Target Server Version : 50639
File Encoding         : 65001

Date: 2018-08-30 14:14:41
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `base_menu`
-- ----------------------------
DROP TABLE IF EXISTS `base_menu`;
CREATE TABLE `base_menu` (
  `id` varchar(50) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `route` varchar(255) DEFAULT NULL,
  `paths` varchar(255) DEFAULT NULL,
  `parentId` varchar(50) DEFAULT NULL,
  `status` int(10) DEFAULT NULL,
  `orderNum` int(10) DEFAULT NULL COMMENT '权重',
  `isLeaf` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of base_menu
-- ----------------------------
INSERT INTO `base_menu` VALUES ('1ba84441dc9a48db928f0f7eac489db6', '站点管理', '站点管理', '##', '###', '4dfdda8ba54944eb9af9dd7f80595f75', '0', '3', '0');
INSERT INTO `base_menu` VALUES ('38a5d63890ca4cdbabecd4a9b5c24b49', '1', '1', '1', '1', '8103c779e29e437486b5d5258bf8c100', '1', '0', '0');
INSERT INTO `base_menu` VALUES ('3abfbe5a32f54377b009ea7dd6c35dfa', '会员增改', '会员增改', '/consumer/au', 'consumer/consumer_au.html', '4dfdda8ba54944eb9af9dd7f80595f75', '1', '0', '1');
INSERT INTO `base_menu` VALUES ('48017a5f08174ddb9b4c55049418b818', '员工增改', '员工增改', '/user/au', 'user/user_au.html', '4dfdda8ba54944eb9af9dd7f80595f75', '1', '0', '1');
INSERT INTO `base_menu` VALUES ('4dfdda8ba54944eb9af9dd7f80595f75', '系统管理', '系统管理', null, null, null, '1', '9', '0');
INSERT INTO `base_menu` VALUES ('4fa77407b06f4f37b70a33239825b030', '会员管理', '会员管理', '/consumer/list', 'consumer/consumer_list.html', '4dfdda8ba54944eb9af9dd7f80595f75', '1', '0', '0');
INSERT INTO `base_menu` VALUES ('53651604d7d84a8b8f979ddd23f6fd2d', '修改密码', '修改密码', '/user/pwdmodify', 'user/modify_pwd.html', '4dfdda8ba54944eb9af9dd7f80595f75', '1', '0', '1');
INSERT INTO `base_menu` VALUES ('556b5ecf6b05470d9366450a6d77aeba', '测试', '测试', '333', '333', '4dfdda8ba54944eb9af9dd7f80595f75', '0', '0', '0');
INSERT INTO `base_menu` VALUES ('62883dab4903441d88f81bc2aec43fe4', '菜单', '菜单', '/authority/menu', 'authority/menu.html', '4dfdda8ba54944eb9af9dd7f80595f75', '1', '11', '0');
INSERT INTO `base_menu` VALUES ('745a674ba0a24e479d6829e370439dd5', '员工管理', '员工管理', '/user/list', 'user/user_list.html', '4dfdda8ba54944eb9af9dd7f80595f75', '1', '0', '0');
INSERT INTO `base_menu` VALUES ('8103c779e29e437486b5d5258bf8c100', '前台管理', '前台管理', null, null, null, '1', '9', '0');
INSERT INTO `base_menu` VALUES ('862ad6fd62fb408799883808e9bd5f9a', '广告管理', '广告管理', '#####', '####', '8103c779e29e437486b5d5258bf8c100', '1', '0', '0');
INSERT INTO `base_menu` VALUES ('9f092f800b4f4ff7b1c475588c91b462', '商品分类管理', '商品分类管理', '/category/list', 'goods/goods_category.html', '4dfdda8ba54944eb9af9dd7f80595f75', '1', '0', '0');
INSERT INTO `base_menu` VALUES ('a58da72802dc495b8d6fc21c341c0784', '商品增改', '商品增改', '/goods/au', 'goods/goods_au.html', '4dfdda8ba54944eb9af9dd7f80595f75', '1', '0', '1');
INSERT INTO `base_menu` VALUES ('a6ab6accfb3749dfbed920efa7551721', '商品管理', '商品管理', '/goods/list', 'goods/goods_list.html', '4dfdda8ba54944eb9af9dd7f80595f75', '1', '0', '0');
INSERT INTO `base_menu` VALUES ('ae437630a27a42dba87411ddbcb738bf', '菜单增改', '菜单增改', '###', '###', '4dfdda8ba54944eb9af9dd7f80595f75', '0', '1', '0');
INSERT INTO `base_menu` VALUES ('d2e95f23329b4e7ab2908fb87849f6df', '角色管理', '角色管理', '/authority/role', 'authority/role.html', '4dfdda8ba54944eb9af9dd7f80595f75', '1', '0', '0');
INSERT INTO `base_menu` VALUES ('dd9f435c8bf84a8ab29213c287a9a11d', 'test', 'test', null, null, null, '0', '0', '0');

-- ----------------------------
-- Table structure for `base_menu_btn`
-- ----------------------------
DROP TABLE IF EXISTS `base_menu_btn`;
CREATE TABLE `base_menu_btn` (
  `id` varchar(50) NOT NULL,
  `menuId` varchar(50) DEFAULT NULL,
  `btnName` varchar(50) DEFAULT NULL,
  `btnType` varchar(50) DEFAULT NULL,
  `status` int(5) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of base_menu_btn
-- ----------------------------
INSERT INTO `base_menu_btn` VALUES ('04842401accb4c7d8482e9b51b2a7c89', '62883dab4903441d88f81bc2aec43fe4', '上下架', 'onof', '0');
INSERT INTO `base_menu_btn` VALUES ('14fc58c746074fa5b76fa9490d24b24e', 'a6ab6accfb3749dfbed920efa7551721', '修改', 'update', '1');
INSERT INTO `base_menu_btn` VALUES ('2400dff1a560410ebef6a8ce67112bb6', '9f092f800b4f4ff7b1c475588c91b462', '删除', 'delete', '1');
INSERT INTO `base_menu_btn` VALUES ('26a650c77ba84bdd9251c6125642e228', '9f092f800b4f4ff7b1c475588c91b462', '修改', 'update', '1');
INSERT INTO `base_menu_btn` VALUES ('367275cdb6534d0abba3e4670a04fe81', '745a674ba0a24e479d6829e370439dd5', '删除', 'delete', '1');
INSERT INTO `base_menu_btn` VALUES ('3c20e4992e9447b388896d019e30e0e4', '745a674ba0a24e479d6829e370439dd5', '修改', 'update', '1');
INSERT INTO `base_menu_btn` VALUES ('469d5fbe3826450682c1208bb9233314', '1ba84441dc9a48db928f0f7eac489db6', '修改', 'update', '1');
INSERT INTO `base_menu_btn` VALUES ('48d0e31f5626486db99277684766c023', '862ad6fd62fb408799883808e9bd5f9a', '添加', 'add', '1');
INSERT INTO `base_menu_btn` VALUES ('6133c867de7f4659ae70e201db0ba73b', '1ba84441dc9a48db928f0f7eac489db6', '删除', 'delete', '1');
INSERT INTO `base_menu_btn` VALUES ('6c3a1ec565b4492ab136150ae48e685b', '62883dab4903441d88f81bc2aec43fe4', '添加', 'add', '1');
INSERT INTO `base_menu_btn` VALUES ('6e8c0d324bcb4e54a860987d7abcfc10', 'a6ab6accfb3749dfbed920efa7551721', '删除', 'delete', '1');
INSERT INTO `base_menu_btn` VALUES ('7332768ace8f41e4bad21c5349dbb810', 'd2e95f23329b4e7ab2908fb87849f6df', '新增', 'add', '1');
INSERT INTO `base_menu_btn` VALUES ('75c84cd84973427ca525ff03437e7ee0', '62883dab4903441d88f81bc2aec43fe4', '删除', 'delete', '1');
INSERT INTO `base_menu_btn` VALUES ('80dc4b70409d4c6b83fad8691895ebcb', '62883dab4903441d88f81bc2aec43fe4', '修改', 'update', '1');
INSERT INTO `base_menu_btn` VALUES ('989976a579e1422596684bc99cc63a1e', '862ad6fd62fb408799883808e9bd5f9a', '删除', 'delete', '1');
INSERT INTO `base_menu_btn` VALUES ('b1ba40600a5e4f16b6a74d55f5f24f47', '4fa77407b06f4f37b70a33239825b030', '删除', 'delete', '1');
INSERT INTO `base_menu_btn` VALUES ('b2dacc5189aa4b24ad50f446006660de', '9f092f800b4f4ff7b1c475588c91b462', '新增', 'add', '1');
INSERT INTO `base_menu_btn` VALUES ('b6f1ee2669fb4bf5b31cc2540dadac06', '862ad6fd62fb408799883808e9bd5f9a', '修改', 'update', '1');
INSERT INTO `base_menu_btn` VALUES ('c47993549e9c41cda624b09ded76bb88', 'd2e95f23329b4e7ab2908fb87849f6df', '删除', 'delete', '1');
INSERT INTO `base_menu_btn` VALUES ('cb8e0c1d27ef4cfc91d3fbb1a5965bd9', '745a674ba0a24e479d6829e370439dd5', '新增', 'add', '1');
INSERT INTO `base_menu_btn` VALUES ('d5432fd7788945418251677628d76486', 'a6ab6accfb3749dfbed920efa7551721', '新增', 'add', '1');
INSERT INTO `base_menu_btn` VALUES ('e11d8a3e83a640b3a74ea293b8926762', '4fa77407b06f4f37b70a33239825b030', '新增', 'add', '1');
INSERT INTO `base_menu_btn` VALUES ('e290c2dc8373421494bcbd842219ffd3', 'd2e95f23329b4e7ab2908fb87849f6df', '修改', 'update', '1');
INSERT INTO `base_menu_btn` VALUES ('ea22b49c13d540689884e1c44f663a73', '4fa77407b06f4f37b70a33239825b030', '修改', 'update', '1');
INSERT INTO `base_menu_btn` VALUES ('ff61ff0afac64147a153abe1cc5f9779', '1ba84441dc9a48db928f0f7eac489db6', '添加', 'add', '1');

-- ----------------------------
-- Table structure for `base_role`
-- ----------------------------
DROP TABLE IF EXISTS `base_role`;
CREATE TABLE `base_role` (
  `id` varchar(50) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `createTime` bigint(20) DEFAULT NULL,
  `status` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of base_role
-- ----------------------------
INSERT INTO `base_role` VALUES ('20e8f435233f44198cf34ea8d7e339b5', '管理员', '管理员', '1498803797073', '1');

-- ----------------------------
-- Table structure for `base_role_menu`
-- ----------------------------
DROP TABLE IF EXISTS `base_role_menu`;
CREATE TABLE `base_role_menu` (
  `id` varchar(50) NOT NULL,
  `roleId` varchar(50) DEFAULT NULL,
  `menuId` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of base_role_menu
-- ----------------------------
INSERT INTO `base_role_menu` VALUES ('03394905f35349db948cedf64fea6820', '20e8f435233f44198cf34ea8d7e339b5', 'e11d8a3e83a640b3a74ea293b8926762');
INSERT INTO `base_role_menu` VALUES ('13b9c6c3acda4ae68569753e8f79209c', '20e8f435233f44198cf34ea8d7e339b5', '26a650c77ba84bdd9251c6125642e228');
INSERT INTO `base_role_menu` VALUES ('2343ce2227eb46ec9ef95fc93ccfe307', '20e8f435233f44198cf34ea8d7e339b5', 'e290c2dc8373421494bcbd842219ffd3');
INSERT INTO `base_role_menu` VALUES ('2d0cf3e1bb0a4b37b15f0b5ff6e5710c', '20e8f435233f44198cf34ea8d7e339b5', 'cb8e0c1d27ef4cfc91d3fbb1a5965bd9');
INSERT INTO `base_role_menu` VALUES ('3f43d8a9bd42468aa5eebae4e2c96ad3', '20e8f435233f44198cf34ea8d7e339b5', '14fc58c746074fa5b76fa9490d24b24e');
INSERT INTO `base_role_menu` VALUES ('403d0d7227264aaa9380c2e1c7f7f333', '20e8f435233f44198cf34ea8d7e339b5', '75c84cd84973427ca525ff03437e7ee0');
INSERT INTO `base_role_menu` VALUES ('4ef82b5ad0784c9a96d42f8e107a257c', '20e8f435233f44198cf34ea8d7e339b5', 'd5432fd7788945418251677628d76486');
INSERT INTO `base_role_menu` VALUES ('5a5ee711dd7e404e9c853b78cd6f6761', '20e8f435233f44198cf34ea8d7e339b5', '367275cdb6534d0abba3e4670a04fe81');
INSERT INTO `base_role_menu` VALUES ('6df1460804324eaeb86b48ee660a6b96', '20e8f435233f44198cf34ea8d7e339b5', '6c3a1ec565b4492ab136150ae48e685b');
INSERT INTO `base_role_menu` VALUES ('8ec0dc28f5a046848c33962f31636017', '20e8f435233f44198cf34ea8d7e339b5', '2400dff1a560410ebef6a8ce67112bb6');
INSERT INTO `base_role_menu` VALUES ('a1d5767007654b3091ebb68816eafed1', '20e8f435233f44198cf34ea8d7e339b5', 'b1ba40600a5e4f16b6a74d55f5f24f47');
INSERT INTO `base_role_menu` VALUES ('a2c1b680842748a6b8bb6d07f8316ff2', '20e8f435233f44198cf34ea8d7e339b5', 'ea22b49c13d540689884e1c44f663a73');
INSERT INTO `base_role_menu` VALUES ('a531eadb7c944088ac5ffe3d2df01580', '20e8f435233f44198cf34ea8d7e339b5', '80dc4b70409d4c6b83fad8691895ebcb');
INSERT INTO `base_role_menu` VALUES ('aa0a9415d3024925823fa844293b193d', '20e8f435233f44198cf34ea8d7e339b5', 'c47993549e9c41cda624b09ded76bb88');
INSERT INTO `base_role_menu` VALUES ('e1c101e943004bde8cf41839bb936cc3', '20e8f435233f44198cf34ea8d7e339b5', 'b2dacc5189aa4b24ad50f446006660de');
INSERT INTO `base_role_menu` VALUES ('e3cfbe03f8f4426dad62213a62e12157', '20e8f435233f44198cf34ea8d7e339b5', '3c20e4992e9447b388896d019e30e0e4');
INSERT INTO `base_role_menu` VALUES ('e93018b22c1a41ddbae32b96c78330f7', '20e8f435233f44198cf34ea8d7e339b5', '6e8c0d324bcb4e54a860987d7abcfc10');

-- ----------------------------
-- Table structure for `base_setting`
-- ----------------------------
DROP TABLE IF EXISTS `base_setting`;
CREATE TABLE `base_setting` (
  `id` int(50) NOT NULL AUTO_INCREMENT,
  `withdrawalRatio` double(10,2) DEFAULT NULL COMMENT '提现手续费率',
  `managementFeeRatio` double(10,2) DEFAULT NULL COMMENT '管理费率',
  `a_advertisingAward` double DEFAULT NULL COMMENT 'a区广告奖金',
  `b_advertisingAward` double DEFAULT NULL COMMENT 'b区广告奖金',
  `layerAward` double DEFAULT NULL COMMENT '层奖',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of base_setting
-- ----------------------------
INSERT INTO `base_setting` VALUES ('1', '0.05', '0.10', '1000', '500', '0');

-- ----------------------------
-- Table structure for `base_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `base_user_role`;
CREATE TABLE `base_user_role` (
  `id` varchar(50) NOT NULL,
  `userId` varchar(50) DEFAULT NULL,
  `roleId` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of base_user_role
-- ----------------------------
INSERT INTO `base_user_role` VALUES ('958675ec03eb4968b726bda87cd88577', '7f32c9cf1ccf4aaf9d5f40129c37f16c', '20e8f435233f44198cf34ea8d7e339b5');
INSERT INTO `base_user_role` VALUES ('b94ad3edd2f34d33b716532a637ae951', '1389aab5319444b896212967d25101fb', '20e8f435233f44198cf34ea8d7e339b5');

-- ----------------------------
-- Table structure for `bulletin_bulletin`
-- ----------------------------
DROP TABLE IF EXISTS `bulletin_bulletin`;
CREATE TABLE `bulletin_bulletin` (
  `id` varchar(50) NOT NULL DEFAULT '',
  `title` varchar(100) DEFAULT NULL COMMENT '标题',
  `type` int(5) DEFAULT NULL COMMENT '类型',
  `sortNum` int(5) DEFAULT NULL COMMENT '排序',
  `submited` int(5) DEFAULT NULL COMMENT '是否发布，0不发部，1发布',
  `createTime` bigint(15) DEFAULT NULL COMMENT '创建时间',
  `content` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bulletin_bulletin
-- ----------------------------

-- ----------------------------
-- Table structure for `consumer_consumer`
-- ----------------------------
DROP TABLE IF EXISTS `consumer_consumer`;
CREATE TABLE `consumer_consumer` (
  `id` varchar(50) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `contactPerson` varchar(50) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `type` int(5) DEFAULT NULL COMMENT '1为个人2为企业',
  `address` varchar(50) DEFAULT NULL COMMENT '联系地址',
  `description` varchar(500) DEFAULT NULL,
  `createTime` bigint(20) DEFAULT NULL,
  `status` int(5) DEFAULT NULL,
  `origin` int(5) DEFAULT NULL,
  `parentId` varchar(50) DEFAULT NULL,
  `bankAccountName` varchar(50) DEFAULT NULL COMMENT '银行开户名称',
  `bankName` varchar(100) DEFAULT NULL COMMENT '开户银行',
  `bankAddress` varchar(100) DEFAULT NULL COMMENT '开户银行所在地',
  `bankCard` varchar(50) DEFAULT NULL COMMENT '银行卡号',
  `zipCode` varchar(20) DEFAULT NULL COMMENT '邮编',
  `userName` varchar(50) DEFAULT NULL COMMENT '登陆名',
  `userPwd` varchar(50) DEFAULT NULL COMMENT '密码',
  `area` int(5) DEFAULT NULL COMMENT '区域，1代表A区，2代表B区',
  `prizeCoin` double DEFAULT '0' COMMENT '奖金币',
  `referrerId` varchar(50) DEFAULT NULL COMMENT '推荐人',
  `twoPassword` varchar(50) DEFAULT NULL COMMENT '二级密码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of consumer_consumer
-- ----------------------------
INSERT INTO `consumer_consumer` VALUES ('152b03fb957246ce99d273ea1576dad0', '543456745', '76547', '547657', '1', '68', '6574645645', '1488524629771', '1', '1', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `consumer_consumer` VALUES ('24fa50fcbb334fd6b0cc384702d6342e', '654', '634534', '5345', '1', '5345', '34654654', '1488524654108', '1', '1', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `consumer_consumer` VALUES ('314a9c2910394507b58a50b581d40775', '12321', '32', '4353', '1', '45345', '34534534', '1488524623195', '1', '1', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `consumer_consumer` VALUES ('492a39d942bf46628b743715453d5d59', 'jj', null, '110', '1', '76hao', null, '1535443059110', '0', '1', null, 'zhfdfd', 'fffr', 'lx ', '234412', '3279', 'zbb002', 'vv123', '2', null, null, null);
INSERT INTO `consumer_consumer` VALUES ('4c68997a69514359956c1c7bf7053db0', '腊月', null, '113', '1', '亳州', null, '1535439069483', '1', '1', '9be802f26d384bc98224b3f70a4887b3', '更好', '中行', '亳州', '234434363', '2356', '1122', '123456', '1', null, null, null);
INSERT INTO `consumer_consumer` VALUES ('55ae1269edff4ae4a4986880f733cd1e', '一号', '一号', '15551591960', '1', '76号', null, '1535070203226', '1', '1', '9b82792058724dac97359499cfda667e', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `consumer_consumer` VALUES ('58f1df78fa2f48a8a10b7c72a79cb15a', '再', null, '13368954875', '1', '56', null, '1535351952332', '1', '1', 'cc2963d175fa407589cdb2f581232d40', '郑冰冰', '农业银行', '亳州', '1231232423', '236800', 'zbb', '123456', null, null, null, null);
INSERT INTO `consumer_consumer` VALUES ('5e1e585793bc4fafa0acccce9e6797c2', '654645', '6456', '54654', '1', '64564', '56456456', '1488524638203', '1', '1', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `consumer_consumer` VALUES ('73cf1ea6a07e47e18c86a5b122af2e29', '456456', '546', '546456', '2', '4564', '5645654', '1488524662508', '1', '1', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `consumer_consumer` VALUES ('8566ffa07f4c485fbf83748ff5105a35', '56856', '568568', '65856', '1', '86585', '685685', '1488524699171', '1', '1', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `consumer_consumer` VALUES ('98867fb70ce64546b9ec03095bd1a3e1', '123', '1321', '312312', '1', '31231', '23123123', '1488524612531', '1', '1', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `consumer_consumer` VALUES ('98e7c4b7e08449259c6fcf05aeb87faa', '123dfg', '1321', '312312', '1', '31231', '23123123', '1488524732923', '1', '1', '55ae1269edff4ae4a4986880f733cd1e', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `consumer_consumer` VALUES ('9b82792058724dac97359499cfda667e', '2222222222', '222222222', '222222222222', '1', '2222222222222', '222222222222222222', '1497940089566', '1', '1', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `consumer_consumer` VALUES ('9be802f26d384bc98224b3f70a4887b3', '456546', '54645', '6456', '1', '45645', '645645656', '1488524647379', '1', '1', null, '435435', '43543', '5345', '345', '435345', '888888', '123456', null, '1275', null, null);
INSERT INTO `consumer_consumer` VALUES ('abe3da32b5044b84aa0926becb3afe39', '5686', '5856', '8568', '1', '568', '56856856', '1488524708898', '1', '1', '8566ffa07f4c485fbf83748ff5105a35', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `consumer_consumer` VALUES ('b5939422ef1144f9aedafd2f2f680c98', '郑冰冰', null, '1156', '1', '34号', null, '1535354164582', '1', '1', '24fa50fcbb334fd6b0cc384702d6342e', '郑冰冰', '农行', '亳州', '132546565465', '236800', 'zbb', '123456', null, null, null, null);
INSERT INTO `consumer_consumer` VALUES ('cb67fa24992e4cdaa1ca0463b8a09082', '6756', null, '567', '1', '657567', null, '1535445917371', '1', '1', '9be802f26d384bc98224b3f70a4887b3', '56765', '中国人民银行1', '65765', '7567', '567567', '1133', '123456', '2', null, null, null);
INSERT INTO `consumer_consumer` VALUES ('cc004658da964e6aa98c5545be5b5285', '1238888888', '1321', '312312', '1', '31231', '23123123122', '1488524828244', '0', '1', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `consumer_consumer` VALUES ('cc2963d175fa407589cdb2f581232d40', '345345', '34534', '6346', '1', '34634', '63463463', '1488524689668', '1', '1', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `consumer_consumer` VALUES ('d07c2ac0fef5422fa06f7d899e8fbaab', '65645', '6456', '7658', '1', '56856', '856857856', '1488524716915', '1', '1', '9b82792058724dac97359499cfda667e', '66', '建行', '利辛', '32456456', '3455524', null, null, '1', null, null, null);
INSERT INTO `consumer_consumer` VALUES ('d800bea05ee445da841e3aeec5ca8eec', '1239999999999999999', '1321', '312312', '1', '31231', '23123123', '1488524757123', '1', '1', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `consumer_consumer` VALUES ('e79c84f58f6a48649ea4b1937b0361ca', '行程表', null, '117', '1', '亳州', null, '1535534105732', '1', '1', '4c68997a69514359956c1c7bf7053db0', '表达', '农行', '亳州', '2342343124', '236789', '999999', '123456', '1', '0', '9be802f26d384bc98224b3f70a4887b3', null);

-- ----------------------------
-- Table structure for `consumer_salary`
-- ----------------------------
DROP TABLE IF EXISTS `consumer_salary`;
CREATE TABLE `consumer_salary` (
  `id` varchar(50) NOT NULL DEFAULT '',
  `layerAward` double DEFAULT '0' COMMENT '层奖',
  `a_advertisingAward` double DEFAULT '0' COMMENT 'a区广告奖',
  `b_advertisingAward` double DEFAULT '0' COMMENT 'b区广告奖',
  `managementFee` double DEFAULT '0' COMMENT '管理费',
  `withdrawalFee` double DEFAULT '0' COMMENT '提现手续费',
  `realWage` double DEFAULT '0' COMMENT '实发工资',
  `createDate` date DEFAULT NULL COMMENT '日期',
  `consumerId` varchar(50) DEFAULT NULL COMMENT '客户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of consumer_salary
-- ----------------------------
INSERT INTO `consumer_salary` VALUES ('a129679b368c4d7b8f3a83a25f441531', '0', '1000', '500', '150', '75', '1275', '2018-08-28', '9be802f26d384bc98224b3f70a4887b3');
INSERT INTO `consumer_salary` VALUES ('f5e1012efdd94bdfacdf707800170f6d', '0', '2000', '0', '200', '100', '1700', '2018-08-29', '4c68997a69514359956c1c7bf7053db0');

-- ----------------------------
-- Table structure for `consumer_withdraw`
-- ----------------------------
DROP TABLE IF EXISTS `consumer_withdraw`;
CREATE TABLE `consumer_withdraw` (
  `id` varchar(50) NOT NULL,
  `userName` varchar(50) DEFAULT NULL COMMENT '登录名（会员编号）',
  `consumerName` varchar(50) DEFAULT NULL COMMENT '会员名称',
  `withdrawCount` double DEFAULT '0' COMMENT '提现数量',
  `bankAccountName` varchar(50) DEFAULT NULL COMMENT '银行卡姓名',
  `bankCard` varchar(50) DEFAULT NULL COMMENT '银行卡号',
  `bankName` varchar(50) DEFAULT NULL COMMENT '开户银行',
  `bankAddress` varchar(50) DEFAULT NULL COMMENT '银行所在地',
  `prizeCoin` double DEFAULT NULL COMMENT '奖金币',
  `consumerId` varchar(50) DEFAULT NULL,
  `withdrawDate` datetime DEFAULT NULL COMMENT '提先日期',
  `deduction` double DEFAULT '0' COMMENT '扣车库',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of consumer_withdraw
-- ----------------------------
INSERT INTO `consumer_withdraw` VALUES ('db145d14ec6748c1907c890c6478d4c0', '888888', '456546', '100', '435435', '345', '43543', '5345', '1275', '9be802f26d384bc98224b3f70a4887b3', '2018-08-29 00:00:00', '0', null);

-- ----------------------------
-- Table structure for `goods_category`
-- ----------------------------
DROP TABLE IF EXISTS `goods_category`;
CREATE TABLE `goods_category` (
  `id` bigint(50) NOT NULL COMMENT '主键',
  `name` varchar(50) NOT NULL COMMENT '分类名称',
  `parentId` bigint(50) DEFAULT NULL COMMENT '父类id',
  `status` int(10) NOT NULL COMMENT '状态，1正常，0删除',
  `createTime` bigint(20) NOT NULL COMMENT '创建时间',
  `shopId` varchar(50) DEFAULT NULL COMMENT '商家id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of goods_category
-- ----------------------------
INSERT INTO `goods_category` VALUES ('1000000000000000', '1', null, '1', '1498178992472', null);
INSERT INTO `goods_category` VALUES ('1001000000000000', '10', '1000000000000000', '1', '1498179114487', null);

-- ----------------------------
-- Table structure for `goods_goods`
-- ----------------------------
DROP TABLE IF EXISTS `goods_goods`;
CREATE TABLE `goods_goods` (
  `id` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL COMMENT '商品名称',
  `shopId` varchar(50) DEFAULT NULL COMMENT '商品所属商家',
  `categoryId` varchar(50) DEFAULT NULL COMMENT '所属分类',
  `createUser` varchar(50) DEFAULT NULL COMMENT '创建人',
  `sellStatus` int(5) NOT NULL COMMENT '销售状态',
  `shopPrice` double(10,0) NOT NULL COMMENT '价格',
  `tag` varchar(100) DEFAULT NULL COMMENT '标签，让人第一眼对商品有个大概认识。',
  `createTime` bigint(15) NOT NULL COMMENT '创建时间',
  `description` varchar(1000) DEFAULT NULL COMMENT '描述',
  `unit` varchar(20) NOT NULL COMMENT '单位',
  `imageUrl` varchar(500) NOT NULL COMMENT '图片链接',
  `quantity` int(10) DEFAULT NULL COMMENT '数量',
  `status` int(5) NOT NULL COMMENT '状态，是否删除',
  `deleteTime` bigint(20) DEFAULT NULL COMMENT '删除时间',
  `updateTime` bigint(20) DEFAULT NULL COMMENT '更新时间',
  `recordId` varchar(20) DEFAULT NULL,
  `discountedPrice` double(15,0) DEFAULT NULL COMMENT '折扣价',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of goods_goods
-- ----------------------------
INSERT INTO `goods_goods` VALUES ('4f386298f8f84f9c8751b72bfb4ae02d', '测试', null, null, null, '0', '123', '正货', '1498181539958', '00000000000000000<img src=\"/link/AdminLTE/common/plugins/kindeditor/plugins/emoticons/images/10.gif\" border=\"0\" alt=\"\" />', '袋', 'product/original/a2135d599f2d4a9bb910dfab66b09dd9.jpg,product/original/dc0f8893a908471d8eec010f540d5e52.jpg', null, '1', null, '1498181539958', null, '120');

-- ----------------------------
-- Table structure for `goods_goods_category_relation`
-- ----------------------------
DROP TABLE IF EXISTS `goods_goods_category_relation`;
CREATE TABLE `goods_goods_category_relation` (
  `id` varchar(50) NOT NULL,
  `categoryId` varchar(50) NOT NULL,
  `goodsId` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of goods_goods_category_relation
-- ----------------------------
INSERT INTO `goods_goods_category_relation` VALUES ('d77d2537255a4aa1985cb8e22522b398', '1001000000000000', '4f386298f8f84f9c8751b72bfb4ae02d');

-- ----------------------------
-- Table structure for `user_user`
-- ----------------------------
DROP TABLE IF EXISTS `user_user`;
CREATE TABLE `user_user` (
  `id` varchar(35) NOT NULL,
  `name` varchar(50) NOT NULL COMMENT '用户名',
  `phone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `password` varchar(50) NOT NULL COMMENT '密码',
  `department` varchar(50) DEFAULT NULL COMMENT '部门',
  `sex` int(5) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `address` varchar(80) DEFAULT NULL,
  `createTime` bigint(15) DEFAULT NULL,
  `status` int(2) DEFAULT NULL,
  `salary` int(10) DEFAULT NULL COMMENT '工资',
  `post` varchar(50) DEFAULT NULL COMMENT '岗位',
  `type` int(5) DEFAULT NULL COMMENT '1代表管理员，2代表会员',
  `consumerId` varchar(50) DEFAULT NULL COMMENT '会员id',
  `twoPassword` varchar(50) DEFAULT NULL COMMENT '二级密码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_user
-- ----------------------------
INSERT INTO `user_user` VALUES ('00b400149b9649b0835ab2a3f2df8d72', 'zbb', '1156', 'E10ADC3949BA59ABBE56E057F20F883E', null, null, null, null, '1535354169217', null, null, null, '2', 'b5939422ef1144f9aedafd2f2f680c98', null);
INSERT INTO `user_user` VALUES ('1389aab5319444b896212967d25101fb', 'admin', '110', 'E10ADC3949BA59ABBE56E057F20F883E', null, '1', '', '', '1535075811644', '1', '10000', '老总', '1', null, null);
INSERT INTO `user_user` VALUES ('234354365467457456456', '888888', '110', 'E10ADC3949BA59ABBE56E057F20F883E', null, null, null, null, null, null, null, null, '2', '9be802f26d384bc98224b3f70a4887b3', 'E10ADC3949BA59ABBE56E057F20F883E');
INSERT INTO `user_user` VALUES ('315d3e48dc694c1faef8b0dccdce4a24', 'zbb002', '110', '4EFC8C88B17BD6760903E4EBC4F06043', null, null, null, null, '1535443059152', null, null, null, '2', '492a39d942bf46628b743715453d5d59', null);
INSERT INTO `user_user` VALUES ('7f32c9cf1ccf4aaf9d5f40129c37f16c', '0001124', '13345265263', 'E10ADC3949BA59ABBE56E057F20F883E', null, '1', '23@12.com', '亳州', '1499048728040', '1', null, null, '1', null, null);
INSERT INTO `user_user` VALUES ('86246d7560914ceaa311c30b43c879d1', '999999', '117', 'E10ADC3949BA59ABBE56E057F20F883E', null, null, null, null, '1535534105743', null, null, null, '2', 'e79c84f58f6a48649ea4b1937b0361ca', null);
INSERT INTO `user_user` VALUES ('963f519fe47b48a08b8dff4ee1a639b1', '1133', '567', 'E10ADC3949BA59ABBE56E057F20F883E', null, null, null, null, '1535445917414', null, null, null, '2', 'cb67fa24992e4cdaa1ca0463b8a09082', null);
