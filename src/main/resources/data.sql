USE wiki;
insert into article(title, body, publish_date, authors, tags, lan) values
('DummyData',
 'This is dummyText yo yo \n dummy\n yoho\n\n\n\n\ndunno. lol',
 CURDATE(),
 JSON_ARRAY('Nima'),
 JSON_ARRAY('Dummy'),
 'ENGLISH'
);