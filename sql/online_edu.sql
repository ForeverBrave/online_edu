/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.21-log : Database - guli_edu
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*Table structure for table `edu_chapter` */

DROP TABLE IF EXISTS `edu_chapter`;

CREATE TABLE `edu_chapter` (
  `id` char(19) NOT NULL COMMENT '章节ID',
  `course_id` char(19) NOT NULL COMMENT '课程ID',
  `title` varchar(50) NOT NULL COMMENT '章节名称',
  `sort` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '显示排序',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_course_id` (`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='课程';

/*Data for the table `edu_chapter` */

insert  into `edu_chapter`(`id`,`course_id`,`title`,`sort`,`gmt_create`,`gmt_modified`) values ('1','14','第一章：HTML',1,'2019-01-01 12:27:40','2019-01-01 12:55:30'),('15','18','第一章：Java入门',1,'2019-01-01 12:27:40','2019-01-01 12:27:40'),('3','14','第二章：CSS',2,'2019-01-01 12:55:35','2019-01-01 12:27:40'),('32','18','第二章：控制台输入和输出',2,'2019-01-01 12:27:40','2019-01-01 12:27:40'),('44','18','第三章：控制流',3,'2019-01-01 12:27:40','2019-01-01 12:27:40'),('48','18','第四章：类的定义',4,'2019-01-01 12:27:40','2019-01-01 12:27:40'),('63','18','第五章：数组',5,'2019-01-01 12:27:40','2019-01-01 12:27:40'),('64','18','第六章：继承',6,'2019-01-01 12:27:40','2019-01-01 12:27:40'),('65','18','第七章：多态性和抽象类',7,'2019-01-01 12:27:40','2019-01-01 12:27:40');

/*Table structure for table `edu_course` */

DROP TABLE IF EXISTS `edu_course`;

CREATE TABLE `edu_course` (
  `id` char(19) NOT NULL COMMENT '课程ID',
  `teacher_id` char(19) NOT NULL COMMENT '课程讲师ID',
  `subject_id` char(19) NOT NULL COMMENT '课程专业ID',
  `title` varchar(50) NOT NULL COMMENT '课程标题',
  `price` decimal(10,4) unsigned NOT NULL DEFAULT '0.0000' COMMENT '课程销售价格，设置为0则可免费观看',
  `lesson_num` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '总课时',
  `cover` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '课程封面图片路径',
  `buy_count` bigint(10) unsigned NOT NULL DEFAULT '0' COMMENT '销售数量',
  `view_count` bigint(10) unsigned NOT NULL DEFAULT '0' COMMENT '浏览数量',
  `version` bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
  `status` varchar(10) NOT NULL DEFAULT 'Draft' COMMENT '视频状态 Draft未发布  Normal已发布',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_title` (`title`),
  KEY `idx_subject_id` (`subject_id`),
  KEY `idx_teacher_id` (`teacher_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='课程';

/*Data for the table `edu_course` */

insert  into `edu_course`(`id`,`teacher_id`,`subject_id`,`title`,`price`,`lesson_num`,`cover`,`buy_count`,`view_count`,`version`,`status`,`gmt_create`,`gmt_modified`) values ('10','1','221','零基础入门学习Python课程学习','1.0000',10,'',80,165,21,'Draft','2018-03-26 00:00:28','2019-02-21 20:46:36'),('14','1','221','XHTML CSS2 JS整站制作教程课程学习','0.0000',3,'',3,29,15,'Draft','2018-04-02 18:33:34','2019-02-21 20:46:36'),('15','1','251','HTML5入门课程学习','0.0000',23,'',0,39,17,'Draft','2018-04-02 18:34:32','2019-02-21 20:46:37'),('17','2','223','MySql从入门到精通','0.0000',100,'',34,130,4,'Draft','2018-04-02 21:13:58','2019-02-21 20:46:38'),('18','2','202','Java精品课程','0.0000',20,'',150,522,6,'Draft','2018-04-02 21:28:46','2019-02-21 20:46:39'),('21','3','221','搜索引擎优化技术','0.0000',23,'',23,123,1,'Draft','2018-09-15 11:38:57','2019-02-21 20:46:40'),('22','3','223','影响力摄影小课堂','0.0000',0,'',0,2,3,'Draft','2018-11-02 10:49:41','2019-02-21 20:46:40'),('24','3','223','国家教师资格考试','0.0000',12,'',324,25,3,'Draft','2018-11-04 09:48:44','2019-02-21 20:46:41'),('25','3','223','听力口语训练营','0.0000',0,'',0,13,14,'Draft','2018-02-26 19:23:48','2019-02-21 20:46:42'),('26','4','223','CAD4零基础教学','0.0000',0,'',0,34,35,'Draft','2018-02-26 19:24:44','2019-02-21 20:46:43');

/*Table structure for table `edu_course_description` */

DROP TABLE IF EXISTS `edu_course_description`;

CREATE TABLE `edu_course_description` (
  `id` char(19) NOT NULL COMMENT '课程ID',
  `description` text COMMENT '课程简介',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='课程简介';

/*Data for the table `edu_course_description` */

/*Table structure for table `edu_subject` */

DROP TABLE IF EXISTS `edu_subject`;

CREATE TABLE `edu_subject` (
  `id` char(19) NOT NULL COMMENT '课程类别ID',
  `title` varchar(10) NOT NULL COMMENT '类别名称',
  `parent_id` char(19) NOT NULL DEFAULT '0' COMMENT '父ID',
  `sort` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '排序字段',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_parent_id` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='课程科目';

/*Data for the table `edu_subject` */

insert  into `edu_subject`(`id`,`title`,`parent_id`,`sort`,`gmt_create`,`gmt_modified`) values ('1','云计算','0',0,'2018-06-26 09:41:21','2019-02-20 23:25:58'),('10','系统/运维','0',0,'2019-02-20 23:29:59','2019-02-20 23:29:59'),('11','数据库','0',0,'2019-02-20 23:30:13','2019-02-20 23:30:13'),('12','服务器','0',0,'2019-02-20 23:30:19','2019-02-20 23:30:19'),('13','考试认证','0',0,'2019-02-20 23:32:25','2019-02-20 23:32:25'),('14','网络安全','0',0,'2019-02-20 23:32:46','2019-02-20 23:32:46'),('15','物联网','0',0,'2019-02-20 23:32:53','2019-02-20 23:32:53'),('16','区块链','0',0,'2019-02-20 23:33:14','2019-02-20 23:33:14'),('2','人工智能','0',0,'2018-06-26 09:41:32','2019-02-20 23:29:10'),('3','大数据','0',3,'2018-06-26 09:41:41','2019-02-20 23:26:21'),('4','后端开发','0',0,'2018-06-26 09:41:51','2019-02-20 23:26:50'),('401','Python','4',0,'2019-02-20 23:34:19','2019-02-20 23:34:27'),('402','Java','4',0,'2019-02-20 23:35:00','2019-02-20 23:35:00'),('5','前端开发','0',0,'2018-09-10 10:35:07','2019-02-20 23:29:11'),('6','移动开发','0',0,'2018-09-10 10:35:56','2019-02-20 23:29:12'),('7','游戏开发','0',0,'2019-02-20 23:28:45','2019-02-20 23:33:25'),('8','测试','0',0,'2019-02-20 23:28:58','2019-02-20 23:28:58'),('9','研发/架构','0',0,'2019-02-20 23:29:48','2019-02-20 23:29:48');

/*Table structure for table `edu_teacher` */

DROP TABLE IF EXISTS `edu_teacher`;

CREATE TABLE `edu_teacher` (
  `id` char(19) NOT NULL COMMENT '讲师ID',
  `name` varchar(20) NOT NULL COMMENT '讲师姓名',
  `intro` varchar(255) NOT NULL COMMENT '讲师资历,一句话说明讲师',
  `career` text COMMENT '讲师简介',
  `level` int(10) unsigned NOT NULL COMMENT '头衔 1高级讲师 2首席讲师',
  `avatar` varchar(255) DEFAULT NULL COMMENT '讲师头像',
  `sort` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '排序',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='讲师';

/*Data for the table `edu_teacher` */

insert  into `edu_teacher`(`id`,`name`,`intro`,`career`,`level`,`avatar`,`sort`,`is_deleted`,`gmt_create`,`gmt_modified`) values ('1','刘德华9','毕业于师范大学数学系，热爱教育事业，执教数学思维6年有余','具备深厚的数学思维功底、丰富的小学教育经验，授课风格生动活泼，擅长用形象生动的比喻帮助理解、简单易懂的语言讲解难题，深受学生喜欢',2,'',10,0,'2018-03-30 17:15:57','2019-02-23 05:48:45'),('10','唐嫣','北京师范大学法学院副教授','北京师范大学法学院副教授、清华大学法学博士。自2004年至今已有9年的司法考试培训经验。长期从事司法考试辅导，深知命题规律，了解解题技巧。内容把握准确，授课重点明确，层次分明，调理清晰，将法条法理与案例有机融合，强调综合，深入浅出。',1,'',20,0,'2018-04-03 14:32:19','2019-02-22 02:01:26'),('1098969411110432769','string name','string intro','string career',2,'string',10,0,'2019-02-22 23:35:00','2019-02-22 23:35:00'),('1098970098250674178','刘德华1','string intro','string career',2,'string3',10,0,'2019-02-22 23:37:44','2019-02-22 23:38:30'),('11','11','11',NULL,1,NULL,0,0,'2019-02-21 21:09:55','2019-02-22 02:01:25'),('2','周润发','中国人民大学附属中学数学一级教师','中国科学院数学与系统科学研究院应用数学专业博士，研究方向为数字图像处理，中国工业与应用数学学会会员。参与全国教育科学“十五”规划重点课题“信息化进程中的教育技术发展研究”的子课题“基与课程改革的资源开发与应用”，以及全国“十五”科研规划全国重点项目“掌上型信息技术产品在教学中的运用和开发研究”的子课题“用技术学数学”。',2,'',1,0,'2018-03-30 18:28:26','2019-02-22 02:01:26'),('3','钟汉良','毕业于北京大学数学系','中教一级职称。讲课极具亲和力。',1,'',1,0,'2018-03-31 09:20:46','2019-02-22 02:01:27'),('4','周杰伦','长期从事考研政治课讲授和考研命题趋势与应试对策研究。考研辅导新锐派的代表。','政治学博士、管理学博士后，北京师范大学马克思主义学院副教授。多年来总结出了一套行之有效的应试技巧与答题方法，针对性和实用性极强，能帮助考生在轻松中应考，在激励的竞争中取得高分，脱颖而出。',1,'',1,0,'2018-04-03 14:13:51','2019-02-22 02:01:28'),('5','陈伟霆','人大附中2009届毕业生','北京大学数学科学学院2008级本科生，2012年第八届学生五四奖章获得者，在数学领域取得多项国际国内奖项，学术研究成绩突出。曾被两次评为北京大学三好学生、一次评为北京大学三好标兵，获得过北京大学国家奖学金、北京大学廖凯原奖学金、北京大学星光国际一等奖学金、北京大学明德新生奖学金等。',1,'',1,0,'2018-04-03 14:15:36','2019-02-22 02:01:29'),('6','姚晨','华东师范大学数学系硕士生导师，中国数学奥林匹克高级教练','曾参与北京市及全国多项数学活动的命题和组织工作，多次带领北京队参加高中、初中、小学的各项数学竞赛，均取得优异成绩。教学活而新，能够调动学生的学习兴趣并擅长对学生进行思维点拨，对学生学习习惯的养成和非智力因素培养有独到之处，是一位深受学生喜爱的老师。',1,'',1,0,'2018-04-01 14:19:28','2019-02-22 02:01:29'),('7','胡歌','考研政治辅导实战派专家，全国考研政治命题研究组核心成员。','法学博士，北京师范大学马克思主义学院副教授，专攻毛泽东思想概论、邓小平理论，长期从事考研辅导。出版著作两部，发表学术论文30余篇，主持国家社会科学基金项目和教育部重大课题子课题各一项，参与中央实施马克思主义理论研究和建设工程项目。',2,'',8,0,'2018-04-03 14:21:03','2019-02-22 02:01:30'),('8','旦增尼玛','上海师范大学法学院副教授','上海师范大学法学院副教授、清华大学法学博士。自2004年至今已有9年的司法考试培训经验。长期从事司法考试辅导，深知命题规律，了解解题技巧。内容把握准确，授课重点明确，层次分明，调理清晰，将法条法理与案例有机融合，强调综合，深入浅出。',1,'',9,0,'2018-04-03 14:23:06','2019-02-22 02:01:31'),('9','谢娜','资深课程设计专家，专注10年AACTP美国培训协会认证导师','十年课程研发和培训咨询经验，曾任国企人力资源经理、大型外企培训经理，负责企业大学和培训体系搭建；曾任专业培训机构高级顾问、研发部总监，为包括广东移动、东莞移动、深圳移动、南方电网、工商银行、农业银行、民生银行、邮储银行、TCL集团、清华大学继续教育学院、中天路桥、广西扬翔股份等超过200家企业提供过培训与咨询服务，并担任近50个大型项目的总负责人。',1,'',10,0,'2018-04-03 14:23:33','2019-02-22 02:01:32');

/*Table structure for table `edu_video` */

DROP TABLE IF EXISTS `edu_video`;

CREATE TABLE `edu_video` (
  `id` char(19) NOT NULL COMMENT '视频ID',
  `course_id` char(19) NOT NULL COMMENT '课程ID',
  `chapter_id` char(19) NOT NULL COMMENT '章节ID',
  `title` varchar(50) NOT NULL COMMENT '节点名称',
  `sort` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '排序字段',
  `play_count` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '播放次数',
  `is_free` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否可以试听：0免费 1收费',
  `video_source_id` varchar(100) DEFAULT NULL COMMENT '视频资源',
  `duration` float NOT NULL DEFAULT '0' COMMENT '视频时长（秒）',
  `status` varchar(20) NOT NULL DEFAULT '' COMMENT '视频状态:见阿里云文档',
  `size` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '视频源文件大小（字节）',
  `version` bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_course_id` (`course_id`),
  KEY `idx_chapter_id` (`chapter_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='课程视频';

/*Data for the table `edu_video` */

insert  into `edu_video`(`id`,`course_id`,`chapter_id`,`title`,`sort`,`play_count`,`is_free`,`video_source_id`,`duration`,`status`,`size`,`version`,`gmt_create`,`gmt_modified`) values ('17','18','15','第一节：Java简介',1,1000,1,'',100,'Draft',0,1,'2019-01-01 13:08:57','2019-02-21 20:46:08'),('18','18','15','第二节：表达式和赋值语句',2,999,1,'',100,'Draft',0,1,'2019-01-01 13:09:02','2019-02-21 20:46:09'),('19','18','15','第三节：String类',3,888,0,'',100,'Draft',0,1,'2019-01-01 13:09:05','2019-02-21 20:46:10'),('20','18','15','第四节：程序风格',4,666,0,'',100,'Draft',0,1,'2019-01-01 13:09:05','2019-02-21 20:46:10');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
