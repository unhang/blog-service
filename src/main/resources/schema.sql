CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

DROP TABLE IF EXISTS roles CASCADE;
DROP TABLE IF EXISTS audit_logs CASCADE;
DROP TABLE IF EXISTS account CASCADE;

CREATE TABLE IF NOT EXISTS roles (
                                     id                   INTEGER PRIMARY KEY,
                                     name                 VARCHAR(50) UNIQUE NOT NULL,
    description          VARCHAR(255),
    created_at           TIMESTAMP DEFAULT now()
    );
--
-- ALTER TABLE account ADD CONSTRAINT fk_account_role
--     FOREIGN KEY (role_id) REFERENCES roles(id);

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
    role_id             INTEGER NOT NULL DEFAULT 2,
    created_time        TIMESTAMP NOT NULL DEFAULT now(),
    updated_time        TIMESTAMP NOT NULL DEFAULT now(),
    is_deleted          BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (role_id) REFERENCES roles(id)
);


-- Audit log table (optional but recommended for security)
CREATE TABLE IF NOT EXISTS audit_logs (
    id                  UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    account_id          UUID,
    action              VARCHAR(100) NOT NULL,
    ip_address          VARCHAR(45),
    user_agent          TEXT,
    created_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (account_id) REFERENCES account(id) ON DELETE SET NULL
);



