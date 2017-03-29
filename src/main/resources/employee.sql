INSERT INTO employee(
            employee_guid, division, dob, email, first_name, gender, grade, 
            hire_date, last_name, marital_status, nationality, office, phone, 
            status, sub_division, suspend_date)
    VALUES ('85a86845-4f78-49a2-a915-17292802b9c5', 'CDC1', '1990-10-07 13:17:17', 'Bagas.Dimas@mitrais.com', 'Bagas', 'M', 'SE2', '2013-10-18 13:17:17', 'Dimas', 'M',
     'Indonesia', 'JOG', '1234567890', 'P', 'Java Bootcamp', null);

INSERT INTO employee_family(
            employee_family_guid, employee_guid, family_dob, family_gender, 
            family_name, family_type, is_active, record_statusid)
    VALUES ('f91ec1c5-46eb-4eba-ba78-ba31bd3faf9f', '85a86845-4f78-49a2-a915-17292802b9c5', '1985-10-07 13:17:17', 'F', 
            'Susana Ngatinah', 'W', true, 1);

INSERT INTO employee_grade(
            employee_grade_guid, ds, employee_grade, employee_guid, end_date, 
            start_date, record_statusid)
    VALUES ('084d4c2b-76dd-4679-99cc-0a80ced7f996', '4', 'SE2', '85a86845-4f78-49a2-a915-17292802b9c5', null, 
            '2013-10-18 13:17:17', 1);

INSERT INTO employee_history(
            employee_history_guid, company, employee_guid, history_endt_date, 
            history_start_date, "position", record_statusid)
    VALUES ('d44b9060-d047-4155-9fb6-cacece845e99', 'PT Abal - Abal', '85a86845-4f78-49a2-a915-17292802b9c5', '2013-10-18 13:17:17', 
            '2015-10-18 13:17:17', 'Kang Sapu', 1);

INSERT INTO job_desc_list(
            jeb_desc_guid, employee_history_guid, jeb_desc_name, record_statusid)
    VALUES ('bbc6d286-1d53-494b-8e28-11806dbd8912', 'd44b9060-d047-4155-9fb6-cacece845e99', 'Nyapu', 1),
		('a402d4ae-5857-4ff9-9254-f68e09d6f9c3', 'd44b9060-d047-4155-9fb6-cacece845e99', 'Ngepel', 1);


INSERT INTO employee_location(
            employee_location_guid, employee_guid, office_address, office_end_date, 
            office_location, office_start_date, record_statusid)
    VALUES ('b9e411e4-c72b-4bb2-921c-57d53a98b96e', '85a86845-4f78-49a2-a915-17292802b9c5', 'Test', '2015-10-18 13:17:17', 
            'JOG', '2013-10-18 13:17:17', 1);
