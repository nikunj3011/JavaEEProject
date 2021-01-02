USE javaeeproject;

CREATE TABLE employees (
	id int AUTO_INCREMENT PRIMARY KEY,
    firstName VARCHAR(50) NOT NULL,
    lastName VARCHAR(50) NOT NULL,
    sin char(9) NOT NULL UNIQUE,
    hourlyPayRate double NOT NULL,
    creationDate date NOT NULL,
	lastUpdateDate date,
    isDeleted BOOLEAN DEFAULT FALSE
);

INSERT INTO employees
	(firstName, LastName, sin, hourlyPayRate, creationDate, lastUpdateDate)
VALUES 
("Alex", "Noseworthy", "111111111", 65, "2020-11-12", "2020-11-12"),
("Brett", "Lund", "222222222", 60, "2020-11-12", "2020-11-12"),
("Marco", "Corsini", "333333333", 70, "2020-12-02", "2020-12-02"),
("Jeff", "Ottar", "444444444", 60, "2020-12-02", "2020-12-02"),
("Lucas", "Parker", "555555555", 80, "2020-12-02", "2020-12-02"),
("Hugh", "Landry", "666666666", 65, "2020-12-02", "2020-12-02");

SELECT * FROM employees;