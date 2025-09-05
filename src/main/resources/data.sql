INSERT INTO roles (id, name, description)
SELECT 1, 'ADMIN', 'Administrator with full access'
    WHERE NOT EXISTS (SELECT 1 FROM roles WHERE name = 'ADMIN');


INSERT INTO roles (id, name, description)
SELECT 2, 'USER', 'Normal user with limited access'
    WHERE NOT EXISTS (SELECT 1 FROM roles WHERE name = 'USER');

INSERT INTO account (username, email, password, logged_in_time, access_token, verified_time, role_id)
SELECT 'unhang', 'unhang@gmail.com', 'hashedPassword','2023-10-27 14:30:00', 'validAccessToken', '2023-10-27 14:30:00', 1
WHERE NOT EXISTS (SELECT 1 FROM account WHERE username = 'unhang');


INSERT INTO account (id, username, email, password, role_id, is_active)
SELECT '1c177819-54d2-4c27-b36c-7e80e6cac75c', 'hang1', 'unhang1@gmail.com', '$2a$12$jWJaG/Sz2X1UJCZvAhILduwnV.LzM31XZSKKeR51hwgPDBKPq3pGm',  1, true
    WHERE NOT EXISTS (SELECT 1 FROM account WHERE username = 'hang1');
