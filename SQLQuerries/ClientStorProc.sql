USE javaeeproject;

DELIMITER $$

CREATE PROCEDURE createClient (IN n VARCHAR(50), d VARCHAR(255), crDate date)
BEGIN
	INSERT INTO clients
    (name, description, creationDate, isDeleted)
    VALUES (n, d, crDate, false);
END $$

DELIMITER ;


DELIMITER $$

CREATE PROCEDURE getClients ()
BEGIN
	SELECT *
    FROM clients
    WHERE isDeleted = FALSE;
END $$

DELIMITER ;


DELIMITER $$

CREATE PROCEDURE getClient (IN clientID INT(11))
BEGIN
	SELECT *
    FROM clients
    WHERE id = clientID;
END $$

DELIMITER ;


DELIMITER $$

CREATE PROCEDURE updateClient (IN idToUpdate INT, n VARCHAR(50), d VARCHAR(255), crDate date)
BEGIN
	UPDATE clients
    SET name = n,
		description = d,
        creationDate = crDate,
        isDeleted = false
	WHERE id = idToUpdate;
END $$

DELIMITER ;

DELIMITER $$
CREATE PROCEDURE deleteClient (IN clientId INT(11))
BEGIN
	UPDATE clients
    SET isDeleted = true
	WHERE id = clientId;
END $$
DELIMITER ;


