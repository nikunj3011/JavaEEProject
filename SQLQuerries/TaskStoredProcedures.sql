USE javaeeproject;

DELIMITER $$
CREATE PROCEDURE createTask
(IN taskname VARCHAR(50), description VARCHAR(255), duration VARCHAR(50), creationDate date)
BEGIN
	INSERT INTO tasks
		(taskname, description, duration, creationDate, lastUpdateDate)
    VALUES 
		(taskname, description, duration, creationDate, creationDate);
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE updateTask
(IN taskID INT(11), taskname VARCHAR(50), description VARCHAR(255), duration VARCHAR(50), creationDate date, updateDate date)
BEGIN
	UPDATE tasks 
		SET 
			taskname = taskname,
            description = description,
            duration = duration,
            creationDate = creationDate,
            lastUpdateDate = updateDate
	WHERE
		id = taskID;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE getTasks ()
BEGIN
	SELECT  id, taskname, description, duration, creationDate,lastUpdateDate
    FROM Tasks 
	WHERE isDeleted = FALSE;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE getTask (IN TaskID INT(11))
BEGIN
	SELECT (id, taskname, description, duration, creationDate,lastUpdateDate, isDeleted)
    FROM Tasks
    WHERE id = TaskID;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE deleteTask (IN taskID INT(11))
BEGIN
	UPDATE tasks 
		SET 
			isDeleted = TRUE
	WHERE
		id = taskID;
END $$
DELIMITER ;



