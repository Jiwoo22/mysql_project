-- # 10 statements for insertions
INSERT INTO hospital.Doctors VALUES(1, 'John Doe', 'Family medicine', 'jdoe@example.com', 1);
INSERT INTO hospital.Doctors VALUES (2, 'Jane Smith', 'Family medicine', 'jsmith@example.com', 2);
INSERT INTO hospital.Doctors VALUES (3, 'Bob Kelso', 'Internal medicine', 'bkelso@example.com', 3);
INSERT INTO hospital.Nurses VALUES (1, 'Dana', 'dana@example.com', 4);
INSERT INTO hospital.Employees VALUES (1, 'John Doe', '1980-05-01', 1);
INSERT INTO hospital.Employees VALUES (2, 'Jane Smith', '1970-05-01', 2);
INSERT INTO hospital.Employees VALUES (3, 'Bob Kelso', '1975-05-01', 3);
INSERT INTO hospital.Employees VALUES (4, 'Dana', '1979-05-01', 4);
INSERT INTO hospital.Departments VALUES(1,'General Medicine',2);
INSERT INTO hospital.Departments VALUES(2,'Surgery',3);
INSERT INTO hospital.Departments VALUES(3,'Pediatrics',1);
INSERT INTO hospital.Doctors_Departments VALUES(1,1);
INSERT INTO hospital.Doctors_Departments VALUES(2,1);
INSERT INTO hospital.Doctors_Departments VALUES(3,2);
INSERT INTO hospital.Doctors_Departments VALUES(3,1);
INSERT INTO hospital.Procedures VALUES(1,'CT Scan',1500.0);
INSERT INTO hospital.Patients VALUES(1,'John Smith','1988-10-01','555-555-5555', 1 , 1);
INSERT INTO hospital.Appointments VALUES(1, 1,'2023-06-01 10:00','2023-06-01 10:30', 1, 1, 1);
INSERT INTO hospital.Medications VALUES (1,'Ibuprofen', 'Advil', 'Pain reliever');
INSERT INTO hospital.Medications VALUES (2,'aspirin', 'Advil', 'Pain reliever');
INSERT INTO hospital.Prescribes VALUES(1,1,1,'2023-06-01',1);
INSERT INTO hospital.Block VALUES(1,1);
INSERT INTO hospital.Block VALUES(1,2);
INSERT INTO hospital.Rooms VALUES(101,'Single',1,1,1);
INSERT INTO hospital.Insurance VALUES(1, 'HMO', '2030-06-01');
INSERT INTO hospital.Undergoes VALUES('2023-06-01', 1, 1, 1);
INSERT INTO hospital.Insurance VALUES(2, 'HMO', '2022-06-01');


-- # 10 statements for updating
UPDATE hospital.Doctors SET email = 'jsmith@hospital.com' WHERE doctor_id = 2;
UPDATE hospital.Patients SET DOB = '1988-10-21' WHERE (patient_name = 'John Smith' AND patient_id > 0);
UPDATE hospital.Doctors_Departments SET department_id = 3 WHERE (doctor_id = 1 AND department_id = 1);
UPDATE hospital.Doctors SET specialization = 'pediatrics' WHERE doctor_id = 1;
UPDATE hospital.Procedures SET cost = cost * 1.1 WHERE (name = 'CT Scan' AND code > 0);
UPDATE hospital.Appointments SET doctor_id = 2 WHERE doctor_id = 1;
UPDATE hospital.Rooms SET capacity = 2 WHERE number = 101;
UPDATE hospital.Rooms SET type = 'Clinic' WHERE number = 101;
UPDATE hospital.Insurance SET expiration_date = '2030-05-01' WHERE insurance_id = 1;
UPDATE hospital.Undergoes SET date = '2023-07-01' WHERE patient_id = 1 and date = '2023-06-01';


-- # 10 statements for deleting
DELETE FROM hospital.Doctors WHERE name = 'Bob Kelsoe';
DELETE FROM hospital.Departments WHERE name = 'Pediatrics';
DELETE FROM hospital.Rooms WHERE number = 103;
DELETE FROM hospital.Patients WHERE (patient_name = 'John Smith' AND phone = '555-555-5552');
DELETE FROM hospital.Patients WHERE (patient_name = 'John Smith' AND phone = '555-555-5556');
DELETE FROM hospital.Appointments WHERE appointment_id = 3;
DELETE FROM hospital.Appointments WHERE appointment_id = 4;
DELETE FROM hospital.Medications WHERE name = 'aspirin';
DELETE FROM hospital.Insurance WHERE expiration_date < '2023-06-01';
DELETE FROM hospital.Undergoes WHERE date < '2023-06-01';


-- # 5 alter table.
ALTER TABLE hospital.Patients ADD COLUMN address VARCHAR(255) ;
ALTER TABLE hospital.Patients MODIFY COLUMN DOB DATE;
ALTER TABLE hospital.Rooms ADD COLUMN available BOOLEAN;
ALTER TABLE hospital.Insurance DROP COLUMN expiration_date;
ALTER TABLE hospital.Insurance ADD COLUMN expiration_date DATE AFTER Insurance_id;


-- # 1 big statement to join all tables in the database.
SELECT *
FROM hospital.Employees
JOIN hospital.Payrolls ON hospital.Employees.payroll_id = hospital.Payrolls.payroll_id
JOIN hospital.Doctors ON hospital.Employees.employee_id = hospital.Doctors.employee_id
JOIN hospital.Doctors_Departments ON hospital.Doctors.doctor_id = hospital.Doctors_Departments.doctor_id
JOIN hospital.Departments ON hospital.Doctors_Departments.department_id = hospital.Departments.department_id
JOIN hospital.Undergoes ON hospital.Doctors.doctor_id = hospital.Undergoes.doctor_id
JOIN hospital.Patients ON hospital.Undergoes.doctor_id = hospital.Patients.doctor_id
JOIN hospital.Insurance ON hospital.Patients.Insurance_id = hospital.Insurance.Insurance_id;


-- # 5 statements with left, right, inner, outer joins.
SELECT *
FROM hospital.Employees
LEFT JOIN Payrolls ON Employees.payroll_id = Payrolls.payroll_id;

SELECT *
FROM hospital.Employees
RIGHT JOIN Payrolls ON Employees.payroll_id = Payrolls.payroll_id;

SELECT *
FROM hospital.Employees
INNER JOIN Payrolls ON Employees.payroll_id = Payrolls.payroll_id;

SELECT *
FROM hospital.Employees
LEFT OUTER JOIN Payrolls ON Employees.payroll_id = Payrolls.payroll_id;

SELECT *
FROM hospital.Employees
RIGHT OUTER JOIN Payrolls ON Employees.payroll_id = Payrolls.payroll_id;


-- # 7 statements with aggregate functions and group by and without having.
SELECT COUNT(*) AS num_employees
FROM Employees;

SELECT AVG(DOB) AS average_birth_year
FROM Employees;

SELECT COUNT(*) AS num_appoitments
FROM Appointments;

SELECT medication_id, COUNT(*) AS medication_count
FROM Prescribes
GROUP BY medication_id;

SELECT doctor_id, COUNT(*) AS num_patients
FROM Patients
GROUP BY doctor_id;

SELECT procedures_code, COUNT(*) AS num_procedures_udergoes
FROM Undergoes
GROUP BY procedures_code;

SELECT department_id, COUNT(*) AS num_doctors
FROM Doctors_Departments
GROUP BY department_id;


-- # 7 statements with aggregate functions and group by and with having.
SELECT department_id, COUNT(*) AS num_doctors
FROM Doctors_Departments
GROUP BY department_id
HAVING num_doctors > 5;

SELECT code, MAX(cost) as max_cost
FROM Procedures
GROUP BY code
HAVING max_cost > 1000;

SELECT COUNT(*) AS num_appointments
FROM Appointments
HAVING COUNT(*) > 5;

SELECT patient_name, MIN(DOB) AS yougest
FROM Patients
GROUP BY patient_name;

SELECT medication_id, COUNT(*) AS medication_count
FROM Prescribes
GROUP BY medication_id
HAVING COUNT(*) > 20;

SELECT doctor_id, COUNT(*) AS num_patients
FROM Patients
GROUP BY doctor_id
HAVING COUNT(*) > 0;

SELECT procedures_code, COUNT(*) AS num_procedures_undergoes
FROM Undergoes
GROUP BY procedures_code
HAVING COUNT(*) > 10;


