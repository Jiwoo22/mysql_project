-- MySQL Script generated by MySQL Workbench
-- Fri Jun  2 12:55:35 2023
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema hospital
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema hospital
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `hospital` DEFAULT CHARACTER SET utf8 ;
USE `hospital` ;

-- -----------------------------------------------------
-- Table `hospital`.`Insurance`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hospital`.`Insurance` (
  `Insurance_id` INT NOT NULL AUTO_INCREMENT,
  `type` VARCHAR(45) NULL,
  `expiration_date` VARCHAR(45) NULL,
  PRIMARY KEY (`Insurance_id`),
  UNIQUE INDEX `Insurance_id_UNIQUE` (`Insurance_id` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hospital`.`Payrolls`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hospital`.`Payrolls` (
  `payroll_id` INT NOT NULL AUTO_INCREMENT,
  `net_salary` FLOAT NOT NULL,
  `bonus_salary` FLOAT NULL,
  `account_number` VARCHAR(45) NULL,
  PRIMARY KEY (`payroll_id`),
  UNIQUE INDEX `payroll_id_UNIQUE` (`payroll_id` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hospital`.`Employees`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hospital`.`Employees` (
  `employee_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `DOB` VARCHAR(45) NULL,
  `payroll_id` INT NOT NULL,
  PRIMARY KEY (`employee_id`),
  INDEX `fk_Employees_Payrolls1_idx` (`payroll_id` ASC) VISIBLE,
  UNIQUE INDEX `employee_id_UNIQUE` (`employee_id` ASC) VISIBLE,
  CONSTRAINT `fk_Employees_Payrolls1`
    FOREIGN KEY (`payroll_id`)
    REFERENCES `hospital`.`Payrolls` (`payroll_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hospital`.`Doctors`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hospital`.`Doctors` (
  `doctor_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `specialization` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `employee_id` INT NOT NULL,
  PRIMARY KEY (`doctor_id`),
  UNIQUE INDEX `DoctorID_UNIQUE` (`doctor_id` ASC) VISIBLE,
  INDEX `fk_Doctors_Employees1_idx` (`employee_id` ASC) VISIBLE,
  CONSTRAINT `fk_Doctors_Employees1`
    FOREIGN KEY (`employee_id`)
    REFERENCES `hospital`.`Employees` (`employee_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hospital`.`Patients`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hospital`.`Patients` (
  `patient_id` BIGINT(16) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `DOB` DATE NOT NULL,
  `phone` VARCHAR(45) NULL,
  `insurance_id` INT NOT NULL,
  `doctor_id` INT NOT NULL,
  PRIMARY KEY (`patient_id`),
  UNIQUE INDEX `PatientID_UNIQUE` (`patient_id` ASC) VISIBLE,
  INDEX `fk_Patients_Insurance1_idx` (`insurance_id` ASC) VISIBLE,
  INDEX `fk_Patients_Doctors1_idx` (`doctor_id` ASC) VISIBLE,
  CONSTRAINT `fk_Patients_Insurance1`
    FOREIGN KEY (`insurance_id`)
    REFERENCES `hospital`.`Insurance` (`Insurance_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Patients_Doctors1`
    FOREIGN KEY (`doctor_id`)
    REFERENCES `hospital`.`Doctors` (`doctor_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hospital`.`Nurses`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hospital`.`Nurses` (
  `nurse_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NULL,
  `employee_id` INT NOT NULL,
  PRIMARY KEY (`nurse_id`),
  UNIQUE INDEX `DoctorID_UNIQUE` (`nurse_id` ASC) VISIBLE,
  INDEX `fk_Nurses_Employees1_idx` (`employee_id` ASC) VISIBLE,
  CONSTRAINT `fk_Nurses_Employees1`
    FOREIGN KEY (`employee_id`)
    REFERENCES `hospital`.`Employees` (`employee_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hospital`.`Departments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hospital`.`Departments` (
  `department_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `head` INT NOT NULL,
  PRIMARY KEY (`department_id`),
  UNIQUE INDEX `idDepartments_UNIQUE` (`department_id` ASC) VISIBLE,
  INDEX `fk_Departments_Doctors1_idx` (`head` ASC) VISIBLE,
  CONSTRAINT `fk_Departments_Doctors1`
    FOREIGN KEY (`head`)
    REFERENCES `hospital`.`Doctors` (`doctor_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hospital`.`Procedures`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hospital`.`Procedures` (
  `code` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `cost` REAL NULL,
  PRIMARY KEY (`code`),
  UNIQUE INDEX `procedure_id_UNIQUE` (`code` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hospital`.`Medications`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hospital`.`Medications` (
  `medication_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `brand` VARCHAR(45) NULL,
  `description` VARCHAR(45) NULL,
  PRIMARY KEY (`medication_id`),
  UNIQUE INDEX `medication_id_UNIQUE` (`medication_id` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hospital`.`Block`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hospital`.`Block` (
  `floor` INT NOT NULL,
  `code` INT NOT NULL,
  PRIMARY KEY (`floor`, `code`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hospital`.`Rooms`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hospital`.`Rooms` (
  `number` INT NOT NULL AUTO_INCREMENT,
  `type` VARCHAR(45) NULL,
  `capacity` INT NULL,
  `Block_floor` INT NOT NULL,
  `Block_code` INT NOT NULL,
  PRIMARY KEY (`number`),
  UNIQUE INDEX `room_id_UNIQUE` (`number` ASC) VISIBLE,
  INDEX `fk_Rooms_Block1_idx` (`Block_floor` ASC, `Block_code` ASC) VISIBLE,
  CONSTRAINT `fk_Rooms_Block1`
    FOREIGN KEY (`Block_floor` , `Block_code`)
    REFERENCES `hospital`.`Block` (`floor` , `code`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hospital`.`Appointments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hospital`.`Appointments` (
  `appointment_id` INT NOT NULL AUTO_INCREMENT,
  `room_id` INT NOT NULL,
  `start_time` DATETIME NOT NULL,
  `end_time` DATETIME NOT NULL,
  `doctor_id` INT NOT NULL,
  `nurse_id` INT NOT NULL,
  `patient_id` BIGINT(16) NOT NULL,
  PRIMARY KEY (`appointment_id`),
  INDEX `fk_Appointments_Rooms1_idx` (`room_id` ASC) VISIBLE,
  UNIQUE INDEX `appointment_id_UNIQUE` (`appointment_id` ASC) VISIBLE,
  INDEX `fk_Appointments_Doctors1_idx` (`doctor_id` ASC) VISIBLE,
  INDEX `fk_Appointments_Nurses1_idx` (`nurse_id` ASC) VISIBLE,
  INDEX `fk_Appointments_Patients1_idx` (`patient_id` ASC) VISIBLE,
  CONSTRAINT `fk_Appointments_Rooms1`
    FOREIGN KEY (`room_id`)
    REFERENCES `hospital`.`Rooms` (`number`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Appointments_Doctors1`
    FOREIGN KEY (`doctor_id`)
    REFERENCES `hospital`.`Doctors` (`doctor_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Appointments_Nurses1`
    FOREIGN KEY (`nurse_id`)
    REFERENCES `hospital`.`Nurses` (`nurse_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Appointments_Patients1`
    FOREIGN KEY (`patient_id`)
    REFERENCES `hospital`.`Patients` (`patient_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hospital`.`Doctors_Departments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hospital`.`Doctors_Departments` (
  `doctor_id` INT NOT NULL,
  `department_id` INT NOT NULL,
  PRIMARY KEY (`doctor_id`, `department_id`),
  INDEX `fk_Doctors_has_Departments_Departments1_idx` (`department_id` ASC) VISIBLE,
  INDEX `fk_Doctors_has_Departments_Doctors1_idx` (`doctor_id` ASC) VISIBLE,
  CONSTRAINT `fk_Doctors_has_Departments_Doctors1`
    FOREIGN KEY (`doctor_id`)
    REFERENCES `hospital`.`Doctors` (`doctor_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Doctors_has_Departments_Departments1`
    FOREIGN KEY (`department_id`)
    REFERENCES `hospital`.`Departments` (`department_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hospital`.`Prescribes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hospital`.`Prescribes` (
  `doctor_id` INT NOT NULL,
  `patient_id` BIGINT(16) NOT NULL,
  `medication_id` INT NOT NULL,
  `date` DATE NOT NULL,
  `appointment_id` INT NOT NULL,
  INDEX `fk_Prescribes_Doctors1_idx` (`doctor_id` ASC) VISIBLE,
  INDEX `fk_Prescribes_Patients1_idx` (`patient_id` ASC) VISIBLE,
  INDEX `fk_Prescribes_Medications1_idx` (`medication_id` ASC) VISIBLE,
  INDEX `fk_Prescribes_Appointments1_idx` (`appointment_id` ASC) VISIBLE,
  PRIMARY KEY (`doctor_id`, `patient_id`, `medication_id`, `date`),
  CONSTRAINT `fk_Prescribes_Doctors1`
    FOREIGN KEY (`doctor_id`)
    REFERENCES `hospital`.`Doctors` (`doctor_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Prescribes_Patients1`
    FOREIGN KEY (`patient_id`)
    REFERENCES `hospital`.`Patients` (`patient_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Prescribes_Medications1`
    FOREIGN KEY (`medication_id`)
    REFERENCES `hospital`.`Medications` (`medication_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Prescribes_Appointments1`
    FOREIGN KEY (`appointment_id`)
    REFERENCES `hospital`.`Appointments` (`appointment_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hospital`.`Undergoes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hospital`.`Undergoes` (
  `date` DATETIME NOT NULL,
  `patient_id` BIGINT(16) NOT NULL,
  `procedures_code` INT NOT NULL,
  `doctors_doctor_id` INT NOT NULL,
  INDEX `fk_Undergoes_Patients1_idx` (`patient_id` ASC) VISIBLE,
  INDEX `fk_Undergoes_Procedures1_idx` (`procedures_code` ASC) VISIBLE,
  INDEX `fk_Undergoes_Doctors1_idx` (`doctors_doctor_id` ASC) VISIBLE,
  PRIMARY KEY (`patient_id`, `procedures_code`),
  CONSTRAINT `fk_Undergoes_Patients1`
    FOREIGN KEY (`patient_id`)
    REFERENCES `hospital`.`Patients` (`patient_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Undergoes_Procedures1`
    FOREIGN KEY (`procedures_code`)
    REFERENCES `hospital`.`Procedures` (`code`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Undergoes_Doctors1`
    FOREIGN KEY (`doctors_doctor_id`)
    REFERENCES `hospital`.`Doctors` (`doctor_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;