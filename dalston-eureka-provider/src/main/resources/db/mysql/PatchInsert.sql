DROP PROCEDURE IF EXISTS `PatchInsert`;
USE `cloud`;

DELIMITER //  
CREATE PROCEDURE PatchInsert()  
  BEGIN  
    DECLARE i INT DEFAULT 1000000;
    WHILE i<=2000000 DO
      INSERT INTO department(dept_name, source) VALUES(CONCAT("测试部",i), "DB");
      SET i=i+1;
  END WHILE;
END;  
//  