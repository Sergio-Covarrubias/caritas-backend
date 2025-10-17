alter table reservations drop constraint reservations_state_check;
alter table reservations add constraint reservations_state_check check (state in ('PENDING', 'ACTIVE', 'INACTIVE', 'CANCELLED'));
alter table if exists hostels add column price float4 not null;
