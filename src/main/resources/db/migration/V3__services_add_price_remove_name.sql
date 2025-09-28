alter table services drop column display_name;
alter table if exists services add column price float4 not null;
