
-- Création de la base
CREATE DATABASE IF NOT EXISTS mdd_db;
USE mdd_db;


-- TABLE : user

CREATE TABLE IF NOT EXISTS `user` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(50) NOT NULL UNIQUE,
    `email` VARCHAR(100) NOT NULL UNIQUE,
    `password` VARCHAR(255) NOT NULL,
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- TABLE : topic

CREATE TABLE IF NOT EXISTS `topic` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `title` VARCHAR(100) NOT NULL UNIQUE,
    `description` TEXT NOT NULL,
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- TABLE : article

CREATE TABLE IF NOT EXISTS `article` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `title` VARCHAR(200) NOT NULL,
    `content` TEXT NOT NULL,
    `author_id` BIGINT NOT NULL,
    `topic_id` BIGINT NOT NULL,
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`author_id`) REFERENCES `user`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`topic_id`) REFERENCES `topic`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- TABLE : subscription

CREATE TABLE IF NOT EXISTS `subscription` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `user_id` BIGINT NOT NULL,
    `topic_id` BIGINT NOT NULL,
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `unique_subscription` (`user_id`, `topic_id`),
    FOREIGN KEY (`user_id`) REFERENCES `user`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`topic_id`) REFERENCES `topic`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- TABLE : comment

CREATE TABLE IF NOT EXISTS `comment` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `content` TEXT NOT NULL,
    `article_id` BIGINT NOT NULL,
    `author_id` BIGINT NOT NULL,
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`article_id`) REFERENCES `article`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`author_id`) REFERENCES `user`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- DONNÉES DE TEST : Topics

INSERT INTO `topic` (`title`, `description`) VALUES
('JavaScript', 'Tout sur JavaScript, le langage du web moderne. ES6+, TypeScript, et les frameworks comme React, Angular et Vue.js.'),
('Java', 'Le langage Java, Spring Boot, Hibernate, et tout l''écosystème JVM. Architecture d''entreprises et microservices.'),
('Python', 'Python et ses frameworks : Django, Flask. Data science, machine learning, et automatisation.'),
('Web3', 'Blockchain, Ethereum, smart contracts, et la décentralisation. Découvrez le futur du web.'),
('React', 'React, Next.js, Hooks, et l''écosystème frontend. Développez des interfaces utilisateur modernes.'),
('DevOps', 'CI/CD, Docker, Kubernetes, et l''automatisation des déploiements. Administrez et scalabilisez vos applications.'),
('Spring Boot', 'Spring Boot, Spring Security, JPA, et l''écosystème Spring pour des applications Java robustes.'),
('Angular', 'Angular, RxJS, NGRX, et tout l''univers du framework de Google pour des applications frontend structurées.'),
('Vue.js', 'Vue.js, Pinia, Vue Router, et la simplicité du framework progressif pour vos interfaces utilisateur.'),
('AI & Machine Learning', 'Intelligence artificielle, machine learning, deep learning, et les bibliothèques Python comme TensorFlow et PyTorch.');