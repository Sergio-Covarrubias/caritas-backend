alter table if exists service_reservations add column order_date date not null;
alter table if exists service_reservations add column cost_count integer;
alter table if exists service_reservations add column state varchar(255) not null check (state in ('PENDING','CONFIRMED'));
