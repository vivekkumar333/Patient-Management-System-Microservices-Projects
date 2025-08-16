SELECT * FROM patient_schema.patient;

SELECT * FROM billing_schema.billing_account;

SELECT * FROM billing_schema.billing_transaction;

SELECT * FROM auth_schema.user;
INSERT INTO auth_schema.user values('1','amar@example.com','sdfdsfsdfdsf','PATIENT','ACTIVE',NOW());