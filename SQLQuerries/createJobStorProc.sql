USE javaeeproject;

DELIMITER $$

CREATE PROCEDURE createJob (IN name VARCHAR(50),
								description VARCHAR(255),
                                startTime DATETIME,
                                duration INT(11),
                                clientID INT(11),
                                teamID INT(11),
                                cost DECIMAL(10,2),
                                isOnSite boolean,
                                isEmergencyCall boolean)
BEGIN
	INSERT INTO jobs
		(name, description, startTime, duration, clientID, teamID, cost, isOnSite, isEmergencyCall, isDeleted)
	VALUES 
		(name, 
        description, 
        startTime, 
        duration,
		clientID, 
        teamID, 
        cost, 
        isOnSite, 
        isEmergencyCall, 
        false);
END $$

DELIMITER ;
