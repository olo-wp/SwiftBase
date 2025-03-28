DROP TABLE IF EXISTS banks;

CREATE TABLE banks(
    swiftCode CHAR(11) PRIMARY KEY ,
    countryISO2 CHAR(2) NOT NULL ,
    address VARCHAR(255),
    bankName VARCHAR(255) NOT NULL ,
    countryName VARCHAR(255) NOT NULL ,
    isHeadquarter BOOLEAN NOT NULL
);