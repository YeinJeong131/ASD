USE wiki;
INSERT INTO article(title, body, publish_date, lan) VALUES
('DummyData', 'This is dummyText yo yo \n dummy\n yoho\n\n\n\n\ndunno. lol', CURDATE(), 'ENGLISH');
INSERT INTO article_authors (article_id, author) VALUES (1, 'Nima');
INSERT INTO article_tags (article_id, tag) VALUES (1, 'Dummy');