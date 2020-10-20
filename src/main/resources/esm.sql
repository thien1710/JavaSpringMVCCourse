UNLOCK TABLES;

DROP TABLE IF EXISTS `projects`;
DROP TABLE IF EXISTS `customers`;
DROP TABLE IF EXISTS `departments`;
DROP TABLE IF EXISTS `users`;
DROP TABLE IF EXISTS `roles`;
DROP TABLE IF EXISTS `user_role`;
DROP TABLE IF EXISTS `info_login`;
DROP TABLE IF EXISTS `messages`;
DROP TABLE IF EXISTS `department_user`;

CREATE TABLE `messages` (
  `id` bigint(19) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255),
  `description` varchar(255),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `users` (
  `id` bigint(19) unsigned NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `address` varchar(255),
  `phone` varchar(255),
  `website` varchar(255),
  `salary_num` bigint(19),
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `departments` (
  `id` bigint(19) unsigned NOT NULL AUTO_INCREMENT,
  `department_name` varchar(255) NOT NULL,
  `qty` varchar(255),
  `note` varchar(255),
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` bigint(19) unsigned DEFAULT NULL,
  `updated_by` bigint(19) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `department_user` (
  `id` bigint(19) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(19) unsigned NOT NULL,
  `department_id` bigint(19) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user_id` (`user_id`),
  KEY `fk_department_id` (`department_id`),
  CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `fk_department_id` FOREIGN KEY (`department_id`) REFERENCES `departments` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `roles` (
  `id` bigint(19) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `user_role` (
  `id` bigint(19) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(19) unsigned NOT NULL,
  `role_id` bigint(19) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_security_user_id` (`user_id`),
  KEY `fk_security_role_id` (`role_id`),
  CONSTRAINT `fk_security_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `fk_security_role_id` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `info_login` (
  `id` bigint(19) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(19) unsigned DEFAULT NULL,
  `token` varchar(255),
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` bigint(19) unsigned DEFAULT NULL,
  `updated_by` bigint(19) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_infologin_user` (`user_id`),
  CONSTRAINT `fk_infologin_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


CREATE TABLE `customers` (
  `id` bigint(19) unsigned NOT NULL AUTO_INCREMENT,
  `customer_name` varchar(255) NOT NULL,
  `phone` varchar(255),
  `email` varchar(255) NOT NULL,
  `address` varchar(255),
  `filed` varchar(255),
  `note` varchar(255),
  `user_id` bigint(19) unsigned DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` bigint(19) unsigned DEFAULT NULL,
  `updated_by` bigint(19) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_customer_user` (`user_id`),
  CONSTRAINT `fk_customer_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `projects` (
  `id` bigint(19) unsigned NOT NULL AUTO_INCREMENT,
  `project_name` varchar(255) NOT NULL,
  `start_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `finish_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `total_time` bigint(19),
  `budget` bigint(19),
  `status` varchar(255),
  `note` varchar(255),
  `customer_id` bigint(19) unsigned DEFAULT NULL,
  `user_id` bigint(19) unsigned DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` bigint(19) unsigned NOT NULL,
  `updated_by` bigint(19) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_project_customer` (`customer_id`),
  KEY `fk_project_user` (`user_id`),
  CONSTRAINT `fk_project_customer` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`),
  CONSTRAINT `fk_project_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


LOCK TABLES `roles` WRITE;
INSERT INTO `roles` VALUES (1,'ROLE_ADMIN'),(2,'ROLE_USER');
UNLOCK TABLES;