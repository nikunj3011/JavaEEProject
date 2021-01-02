USE javaeeproject;

DELIMITER $$

CREATE PROCEDURE getJob (IN idToFind INT(11))
BEGIN
	SELECT *
    FROM jobs
    WHERE id = idToFind;
END $$

DELIMITER ;