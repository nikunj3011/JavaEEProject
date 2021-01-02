USE javaeeproject;

DELIMITER $$
CREATE PROCEDURE createEmployee 
(IN firstName VARCHAR(50), lastName VARCHAR(50), sin CHAR(9), hourlyPayRate double, crDate date)
BEGIN
	INSERT INTO employees
		(firstName, LastName, sin, hourlyPayRate, creationDate, lastUpdateDate)
    VALUES 
		(firstName, lastName, sin, hourlyPayRate, crDate, crDate);
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE updateEmployee 
(IN employeeID INT(11), firstName VARCHAR(50), lastName VARCHAR(50), sin CHAR(9), hourlyPayRate double, crDate date, updateDate date)
BEGIN
	UPDATE employees 
		SET 
			firstName = firstName,
            lastName = lastName,
            sin = sin,
            hourlyPayRate = hourlyPayRate,
            creationDate = crDate,
            lastUpdateDate = updateDate
	WHERE
		id = employeeID;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE getEmployees ()
BEGIN
	SELECT  id, firstName, LastName, sin, hourlyPayRate, creationDate, lastUpdateDate
    FROM Employees
    WHERE isDeleted = FALSE;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE getEmployee (IN employeeID INT(11))
BEGIN
	SELECT (id, firstName, LastName, sin, hourlyPayRate, creationDate, lastUpdateDate, isDeleted)
    FROM Employees
    WHERE id = employeeID;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE deleteEmployee (IN employeeID INT(11))
BEGIN
	UPDATE employees 
		SET 
			isDeleted = TRUE
	WHERE
		id = employeeID;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE getTeamsForEmployee (IN employeeID INT(11))
BEGIN
	SELECT id, teamname, iscurrentlyOnCall, creationDate
    FROM teams
    INNER JOIN employeesInTeams ON (teams.id = employeesInTeams.teamID) 
    WHERE (employeesInTeams.employeeID = employeeID) 
		AND NOT isDeleted;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE getTasksForEmployee (IN employeeID INT(11))
BEGIN
	SELECT id, taskname, description, duration, creationDate
    FROM tasks
    INNER JOIN employeeskills ON (tasks.id = employeeskills.taskid) 
    WHERE (employeeskills.employeeID = employeeID) 
		AND NOT isDeleted;
END $$
DELIMITER ;

SELECT
    select_list
FROM t1
INNER JOIN t2 ON join_condition1




