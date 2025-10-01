# USE wiki;

SET FOREIGN_KEY_CHECKS=0;
TRUNCATE TABLE user_roles;
TRUNCATE TABLE article_authors;
TRUNCATE TABLE article_tags;
TRUNCATE TABLE users;
TRUNCATE TABLE roles;
TRUNCATE TABLE article;
SET FOREIGN_KEY_CHECKS=1;

-- Articles (keep)
INSERT INTO article(title, body, publish_date, lan) VALUES
    ('DummyData', 'This is dummyText yo yo \n dummy\n yoho\n\n\n\n\ndunno. lol', CURDATE(), 'ENGLISH');
INSERT INTO article_authors (article_id, author) VALUES (1, 'Nima');
INSERT INTO article_tags (article_id, tag) VALUES (1, 'Dummy');

-- F101: seed a test user (no display_name)
INSERT INTO users (email, password, enabled)
VALUES ('user1@example.com','changeme',TRUE);

-- Ensure roles exist
INSERT INTO roles (name) VALUES ('ROLE_USER')
ON DUPLICATE KEY UPDATE name = name;

INSERT INTO roles (name) VALUES ('ROLE_ADMIN')
ON DUPLICATE KEY UPDATE name = name;

-- Link test user to ROLE_USER
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u
         JOIN roles r ON r.name = 'ROLE_USER'
WHERE u.email = 'user1@example.com';

-- ===== Admin seed =====
-- Create admin user (plain text password for current dev setup)
INSERT INTO users (email, password, enabled)
VALUES ('admin@betterpedia.local','admin123',TRUE);

-- Link admin to ROLE_ADMIN
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u
         JOIN roles r ON r.name = 'ROLE_ADMIN'
WHERE u.email = 'admin@betterpedia.local';

-- Optionally also give admin the ROLE_USER
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u
         JOIN roles r ON r.name = 'ROLE_USER'
WHERE u.email = 'admin@betterpedia.local';
