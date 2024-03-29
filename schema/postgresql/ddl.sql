-- drop
DROP TABLE REFROG.COMPANY;
DROP SEQUENCE REFROG.COMPANY_SEQUENCE;

-- create
-- sequence
CREATE SEQUENCE REFROG.COMPANY_SEQUENCE START 1 MAXVALUE 999999999999999999 CYCLE;

-- table
CREATE TABLE REFROG.COMPANY(
SEQ NUMERIC(18) NOT NULL PRIMARY KEY DEFAULT nextval('REFROG.COMPANY_SEQUENCE'),
UID VARCHAR(8) NOT NULL,
NAME VARCHAR(200),
ADDRESS VARCHAR(500),
PHONE VARCHAR(20),
EMAIL VARCHAR(100),
DEL_STATUS VARCHAR(1) DEFAULT 'N',
CREATE_TIME TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
UPDATE_TIME TIMESTAMP
);
