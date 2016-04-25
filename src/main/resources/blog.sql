create database blog;
use blog;

create table users
(
firstName varchar(30) not null,
lastName varchar(30) not null,
email varchar(40) not null unique,
phoneNumber BIGINT not null unique,
password varchar(30) not null,
userId int primary key
);

create table blogs
(
blogId int primary key,
title varchar(200) not null,
description varchar(2000) not null,
users_userId int references users
);

create table comments
(
commentId int primary key,
comment varchar(1000) not null,
blogs_blogId int references blogs
);

alter table comments
add column users_userId int references users;