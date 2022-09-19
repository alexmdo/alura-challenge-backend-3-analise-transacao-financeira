create table users
(
    username varchar(50) not null primary key,
    password varchar(100) not null,
    enabled  boolean not null
);

create table authorities
(
    username  varchar(50) not null,
    authority varchar(50) not null,
    constraint fk_authorities_users foreign key (username) references users (username)
);

create unique index ix_auth_username on authorities (username, authority);

insert into users values ('admin@email.com.br', '$2a$10$es2i3xEUQJEEhuY6cOzw1u3OHEJL/oMzj4CEKacYSTQ7qvt5U1Hhq', true);
insert into authorities values ('admin@email.com.br', 'ROLE_ADM');