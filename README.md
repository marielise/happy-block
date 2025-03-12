# happy-block

## db installation: 

* Install postgresql
Cf: brew

### Initialize the db:
```
create database happy_db;
create user happy_s with password 'h@pp1_d8';
grant all PRIVILEGES ON DATABASE happy_db to happy_s;
create user happy_w with password 'h@pp1_d8_w';
```
