alter table person add column enabled bit(1) not null default b'1' after last_name;