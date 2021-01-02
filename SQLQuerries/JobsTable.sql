USE javaeeproject;

CREATE TABLE jobs (
	id INT(11) AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(255) NULL,
	startTime DATETIME NOT NULL,
    duration INT(11) NOT NULL,
    clientID INT(11),
    teamID INT(11),
    cost DECIMAL(10, 2) NOT NULL,
    isOnSite boolean NOT NULL,
    isEmergencyCall boolean NOT NULL,
	isDeleted boolean default false,
    FOREIGN KEY (clientID) REFERENCES clients(id),
    FOREIGN KEY (teamID) REFERENCES teams(id)
);

INSERT INTO jobs
	(name, description, startTime, duration, clientID, teamID, cost, isOnSite, isEmergencyCall, isDeleted)
VALUES 
	("Red Hat, Rack Mount, Switch, Cabling", "Red Hat Linux: 120min; Rack Mount Server: 30min; Cisco Switch: 60min; Cat5e Cabling: 60 min", "2020-03-03 10:00:00", 150,
		1, 1, 320.00, true, false, false);

SELECT * FROM jobs;