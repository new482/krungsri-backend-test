CREATE TABLE person (
    id UUID NOT NULL PRIMARY KEY,
    username VARCHAR(64) NOT NULL,
    password VARCHAR(64) NOT NULL,
    phone_no VARCHAR(64) NOT NULL,
    first_name VARCHAR(64) NOT NULL,
    last_name VARCHAR(64) NOT NULL,
    address TEXT NOT NULL,
    salary BIGINT NOT NULL,
    ref_code VARCHAR(16) NOT NULL
);