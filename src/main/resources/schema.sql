CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS  account (
    id uuid             PRIMARY KEY DEFAULT uuid_generate_v4(),
    username            VARCHAR (50) UNIQUE NOT NULL,
    email               VARCHAR (255) UNIQUE NOT NULL,
    password            VARCHAR (255) NOT NULL,
    is_active           BOOLEAN NOT NULL DEFAULT TRUE,
    is_verified         BOOLEAN NOT NULL DEFAULT FALSE,
    is_test             BOOLEAN NOT NULL DEFAULT FALSE,
    logged_in_time      TIMESTAMP,
    access_token        TEXT,
    verified_time       TIMESTAMP,
    role_id             INTEGER NOT NULL,
    created_time        TIMESTAMP NOT NULL DEFAULT now(),
    updated_time        TIMESTAMP NOT NULL DEFAULT now(),
    is_deleted          BOOLEAN NOT NULL DEFAULT FALSE
);