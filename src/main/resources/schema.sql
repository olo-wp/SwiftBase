CREATE TABLE banks(
    swift_code char(11) primary key ,
    address varchar(255),
    bank_name varchar(255) not null ,
    countryiso2 char(2) not null ,
    country_name varchar(255) not null ,
    is_headquater boolean not null
)