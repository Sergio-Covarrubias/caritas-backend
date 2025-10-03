create table person_reservations (id uuid not null, person_id uuid not null, reservation_id uuid not null, primary key (id));
create table persons (id uuid not null, age integer not null, alergies TEXT not null, discapacities TEXT not null, first_name varchar(255) not null, last_name varchar(255) not null, medicines TEXT not null, user_id varchar(255) not null, primary key (id));
alter table if exists person_reservations add constraint FKs6avqgjihj2r5pwkwthhgctil foreign key (person_id) references persons;
alter table if exists person_reservations add constraint FKra32kxnvpydb85xhy6trur0sv foreign key (reservation_id) references reservations;
alter table if exists persons add constraint FKrp309masjisdm7mmqon63obpv foreign key (user_id) references users;
alter table reservations drop column people_count;
