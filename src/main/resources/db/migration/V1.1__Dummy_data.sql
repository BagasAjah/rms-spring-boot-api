INSERT INTO lookup(
            lookup_guid, lookup_code, lookup_type, lookup_value)
    VALUES 
('52fb1769-a18f-4dd1-84d5-bf7fd1ca70d6', 'M', 'gender', 'Male'),
('e822d482-f29d-4977-b735-8b46db83333b', 'F', 'gender', 'Female'),

('1c8d7c20-22cc-4de9-b8ff-c5266fc42445', 'S', 'statusMarital', 'Single'),
('e52c0fba-30b4-43a7-9791-198eb279cf1c', 'M', 'statusMarital', 'Married'),

('62c93ba6-e7d7-427e-8172-b91f45ccd351', 'C', 'status', 'Married'),
('971373cf-2cc1-4f85-87ef-f525818da82b', 'P', 'status', 'Permanent'),

('776591fb-313d-46f0-8e22-94b180345dc3', 'SE1', 'grade', 'SE - JP'),
('faa4ebea-0aad-42f7-92e6-509d1d3c8a36', 'SE2', 'grade', 'SE - PG'),
('9dcbea0d-bfdc-4c97-90d9-316655484966', 'SE3', 'grade', 'SE - AP'),
('09098895-860b-47f4-a225-5d53e62c6eb7', 'SE4', 'grade', 'SE - AN'),
('a082f51f-3c56-412f-9a56-85338353ac4c', 'SQ1', 'grade', 'SQ - JT'),
('c4093c7f-8fd5-4419-bdd9-48bf1f3d18d9', 'SQ2', 'grade', 'SQ - TS'),
('68a20ffb-7e4c-408c-b4b5-31a8d923cf44', 'SQ3', 'grade', 'SQ - ST'),
('da2ac162-62d3-43ce-a16f-bf19c547fb26', 'SM', 'grade', 'MJF - SM'),
('139ac73b-6ce5-42c0-9d55-e7f3d3276e2d', 'PM', 'grade', 'MJF - PM'),


('34f8af71-eb20-4c38-a3ef-5aa638567d2e', 'CDC', 'division', 'CDC'),
('175eb91d-8734-405e-85cf-e30613f672f6', 'CDC1', 'division', 'CDC Asterx'),
('309e48c3-6a41-45ee-8a39-d1640ba5a1c6', 'RED', 'division', 'SWD Red'),
('c6edf306-263e-4ef1-a3e0-2ca82937ca8c', 'BLUE', 'division', 'SWD Blue'),


('a215b65d-6fc8-49c9-bc04-305fcfefb760', 'H', 'familyType', 'Husband'),
('3adb30ed-b7a4-4c57-91ed-7ce5b64077b8', 'W', 'familyType', 'Wife'),
('feb62de0-49f2-4d28-aa10-cfbcf22d94bc', 'S', 'familyType', 'Son'),
('518a2b90-1de0-4243-994b-050b9624f4b4', 'D', 'familyType', 'Daughter'),


('dc10ef8c-7fb8-4abf-8711-a2637f1ad8fc', 'JOG', 'location', 'Jogja Office'),
('57b736a2-0cc6-49f6-bf24-83c12016d21b', 'BAL', 'location', 'Bali Office'),
('a7d8b16c-1570-4972-a545-3b6ec63b8e66', 'BAN', 'location', 'Bandung Office')
;


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
