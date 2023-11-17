-- Crear esquema retrochat_crud si no existe
CREATE SCHEMA IF NOT EXISTS `retrochat_crud`;

-- Utilizar el esquema retrochat_crud
USE `retrochat_crud`;

-- Crear tabla `users`
CREATE TABLE IF NOT EXISTS `users` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(30) NOT NULL,
  `email` VARCHAR(60) NOT NULL,
  `password` VARCHAR(70) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

-- Crear tabla `chats`
CREATE TABLE IF NOT EXISTS `chats` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_1` INT NOT NULL,
  `user_2` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `user_1_idx` (`user_1`),
  INDEX `user_2_idx` (`user_2`),
  CONSTRAINT `user1`
    FOREIGN KEY (`user_1`)
    REFERENCES `users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `user2`
    FOREIGN KEY (`user_2`)
    REFERENCES `users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
) ENGINE=InnoDB;

-- Crear tabla `messages`
CREATE TABLE IF NOT EXISTS `messages` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `sender` INT NOT NULL,
  `message` VARCHAR(500) NULL,
  `time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `chat_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `chat_id_idx` (`chat_id`),
  INDEX `sender_idx` (`sender`),
  CONSTRAINT `chat_id`
    FOREIGN KEY (`chat_id`)
    REFERENCES `chats` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `sender`
    FOREIGN KEY (`sender`)
    REFERENCES `users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
) ENGINE=InnoDB;

