DROP TABLE
IF EXISTS pd_menu;

CREATE TABLE
IF NOT EXISTS pd_menu (
	id INT (64) NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
	parent_id INT (11) DEFAULT NULL COMMENT '父级ID',
	parent_ids VARCHAR (2000) DEFAULT NULL COMMENT '父级IDS',
	`name` VARCHAR (255) DEFAULT NULL COMMENT '名称',
	href VARCHAR (2000) DEFAULT NULL COMMENT '链接',
	permission VARCHAR (2000) DEFAULT NULL COMMENT '权限标识',
	del TINYINT (2) DEFAULT '0' COMMENT '是否删除',
	create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	update_time datetime DEFAULT NULL COMMENT '更新时间',
	remarks VARCHAR (255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注信息'
) ENGINE = INNODB DEFAULT CHARSET = utf8 COLLATE = utf8_bin COMMENT = '菜单表';

DROP TABLE
IF EXISTS pd_role;

CREATE TABLE
IF NOT EXISTS pd_role (
	id INT (64) NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
	`name` VARCHAR (100) NOT NULL COMMENT '角色名称',
	role_type TINYINT (3) DEFAULT NULL COMMENT '角色类型',
	disable TINYINT (2) NOT NULL DEFAULT '0' COMMENT '是否可用',
	del TINYINT (2) NOT NULL DEFAULT '0' COMMENT '删除标记',
	create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	update_time datetime DEFAULT NULL COMMENT '更新时间',
	remarks VARCHAR (255) DEFAULT NULL COMMENT '备注信息'
) ENGINE = INNODB DEFAULT CHARSET = utf8 COLLATE = utf8_bin COMMENT = '角色表';

DROP TABLE
IF EXISTS pd_user;

CREATE TABLE
IF NOT EXISTS pd_user (
	id INT (64) NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
	login_name VARCHAR (100) COLLATE utf8_bin NOT NULL COMMENT '登录名',
	`password` VARCHAR (100) COLLATE utf8_bin NOT NULL COMMENT '密码',
	`name` VARCHAR (100) COLLATE utf8_bin NOT NULL COMMENT '姓名',
	email VARCHAR (200) COLLATE utf8_bin DEFAULT NULL COMMENT '邮箱',
	phone VARCHAR (200) COLLATE utf8_bin DEFAULT NULL COMMENT '电话',
	login_ip VARCHAR (100) COLLATE utf8_bin DEFAULT NULL COMMENT '最后登陆IP',
	login_date datetime DEFAULT NULL COMMENT '最后登陆时间',
	expired TINYINT (3) DEFAULT '0' COMMENT '是否过期',
	disabled TINYINT (3) DEFAULT '0' COMMENT '是否可用',
	del TINYINT (2) NOT NULL DEFAULT '0' COMMENT '删除标记',
	create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	update_time datetime DEFAULT NULL COMMENT '更新时间',
	remarks VARCHAR (255) DEFAULT NULL COMMENT '备注信息'
) ENGINE = INNODB DEFAULT CHARSET = utf8 COLLATE = utf8_bin COMMENT = '用户表';

DROP TABLE
IF EXISTS pd_role_menu;

CREATE TABLE
IF NOT EXISTS pd_role_menu (
	id INT (64) NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
	role_id VARCHAR (64) DEFAULT NULL COMMENT '角色id',
	menu_id VARCHAR (64) DEFAULT NULL COMMENT '菜单id',
	del TINYINT (2) NOT NULL DEFAULT '0' COMMENT '删除标记',
	create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) ENGINE = INNODB DEFAULT CHARSET = utf8 COLLATE = utf8_bin COMMENT = '角色-菜单';

DROP TABLE
IF EXISTS pd_user_role;

CREATE TABLE
IF NOT EXISTS pd_user_role (
	id INT (64) NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
	user_id VARCHAR (64) DEFAULT NULL COMMENT '用户id',
	role_id VARCHAR (64) DEFAULT NULL COMMENT '角色id',
	del TINYINT (2) NOT NULL DEFAULT '0' COMMENT '删除标记',
	create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) ENGINE = INNODB DEFAULT CHARSET = utf8 COLLATE = utf8_bin COMMENT = '用户-角色';