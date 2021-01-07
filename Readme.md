# The-News
## Description
 An application for querying and retrieving scoped news and information with news/articles/posts that are available to all employees.
## Author
 Murielle IRIBORI
## Setup instruction
* git clone
* Open through vscode or atom
* Make neccessary changes
* Run it
## Database setup
* CREATE DATABASE the_news;
* \c the_news_
* CREATE TABLE departments (id SERIAL PRIMARY KEY, name VARCHAR, description VARCHAR, employees int);
* CREATE TABLE generalnews ( id SERIAL PRIMARY KEY,title VARCHAR, content VARCHAR, userId int );
* CREATE TABLE departmentnews ( id SERIAL PRIMARY KEY,title VARCHAR, content VARCHAR, userId int, departmentId int );
* CREATE TABLE users ( id SERIAL PRIMARY KEY, name VARCHAR, role VARCHAR, position VARCHAR);
* CREATE TABLE users_departments ( id SERIAL PRIMARY KEY, userId int, departmentId int);
* CREATE DATABASE the_news_test WITH TEMPLATE the_news;
## Technologies Used
* Java
* markdown
* Handlebars
* psql
## Contact Information 
Email: [irimurielle@gmail.com]

Copyright (c) 2020 [MIT LICENSE](./License)