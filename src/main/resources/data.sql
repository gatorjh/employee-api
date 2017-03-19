INSERT INTO Department (name) VALUES ('Marketing');
INSERT INTO Department (name) VALUES ('Finance');
INSERT INTO Department (name) VALUES ('Information Technology');
INSERT INTO Department (name) VALUES ('Sales');

INSERT INTO Employee (firstName, lastName, hireDate) VALUES ('Jerry', 'Haynes', '2005-01-24');
INSERT INTO Employee (firstName, lastName, hireDate) VALUES ('Jonathan', 'Smith', '2010-06-10');
INSERT INTO Employee (firstName, lastName, hireDate) VALUES ('Jeremy', 'Mitchel', '2012-03-19');
INSERT INTO Employee (firstName, lastName, hireDate) VALUES ('Deborah', 'Cloud', '2015-09-25');

INSERT INTO DeptEmpl (emplId, deptId, fromDate, toDate) VALUES (1, 3, '2005-01-24', null);
INSERT INTO DeptEmpl (emplId, deptId, fromDate, toDate) VALUES (2, 2, '2010-06-10', null);
INSERT INTO DeptEmpl (emplId, deptId, fromDate, toDate) VALUES (3, 1, '2012-03-19', null);
INSERT INTO DeptEmpl (emplId, deptId, fromDate, toDate) VALUES (4, 4, '2015-09-25', null);

INSERT INTO Salary (emplId, salary, fromDate, toDate) VALUES (1, 90000, '2005-01-24', '2006-01-24');
INSERT INTO Salary (emplId, salary, fromDate, toDate) VALUES (1, 95000, '2006-01-24', '2007-01-24');
INSERT INTO Salary (emplId, salary, fromDate, toDate) VALUES (1, 100000, '2007-01-24', '2008-01-24');
INSERT INTO Salary (emplId, salary, fromDate, toDate) VALUES (1, 105000, '2008-01-24', null);
INSERT INTO Salary (emplId, salary, fromDate, toDate) VALUES (2, 50000, '2010-06-10', null);
INSERT INTO Salary (emplId, salary, fromDate, toDate) VALUES (3, 50000, '2012-03-19', null);
INSERT INTO Salary (emplId, salary, fromDate, toDate) VALUES (4, 50000, '2015-09-25', null);