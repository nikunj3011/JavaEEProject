USE javaeeproject;

DELIMITER $$

CREATE PROCEDURE getJobs ()
BEGIN
	SELECT *
    FROM jobs;
END $$

DELIMITER ;