DROP TABLE IF EXISTS banks;

CREATE TABLE banks (
    swift_code CHAR(11) PRIMARY KEY,
    ISO2 CHAR(2) NOT NULL ,
    bank_name VARCHAR(255) NOT NULL ,
    country VARCHAR(255) NOT NULL ,
    town VARCHAR(255) NOT NULL ,
    is_hq BOOLEAN NOT NULL
)