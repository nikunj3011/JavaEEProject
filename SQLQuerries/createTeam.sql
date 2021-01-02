USE javaeeproject;

CREATE TABLE teams (
	id int AUTO_INCREMENT PRIMARY KEY,
    teamName VARCHAR(50) NOT NULL,
    isCurrentlyOnCall boolean default false,
    creationDate date NOT NULL,
	isDeleted boolean default false
);

INSERT INTO teams
	(teamName, isCurrentlyOnCall, creationDate)
VALUES 
("Alpha", false, "2020-11-16"),
("Bravo", false , "2020-11-16"),
("Charlie", false, "2020-12-02");

SELECT * FROM teams;