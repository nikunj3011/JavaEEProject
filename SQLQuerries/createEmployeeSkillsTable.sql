USE javaeeproject;

CREATE TABLE EmployeeSkills (
	EmployeeID INT NOT NULL,
    TaskID INT NOT NULL,
    PRIMARY KEY (EmployeeID, TaskID),
    FOREIGN KEY (EmployeeID) REFERENCES employees(id),
    FOREIGN KEY (TaskID) REFERENCES tasks(id)
);

INSERT INTO EmployeeSkills
	(EmployeeID, TaskID)
VALUES
	(1, 1),
    (1, 2),
    (1, 5),
    (1, 6),
    (2, 2),
    (2, 3),
    (2, 5),
    (3, 1),
    (3, 2),
    (3, 4),
    (4, 3),
    (5, 1),
    (5, 2),
    (5, 3),
    (5, 4),
    (6, 5),
    (6, 6);

SELECT * FROM EmployeeSkills;
