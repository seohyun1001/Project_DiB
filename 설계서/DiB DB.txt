CREATE TABLE `accommodation` (
  `ano` int NOT NULL AUTO_INCREMENT,
  `acc_name` varchar(50) NOT NULL,
  `acc_exp` varchar(255) NOT NULL,
  `acc_loc` varchar(255) NOT NULL,
  `acc_phone` varchar(20) DEFAULT NULL,
  `acc_image` varchar(100) DEFAULT NULL,
  `acc_like` tinyint DEFAULT '0',
  `acc_total_like` int DEFAULT '0',
  `acc_tag` varchar(20) DEFAULT NULL,
  `moddate` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `regdate` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  PRIMARY KEY (`ano`)
);



CREATE TABLE `accommodation_img` (
  `uuid` varchar(200) NOT NULL,
  `file_name` varchar(200) DEFAULT NULL,
  `ano` int DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  KEY `ACCOMMODATION_ANO_idx` (`ano`),
  CONSTRAINT `acc_ano_fk` FOREIGN KEY (`ano`) REFERENCES `accommodation` (`ano`)
);



CREATE TABLE `faq` (
  `fno` int NOT NULL AUTO_INCREMENT,
  `faq_title` varchar(200) NOT NULL,
  `faq_content` varchar(500) NOT NULL,
  `faq_image` varchar(100) DEFAULT NULL,
  `regdate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `moddate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`fno`)
);



CREATE TABLE `faq_img` (
  `uuid` varchar(200) NOT NULL,
  `file_name` varchar(200) DEFAULT NULL,
  `fno` int DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  KEY `faq_no_idx` (`fno`),
  CONSTRAINT `fno_fk` FOREIGN KEY (`fno`) REFERENCES `faq` (`fno`)
);



CREATE TABLE `member` (
  `mid` varchar(50) NOT NULL,
  `mpw` varchar(50) NOT NULL,
  `name` varchar(30) NOT NULL,
  `email` varchar(100) NOT NULL,
  `address` varchar(200) DEFAULT NULL,
  `birthdate` date DEFAULT NULL,
  `phone` varchar(11) NOT NULL,
  `regdate` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `moddate` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  PRIMARY KEY (`mid`)
);



CREATE TABLE `notice` (
  `nno` int NOT NULL AUTO_INCREMENT,
  `notice_title` varchar(100) NOT NULL,
  `notice_content` varchar(500) NOT NULL,
  `notice_image` varchar(100) DEFAULT NULL,
  `regdate` date NOT NULL,
  `moddate` date NOT NULL,
  PRIMARY KEY (`nno`)
);



CREATE TABLE `notice_img` (
  `uuid` varchar(200) NOT NULL,
  `file_name` varchar(200) DEFAULT NULL,
  `nno` int DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  KEY `notice_no_idx` (`nno`),
  CONSTRAINT `nno_fk` FOREIGN KEY (`nno`) REFERENCES `notice` (`nno`)
);



CREATE TABLE `restaurant` (
  `rno` bigint NOT NULL AUTO_INCREMENT,
  `rest_name` varchar(50) NOT NULL,
  `rest_exp` varchar(255) NOT NULL,
  `rest_exp2` varchar(2000) NOT NULL,
  `rest_loc` varchar(255) NOT NULL,
  `rest_phone` varchar(20) DEFAULT NULL,
  `rest_menu` varchar(50) DEFAULT NULL,
  `rest_time` varchar(50) DEFAULT NULL,
  `rest_image` varchar(100) DEFAULT NULL,
  `moddate` datetime(6) DEFAULT CURRENT_TIMESTAMP(6),
  `regdate` datetime(6) DEFAULT CURRENT_TIMESTAMP(6),
  PRIMARY KEY (`rno`)
);



CREATE TABLE `restaurant_img` (
  `uuid` varchar(200) NOT NULL,
  `file_name` varchar(200) NOT NULL,
  `rno` bigint NOT NULL,
  PRIMARY KEY (`uuid`),
  KEY `restaurant_rno_idx` (`rno`),
  CONSTRAINT `rno_fk` FOREIGN KEY (`rno`) REFERENCES `restaurant` (`rno`)
);



CREATE TABLE `restaurant_review` (
  `review_no` bigint NOT NULL AUTO_INCREMENT,
  `review_text` varchar(255) NOT NULL,
  `mid` varchar(50) NOT NULL,
  `review_like` tinyint DEFAULT NULL,
  `rno` bigint NOT NULL,
  `moddate` datetime(6) DEFAULT CURRENT_TIMESTAMP(6),
  `regdate` datetime(6) DEFAULT CURRENT_TIMESTAMP(6),
  PRIMARY KEY (`review_no`),
  KEY `restaurant_rno_idx` (`rno`),
  KEY `mid_fk_idx` (`mid`),
  CONSTRAINT `mid_fk` FOREIGN KEY (`mid`) REFERENCES `member` (`mid`),
  CONSTRAINT `rest_rno` FOREIGN KEY (`rno`) REFERENCES `restaurant` (`rno`)
);



CREATE TABLE `restaurant_score` (
  `sno` bigint NOT NULL AUTO_INCREMENT,
  `review_no` bigint NOT NULL,
  `mid` varchar(50) NOT NULL,
  `score` int NOT NULL DEFAULT '0',
  `moddate` datetime(6) DEFAULT CURRENT_TIMESTAMP(6),
  `regdate` datetime(6) DEFAULT CURRENT_TIMESTAMP(6),
  PRIMARY KEY (`sno`),
  KEY `reply_rrno_idx` (`review_no`),
  CONSTRAINT `review_no` FOREIGN KEY (`review_no`) REFERENCES `restaurant_review` (`review_no`),
  CONSTRAINT `restaurant_score_chk_1` CHECK (((`score` >= 0) and (`score` <= 5)))
);



CREATE TABLE `tourist` (
  `tno` int NOT NULL AUTO_INCREMENT,
  `tour_name` varchar(200) NOT NULL,
  `tour_addr` varchar(500) NOT NULL,
  `tour_opentime` time DEFAULT NULL,
  `tour_closetime` time DEFAULT NULL,
  `tour_parking` tinyint DEFAULT NULL,
  `tour_content` varchar(2000) DEFAULT NULL,
  `tour_image` varchar(100) DEFAULT NULL,
  `moddate` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `regdate` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  PRIMARY KEY (`tno`)
);



CREATE TABLE `tourist_img` (
  `uuid` varchar(255) NOT NULL,
  `file_name` varchar(255) NOT NULL,
  `tno` int NOT NULL,
  PRIMARY KEY (`uuid`),
  KEY `T_NO_FK_idx` (`tno`),
  CONSTRAINT `T_NO_FK` FOREIGN KEY (`tno`) REFERENCES `tourist` (`tno`)
);