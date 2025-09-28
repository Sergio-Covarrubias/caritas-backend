alter table if exists hostels add column image_urls TEXT not null;
alter table if exists hostels add column location_url varchar(255) not null;
alter table if exists hostels add column max_capacity integer not null;
