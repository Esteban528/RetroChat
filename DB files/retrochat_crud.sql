-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema retrochat
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema retrochat_crud
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema retrochat_crud
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `retrochat_crud` ;
USE `retrochat_crud` ;

-- -----------------------------------------------------
-- Table `retrochat_crud`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `retrochat_crud`.`users` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(30) NOT NULL,
  `email` VARCHAR(60) NOT NULL,
  `password` VARCHAR(70) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `retrochat_crud`.`chats`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `retrochat_crud`.`chats` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_1` INT NOT NULL,
  `user_2` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `user_1_idx` (`user_1` ASC) VISIBLE,
  INDEX `user2_idx` (`user_2` ASC) VISIBLE,
  CONSTRAINT `user1`
    FOREIGN KEY (`user_1`)
    REFERENCES `retrochat_crud`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `user2`
    FOREIGN KEY (`user_2`)
    REFERENCES `retrochat_crud`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `retrochat_crud`.`messages`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `retrochat_crud`.`messages` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `sender` INT NOT NULL,
  `message` VARCHAR(500) NULL,
  `time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `chat_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `chat_id_idx` (`chat_id` ASC) VISIBLE,
  INDEX `sender_idx` (`sender` ASC) VISIBLE,
  CONSTRAINT `chat_id`
    FOREIGN KEY (`chat_id`)
    REFERENCES `retrochat_crud`.`chats` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `sender`
    FOREIGN KEY (`sender`)
    REFERENCES `retrochat_crud`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
