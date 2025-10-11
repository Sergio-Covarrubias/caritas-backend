alter table persons drop column age;
alter table if exists persons add column birth_date date not null;
alter table reservations alter COLUMN end_date drop not null;
alter table reservations drop column active;
alter table if exists reservations add column state varchar(255) not null check (state in ('PENDING','ACTIVE','INACTIVE'));