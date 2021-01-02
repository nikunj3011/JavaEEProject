USE javaeeproject;

CREATE TABLE EmployeesInTeams (
	EmployeeID INT NOT NULL,
    TeamID INT NOT NULL,
    PRIMARY KEY (EmployeeID, TeamID),
    FOREIGN KEY (EmployeeID) REFERENCES employees(id),
    FOREIGN KEY (TeamID) REFERENCES teams(id)
);

INSERT INTO EmployeesInTeams
	(EmployeeID, TeamID)
VALUES
	(1, 1),
    (2, 1),
    (3, 2),
    (4, 2),
    (5, 3),
    (6, 3);

SELECT * FROM EmployeesInTeams;
