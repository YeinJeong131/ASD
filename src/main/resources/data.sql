# USE wiki;

SET FOREIGN_KEY_CHECKS=0;
TRUNCATE TABLE user_roles;
TRUNCATE TABLE article_authors;
TRUNCATE TABLE article_tags;
TRUNCATE TABLE users;
TRUNCATE TABLE roles;
TRUNCATE TABLE article;
SET FOREIGN_KEY_CHECKS=1;

-- Articles (nimahs?)
INSERT INTO article(title, body, publish_date, lan) VALUES
    ('DummyData', 'This is dummyText yo yo \n dummy\n yoho\n\n\n\n\ndunno. lol', CURDATE(), 'ENGLISH');
INSERT INTO article_authors (article_id, author) VALUES (1, 'Nima');
INSERT INTO article_tags (article_id, tag) VALUES (1, 'Dummy');

--  seed a default test user
INSERT INTO users (email, password, enabled)
VALUES ('user1@example.com','user123',TRUE);

# make sure required roles exist
INSERT INTO roles (name) VALUES ('ROLE_USER')
ON DUPLICATE KEY UPDATE name = name;

INSERT INTO roles (name) VALUES ('ROLE_ADMIN')
ON DUPLICATE KEY UPDATE name = name;

# link user to role_user
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u
         JOIN roles r ON r.name = 'ROLE_USER'
WHERE u.email = 'user1@example.com';

# Create default admin user
INSERT INTO users (email, password, enabled)
VALUES ('admin@betterpedia.local','admin123',TRUE);

# give admin admin role
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u
         JOIN roles r ON r.name = 'ROLE_ADMIN'
WHERE u.email = 'admin@betterpedia.local';


# give admin user role
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u
         JOIN roles r ON r.name = 'ROLE_USER'
WHERE u.email = 'admin@betterpedia.local';
