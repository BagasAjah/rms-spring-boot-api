DROP TABLE IF EXISTS job_desc_list CASCADE
;

DROP TABLE IF EXISTS employee_history CASCADE
;

DROP TABLE IF EXISTS employee_location CASCADE
;

DROP TABLE IF EXISTS employee_grade CASCADE
;

DROP TABLE IF EXISTS employee_family CASCADE
;

DROP TABLE IF EXISTS employee CASCADE
;

DROP TABLE IF EXISTS lookup CASCADE
;

CREATE TABLE lookup
(
  lookup_guid character varying(36) NOT NULL,
  lookup_code character varying(36),
  lookup_type character varying(36),
  lookup_value character varying(36),
  CONSTRAINT lookup_pkey PRIMARY KEY (lookup_guid)
);

CREATE TABLE employee
(
  employee_guid character varying(36) NOT NULL,
  avatar oid,
  division character varying(10),
  dob timestamp without time zone NOT NULL,
  email character varying(40),
  first_name character varying(40) NOT NULL,
  gender character varying(1) NOT NULL,
  grade character varying(4),
  hire_date timestamp without time zone NOT NULL,
  last_name character varying(40),
  marital_status character varying(1),
  nationality character varying(20),
  office character varying(20),
  phone character varying(15),
  status character varying(1),
  sub_division character varying(40),
  suspend_date timestamp without time zone,
  CONSTRAINT employee_pkey PRIMARY KEY (employee_guid)
);

CREATE TABLE employee_family
(
  employee_family_guid character varying(36) NOT NULL,
  record_statusid integer NOT NULL,
  employee_guid character varying(36) NOT NULL,
  family_dob timestamp without time zone,
  family_gender character varying(1),
  family_name character varying(40),
  family_type character varying(1),
  is_active boolean,
  CONSTRAINT employee_family_pkey PRIMARY KEY (employee_family_guid),
  CONSTRAINT fknhkppxn1vlhxyplki5klpiiks FOREIGN KEY (employee_guid)
      REFERENCES employee (employee_guid) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE employee_grade
(
  employee_grade_guid character varying(36) NOT NULL,
  record_statusid integer NOT NULL,
  ds character varying(2),
  employee_guid character varying(36) NOT NULL,
  end_date timestamp without time zone,
  employee_grade character varying(4),
  start_date timestamp without time zone,
  CONSTRAINT employee_grade_pkey PRIMARY KEY (employee_grade_guid),
  CONSTRAINT fk9fv3j2205ggo7rpdclmyi1off FOREIGN KEY (employee_guid)
      REFERENCES employee (employee_guid) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE employee_location
(
  employee_location_guid character varying(36) NOT NULL,
  record_statusid integer NOT NULL,
  employee_guid character varying(36) NOT NULL,
  office_address character varying(255),
  office_end_date timestamp without time zone,
  office_location character varying(40),
  office_start_date timestamp without time zone,
  CONSTRAINT employee_location_pkey PRIMARY KEY (employee_location_guid),
  CONSTRAINT fk5yxdyncwcal1mfkep29gs83bc FOREIGN KEY (employee_guid)
      REFERENCES employee (employee_guid) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE employee_history
(
  employee_history_guid character varying(36) NOT NULL,
  record_statusid integer NOT NULL,
  company character varying(20),
  employee_guid character varying(36) NOT NULL,
  history_endt_date timestamp without time zone,
  history_start_date timestamp without time zone,
  "position" character varying(20),
  CONSTRAINT employee_history_pkey PRIMARY KEY (employee_history_guid),
  CONSTRAINT fkehh79hmcs0eky527df2oh98an FOREIGN KEY (employee_guid)
      REFERENCES employee (employee_guid) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE job_desc_list
(
  jeb_desc_guid character varying(36) NOT NULL,
  record_statusid integer NOT NULL,
  employee_history_guid character varying(36) NOT NULL,
  jeb_desc_name character varying(255),
  CONSTRAINT job_desc_list_pkey PRIMARY KEY (jeb_desc_guid),
  CONSTRAINT fk2w7gvxgvxrxwqnvqw1kmb1y1t FOREIGN KEY (employee_history_guid)
      REFERENCES employee_history (employee_history_guid) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

