USE javaeeproject;

DELIMITER $$
CREATE PROCEDURE createTeams
(IN teamName VARCHAR(50), isCurrentlyOnCall boolean, creationDate date, isDeleted boolean)
BEGIN
	INSERT INTO teams
		(teamName, isCurrentlyOnCall, creationDate, isDeleted)
    VALUES 
		(teamName, isCurrentlyOnCall, creationDate, isDeleted);
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE updateTeams
(IN teamID INT(11), teamName VARCHAR(50), isCurrentlyOnCall boolean, creationDate date, isDeleted boolean)
BEGIN
	UPDATE teams 
		SET 
			teamName = teamName,
            isCurrentlyOnCall = isCurrentlyOnCall,
            creationDate = creationDate,
            isDeleted = isDeleted
	WHERE
		id = teamID;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE getTeams ()
BEGIN
	SELECT  id, teamName, isCurrentlyOnCall, creationDate
    FROM teams
	WHERE isDeleted = FALSE;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE getTeam(IN teamID INT(11))
BEGIN
	SELECT (id, teamName, isCurrentlyOnCall, creationDate)
    FROM teams
    WHERE id = teamID;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE deleteTeams (IN teamID INT(11))
BEGIN
	UPDATE teams 
		SET 
			isDeleted = TRUE
	WHERE
		id = teamID;
END $$
DELIMITER ;



