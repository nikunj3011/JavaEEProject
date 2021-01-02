DROP DATABASE IF EXISTS javaeeproject;

CREATE DATABASE javaeeproject;

USE javaeeproject;

#Clients
 CREATE TABLE clients (
	id int AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(255) NULL,
    creationDate date NOT NULL,
    isDeleted boolean NOT NULL
);

INSERT INTO clients
		(name, description, creationDate, isDeleted)
VALUES ("Petooze", "Pet Store", "2020-11-20", false),
		("Teaaholic", "Everything about tea. Web Site.", "2020-11-20", false),
        ("Cozy Language", "Online language school", "2020-12-03", false);   
        
#Employees
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

#Teams
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

#Tasks
CREATE TABLE tasks (
	id int AUTO_INCREMENT PRIMARY KEY,
    taskname VARCHAR(50) NOT NULL,
    description VARCHAR(255) NULL,
	duration VARCHAR(50) NULL,
    creationDate date NOT NULL,
	lastUpdateDate date,
	isDeleted boolean default false
);

INSERT INTO tasks
	(taskname, description, duration, creationDate, lastUpdateDate)
VALUES 
("Dell R710", "Dell R710 Rack Mount Server Installation", "30", "2020-11-16","2020-11-16"),
("Red Hat Linux", "Red Hat Linux Installation", "120", "2020-11-16","2020-11-16"),
("Cisco Switch", "Cisco Switch Installation", "60", "2020-12-03", "2020-12-03"),
("Cat5e Cabling", "Cat5e Cabling Installation", "60", "2020-12-03", "2020-12-03"),
("HD Replacement", "Hard Drive Replacement", "60", "2020-12-03", "2020-12-03"),
("Floppy Disk Replacement", "Floppy Disk Replacement", "45", "2020-12-03", "2020-12-03");

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

CREATE TABLE jobComments(
	id INT(11) AUTO_INCREMENT PRIMARY KEY,
    JobID INT(11) NOT NULL,
    Comment VARCHAR(255) NOT NULL,
    creationDate date NOT NULL,
	lastUpdateDate date,
    
	FOREIGN KEY (JobID) REFERENCES jobs(id)
);

INSERT INTO jobs
	(name, description, startTime, duration, clientID, teamID, cost, isOnSite, isEmergencyCall)
VALUES 
	("Job 1", "Hard work", "2020-03-03 10:00:00", 150, 1, 1, 320.00, true, false),
    ("Job 2", "Some description...", "2020-03-03 15:00:00", 30, 1, 1, 320.00, true, false),
    ("Jpb 3", "", "2020-03-03 23:00:00", 150, 1, 1, 320.00, false, true),
    ("Job 4", "Easy to do", "2020-03-03 10:00:00", 150, 1, 1, 320.00, true, false);

#Job_Task_Joining_Table
CREATE TABLE JobSkills (
	JobID INT NOT NULL,
    TaskID INT NOT NULL,
    PRIMARY KEY (JobID, TaskID),
    FOREIGN KEY (JobID) REFERENCES jobs(ID),
    FOREIGN KEY (TaskID) REFERENCES tasks(ID)
);

INSERT INTO JobSkills
(JobID, TaskID)
VALUES 
	(1, 1),
	(1, 2),
	(1, 3),
	(1, 4),
    (2, 1),
    (2, 5),
    (3, 6),
    (3, 2),
    (4, 5);


#Employee_Skill_Joining_Table
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
    
#Employee_Team_Joining_Table
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
    
INSERT INTO jobComments
	(JobID, Comment, creationDate, lastUpdateDate)
VALUES
    (1, "this is a comment", "2020-11-16", "2020-11-18"),
	(1, "urgent", "2020-11-16", "2020-11-16"),
	(4, "some comment", "2020-11-16", "2020-11-16"),
	(2, "Comment", "2020-11-16", "2020-11-16"),
    (1, "Epsum Larum", "2020-11-16", "2020-11-16"),
    (3, "Vingardium Leviosa", "2020-11-16", "2020-11-16"),
    (3, "Alohomora", "2020-11-16", "2020-11-16");
    
#Stored_Procedures
#Clients
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

#Jobs
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
		(name, description, startTime, duration, clientID, teamID, cost, isOnSite, isEmergencyCall)
	VALUES 
		(name, 
        description, 
        startTime, 
        duration,
		clientID, 
        teamID, 
        cost, 
        isOnSite, 
        isEmergencyCall);
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE getLastJobId ()
BEGIN
	SELECT ID
    FROM jobs
    ORDER BY ID DESC LIMIT 1;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE getJobs ()
BEGIN
	SELECT *
    FROM jobs
    WHERE isDeleted = false;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE getJobsBetween(IN sTime VARCHAR(10), eTime VARCHAR(10))
BEGIN
	SELECT * 
	FROM Jobs
	WHERE startTime >= sTime 
		AND startTime <= eTime
        AND isDeleted = false;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE getJob (IN idToFind INT(11))
BEGIN
	SELECT *
    FROM jobs
    WHERE id = idToFind;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE deleteJob (IN jobId INT(11))
BEGIN
	UPDATE jobs
	SET isDeleted = true
	WHERE id = jobId;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE getTasksForJob (IN jID INT)
BEGIN
	SELECT TaskID
	FROM jobskills
	WHERE JobID = jID;
END $$
DELIMITER ;

#Employees
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
	SELECT id, firstName, LastName, sin, hourlyPayRate, creationDate, lastUpdateDate, isDeleted
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
CREATE PROCEDURE getTeamsForEmployee(IN tID INT(11))
BEGIN
	SELECT id, teamname, iscurrentlyOnCall, creationDate
    FROM teams
    INNER JOIN employeesInTeams ON (teams.id = employeesInTeams.teamID) 
    WHERE (employeesInTeams.employeeID = employeeID) 
		AND NOT isDeleted;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE addEmployeeTeam(IN eID INT(11), tID INT(11))
BEGIN
	INSERT INTO employeesinteams
		(EmployeeID, TeamID)
    VALUES 
		(eID, tID);
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

#Tasks
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
	SELECT id, taskname, description, duration, creationDate,lastUpdateDate, isDeleted
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

#Teams
DELIMITER $$
CREATE PROCEDURE createTeams
(IN teamName VARCHAR(50), isCurrentlyOnCall boolean, creationDate date, isDeleted boolean, OUT TeamID INT)
BEGIN
	INSERT INTO teams
		(teamName, isCurrentlyOnCall, creationDate, isDeleted)
    VALUES 
		(teamName, isCurrentlyOnCall, creationDate, isDeleted);
		
	SET teamID = LAST_INSERT_ID();
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
	SELECT id, teamName, isCurrentlyOnCall, creationDate
    FROM teams
    WHERE id = teamID;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE getTeamWithEmployees (IN teamID INT(11))
BEGIN
	SELECT id, teamName, isCurrentlyOnCall, creationDate, EmployeeID
	FROM teams
	INNER JOIN employeesinteams
		ON teams.id = employeesinteams.TeamID
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

DELIMITER $$
CREATE PROCEDURE removeEmployeesFromTeam(IN tID INT(11))
BEGIN
	DELETE FROM employeesinteams
	WHERE TeamId = tID;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE getSkillsForTeam (IN tID INT(11))
BEGIN
	SELECT employeeskills.TaskID
	FROM employeesinteams
		INNER JOIN employeeskills
			ON employeesinteams.EmployeeID = employeeskills.EmployeeID
	WHERE teamID = tID
	GROUP BY employeeskills.TaskID;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE removeSkillFromEmployee (IN eID INT(11), sID INT(11))
BEGIN
	DELETE FROM employeeskills
	WHERE EmployeeID = eID AND TaskID = sID;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE addSkillForEmployee (IN eID INT(11), sID INT(11))
BEGIN
	INSERT INTO employeeskills
		(EmployeeID, TaskID)
    VALUES 
		(eID, sID);
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE addJobTask (IN jID INT(11), tID INT(11))
BEGIN
	INSERT INTO jobskills
		(JobID, TaskID)
    VALUES 
		(jID, tID);
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE getCommentsForJob (IN jID INT(11))
BEGIN
	SELECT id, jobID, Comment, creationDate, lastUpdateDate
	FROM jobComments
	WHERE jobID = jID;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE updateJobComment (IN comID INT(11), com VARCHAR(255), updateDate date)
BEGIN
	UPDATE jobComments 
		SET 
			Comment = com,
            lastUpdateDate = updateDate
	WHERE
		id = comID;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE deleteJobComment (IN comID INT(11))
BEGIN
	DELETE FROM jobComments
	WHERE id = comID;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE createJobComment
(IN jID INT, comm VARCHAR(255), crDate date)
BEGIN
	INSERT INTO jobComments
		(jobID, Comment, creationDate, lastUpdateDate)
    VALUES 
		(jID, comm, crDate, crDate);
END $$
DELIMITER ;

