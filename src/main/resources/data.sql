# USE wiki;

-- Reset dev data (children first)
SET FOREIGN_KEY_CHECKS=0;
TRUNCATE TABLE user_roles;
TRUNCATE TABLE article_authors;
TRUNCATE TABLE article_tags;
TRUNCATE TABLE users;
TRUNCATE TABLE roles;
TRUNCATE TABLE article;
SET FOREIGN_KEY_CHECKS=1;

-- ========= ROLES =========
INSERT INTO roles (name) VALUES ('ROLE_USER')
ON DUPLICATE KEY UPDATE name = name;

INSERT INTO roles (name) VALUES ('ROLE_ADMIN')
ON DUPLICATE KEY UPDATE name = name;

-- ========= USERS =========
-- Default user
INSERT INTO users (email, password, enabled)
VALUES ('user1@example.com','user123',TRUE);

-- Default admin
INSERT INTO users (email, password, enabled)
VALUES ('admin@betterpedia.local','admin123',TRUE);

-- ========= USER ↔ ROLE LINKS =========
-- user1 → ROLE_USER
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u
         JOIN roles r ON r.name = 'ROLE_USER'
WHERE u.email = 'user1@example.com';

-- admin → ROLE_ADMIN
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u
         JOIN roles r ON r.name = 'ROLE_ADMIN'
WHERE u.email = 'admin@betterpedia.local';

-- admin → ROLE_USER (optional convenience)
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u
         JOIN roles r ON r.name = 'ROLE_USER'
WHERE u.email = 'admin@betterpedia.local';

-- ========= ARTICLES (sample) =========
-- If your schema for `article` changes, feel free to comment this block out.
INSERT INTO article (title, body, publish_date, lan)
VALUES ('DummyData',
        'This is dummyText yo yo \n dummy\n yoho\n\n\n\n\ndunno. lol',
        CURDATE(),
        'ENGLISH');

-- Link sample authors/tags to the article we just inserted (id = 1 after TRUNCATE)
INSERT INTO article_authors (article_id, author) VALUES (1, 'Nima');
INSERT INTO article_tags (article_id, tag)       VALUES (1, 'Dummy');
