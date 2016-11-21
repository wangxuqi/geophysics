USE geo;

DROP TABLE IF EXISTS `geophysics_survey_lines`;

CREATE TABLE `geophysics_survey_lines`(
	`survey_line_id` bigint(20) unsigned NOT NULL DEFAULT'0',
	`start_point_lon` double NOT NULL DEFAULT '0' COMMENT '起点经度',
	`start_point_lat` double NOT NULL DEFAULT '0' COMMENT '起点纬度',
	`end_point_lon` double NOT NULL DEFAULT '0' COMMENT '终点经度',
	`end_point_lat` double NOT NULL DEFAULT '0' COMMENT '终点纬度',
	`distance` double unsigned NOT NULL DEFAULT '0' COMMENT '测线距离',
	`number` smallint(4) unsigned NOT NULL DEFAULT '0' COMMENT '包含测点数量',
	`type_id` smallint(4) unsigned NOT NULL DEFAULT '0' COMMENT '数据类型ID',
	`method_id` smallint(4) unsigned NOT NULL DEFAULT '0' COMMENT '测量方法ID',
	`collect_time` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '采集时间',
	`processed_time` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '处理时间',
	`collector` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '采集人',
	`processor`	varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '处理人',
	`survey_area_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '测区ID',
	`project_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '工程ID',
	`origin_data_path` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '原始数据存放路径',
	`processed_data_path` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '处理后数据存放路径',
	`processed_image_path` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '处理后图像存放路径',
	`anomaly_3Dmodel_path` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '3D异常模型存放路径',
	PRIMARY KEY (`survey_line_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


DROP TABLE IF EXISTS `geophysics_survey_areas`;

CREATE TABLE `geophysics_survey_areas`(
	`survey_area_id` bigint(20) unsigned NOT NULL DEFAULT'0',
	`name` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '测区名',
	`coords` MEDIUMTEXT NOT NULL COMMENT '坐标点json',
	`method_id` smallint(4) unsigned NOT NULL DEFAULT '0' COMMENT '测量方法ID',
	`project_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '工程ID',
	`start_time` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '开始时间',
	`finish_time` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '结束时间',
	PRIMARY KEY (`survey_area_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


DROP TABLE IF EXISTS `geophysics_projects`;

CREATE TABLE `geophysics_projects`(
	`project_id` int(10) unsigned NOT NULL DEFAULT '0',
	`name` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '测区名',
	`coords` MEDIUMTEXT NOT NULL COMMENT '坐标点json',
	`start_time` varchar(255) COLLATE utf8_bin  DEFAULT '' COMMENT '开始时间',
	`finish_time` varchar(255) COLLATE utf8_bin  DEFAULT '' COMMENT '结束时间',
	`principal`	varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '负责人',
	PRIMARY KEY (`project_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `geophysics_method_types`;

CREATE TABLE `geophysics_method_types`(
	`method_id` smallint(4) unsigned NOT NULL DEFAULT '0',
	`name` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '方法名',
	PRIMARY KEY (`method_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


DROP TABLE IF EXISTS `geophysics_data_types`;

CREATE TABLE `geophysics_data_types`(
	`type_id` smallint(4) unsigned NOT NULL DEFAULT '0',
	`name` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '方法名',
	PRIMARY KEY (`type_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


DROP TABLE IF EXISTS `geophysics_anomalies`;

CREATE TABLE `geophysics_anomalies`(
	`anomay_id` int(10) unsigned NOT NULL DEFAULT '0',
	`coords` MEDIUMTEXT NOT NULL COMMENT '坐标点json',
	`create_time` varchar(255) COLLATE utf8_bin  DEFAULT '' COMMENT '圈定时间',
	`approver` varchar(255) COLLATE utf8_bin  DEFAULT '' COMMENT '圈定人',
	`suspected_thing` varchar(255) COLLATE utf8_bin  DEFAULT '' COMMENT '疑似物体',
	`anomaly_3Dmodel_path` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '3D异常模型存放路径',
	PRIMARY KEY (`anomay_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


DROP TABLE IF EXISTS `geophysics_anomaly_points`;

CREATE TABLE `geophysics_anomaly_points`(
	`point_id` bigint(20) unsigned NOT NULL DEFAULT'0',
	`lon` double NOT NULL DEFAULT '0' COMMENT '经度',
	`lat` double NOT NULL DEFAULT '0' COMMENT '纬度',
	`depth` double NOT NULL DEFAULT '0' COMMENT '深度',
	`value` double NOT NULL DEFAULT '0' COMMENT '物探属性值',
	`survey_line_id` bigint(20) unsigned NOT NULL DEFAULT'0' COMMENT '所属测线ID',
	`anomaly_id`int(10) unsigned NOT NULL DEFAULT '0' COMMENT '所属异常ID',
	PRIMARY KEY (`point_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;



CREATE TABLE `test_polygon`(
	`id` int PRIMARY KEY AUTO_INCREMENT,
	`name` varchar(128) NOT NULL DEFAULT '',
	`create_time` varchar(255) COLLATE utf8_bin  DEFAULT '' COMMENT '圈定时间',
	`anomaly_3Dmodel_path` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '3D异常模型存放路径',
	`pgn` POLYGON
)ENGINE=MyISAM CHARSET=utf8 COLLATE=utf8_bin;

INSERT INTO `test_polygon` VALUES(
	null,
	'1号异常',
	'2016-3-18',
	'/home/test/111.xxx',
	POLYGONFROMTEXT('POLYGON((0 0,10 0,10 10,0 10,0 0),(5 5,7 5,7 7,5 7, 5 5))')
);

