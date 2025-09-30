<<<<<<< HEAD
create database if not exists wiki character set utf8mb4 collate utf8mb4_unicode_ci;
=======
drop table if exists article;
>>>>>>> nima_Featuress

use wiki;

create table if not exists article (
                                       id bigint auto_increment primary key,
                                       title varchar(255) not null,
                                       body longtext,
                                       publish_date date,
                                       author json default (json_array()),
                                       tags json default (json_array())
);
<<<<<<< HEAD
=======
use wiki;
>>>>>>> nima_Featuress
