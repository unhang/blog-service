INSERT INTO account (username, email, password, logged_in_time, access_token, verified_time, role_id)
SELECT 'unhang', 'unhang@gmail.com', 'hashedPassword','2023-10-27 14:30:00', 'validAccessToken', '2023-10-27 14:30:00', 1
WHERE NOT EXISTS (SELECT 1 FROM account WHERE username = 'unhang');