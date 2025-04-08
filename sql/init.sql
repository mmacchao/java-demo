-- 创建订单库
CREATE DATABASE IF NOT EXISTS seata_order;
USE seata_order;

CREATE TABLE IF NOT EXISTS `t_order` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(255) DEFAULT NULL,
  `product_id` bigint(11) DEFAULT NULL,
  `count` int(11) DEFAULT NULL,
  `money` decimal(11,0) DEFAULT NULL,
  `status` int(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- 创建库存库
CREATE DATABASE IF NOT EXISTS seata_storage;
USE seata_storage;

CREATE TABLE IF NOT EXISTS `t_storage` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `product_id` bigint(11) DEFAULT NULL,
  `total` int(11) DEFAULT NULL,
  `used` int(11) DEFAULT NULL,
  `residue` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- 初始化库存数据
INSERT INTO t_storage VALUES (1, 1, 100, 0, 100);

-- 创建Seata所需的事务日志表
CREATE TABLE IF NOT EXISTS `undo_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `branch_id` bigint(20) NOT NULL,
  `xid` varchar(100) NOT NULL,
  `context` varchar(128) NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int(11) NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- 在两个库中都需要创建undo_log表
USE seata_order;
CREATE TABLE IF NOT EXISTS `undo_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `branch_id` bigint(20) NOT NULL,
  `xid` varchar(100) NOT NULL,
  `context` varchar(128) NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int(11) NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;