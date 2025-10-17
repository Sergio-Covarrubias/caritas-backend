create table bath_reservations (id uuid not null, count integer not null, order_date date not null, primary key (id));
create table breakfast_reservations (id uuid not null, count integer not null, order_date date not null, primary key (id));
create table dental_reservations (id uuid not null, count integer not null, order_date date not null, primary key (id));
create table dinner_reservations (id uuid not null, count integer not null, order_date date not null, primary key (id));
create table document_reservations (id uuid not null, count integer not null, order_date date not null, primary key (id));
create table laundry_reservations (id uuid not null, count integer not null, order_date date not null, primary key (id));
create table meal_reservations (id uuid not null, count integer not null, order_date date not null, primary key (id));
create table mental_reservations (id uuid not null, count integer not null, order_date date not null, primary key (id));
create table transportation_reservations (id uuid not null, count integer not null, from_hostel boolean not null, hostel_name varchar(255) not null, order_date date not null, pickup_time time(6) not null, place varchar(255) not null, primary key (id));
