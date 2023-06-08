INSERT INTO hospital.Payrolls VALUES (1, 200000, 20000, 1);
INSERT INTO hospital.Payrolls VALUES (2, 250000, 25000, 2);
INSERT INTO hospital.Payrolls VALUES (3, 300000, 30000, 3);
INSERT INTO hospital.Payrolls VALUES (4, 100000, 10000, 4);

INSERT INTO hospital.Employees VALUES (1, 'John Doe', '1980-05-01', 1);
INSERT INTO hospital.Employees VALUES (2, 'Jane Smith', '1970-05-01', 2);
INSERT INTO hospital.Employees VALUES (3, 'Bob Kelso', '1975-05-01', 3);
INSERT INTO hospital.Employees VALUES (4, 'Dana', '1979-05-01', 4);
INSERT INTO hospital.Doctors VALUES(1, 'John Doe', 'Family medicine', 'jdoe@example.com', 1);
INSERT INTO hospital.Doctors VALUES (2, 'Jane Smith', 'Family medicine', 'jsmith@example.com', 2);
INSERT INTO hospital.Doctors VALUES (3, 'Bob Kelso', 'Internal medicine', 'bkelso@example.com', 3);
INSERT INTO hospital.Nurses VALUES (1, 'Dana', 'dana@example.com', 4);

INSERT INTO hospital.Insurance VALUES(1, 'HMO', '2030-06-01');
INSERT INTO hospital.Insurance VALUES(2, 'HMO', '2022-06-01');

INSERT INTO hospital.Patients VALUES(1,'John Smith','1988-10-01','555-555-5555', 1 , 1);
INSERT INTO hospital.Patients VALUES(2,'Jiwoo Choi','1988-10-21','555-555-5555', 2 , 2);

INSERT INTO hospital.Departments VALUES(1,'General Medicine',2);
INSERT INTO hospital.Departments VALUES(2,'Surgery',3);
INSERT INTO hospital.Departments VALUES(3,'Pediatrics',1);
INSERT INTO hospital.Doctors_Departments VALUES(1,1);
INSERT INTO hospital.Doctors_Departments VALUES(2,1);
INSERT INTO hospital.Doctors_Departments VALUES(3,2);
INSERT INTO hospital.Doctors_Departments VALUES(3,1);

INSERT INTO hospital.Procedures VALUES(1,'CT Scan',1500.0);

INSERT INTO hospital.Block VALUES(1,1);
INSERT INTO hospital.Block VALUES(1,2);
INSERT INTO hospital.Rooms VALUES(101,'Single',1,1,1);

INSERT INTO hospital.Appointments VALUES(1, 101,'2023-06-01 10:00','2023-06-01 10:30', 1, 1, 1);
INSERT INTO hospital.Medications VALUES (1,'Ibuprofen', 'Advil', 'Pain reliever');
INSERT INTO hospital.Medications VALUES (2,'aspirin', 'Advil', 'Pain reliever');
INSERT INTO hospital.Prescribes VALUES(1,1,1,'2023-06-01',1);

INSERT INTO hospital.Undergoes VALUES('2023-06-01', 1, 1, 1);
