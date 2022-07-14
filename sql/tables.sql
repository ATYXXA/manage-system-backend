CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(256) DEFAULT NULL COMMENT '用户昵称',
  `userAccount` varchar(256) DEFAULT NULL COMMENT '账号',
  `avatarUrl` varchar(1024) DEFAULT NULL COMMENT '头像url',
  `gender` tinyint DEFAULT NULL COMMENT '性别',
  `userPassword` varchar(512) DEFAULT NULL COMMENT '密码',
  `phone` varchar(128) DEFAULT NULL COMMENT '电话',
  `email` varchar(512) DEFAULT NULL COMMENT '邮箱',
  `userStatus` int DEFAULT '0' COMMENT '用户状态',
  `createTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '数据更新时间',
  `isDelete` tinyint DEFAULT '0' COMMENT '逻辑删除',
  `userRole` int NOT NULL DEFAULT '0' COMMENT '用户角色 0 - 普通用户 1 - 管理员 2 - VIP',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户'

