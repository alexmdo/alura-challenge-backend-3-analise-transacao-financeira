create table authorities
(
    username  varchar(50) not null,
    authority varchar(50) not null,
    constraint fk_authorities_users foreign key (username) references users (username)
);

create unique index ix_auth_username on authorities (username, authority);

insert into users(username, password, enabled, name) values ('admin@email.com.br', '$2a$10$RJcLRcnuiustz2hfI6ObuOub9wllLZrwCFypL.6Ql5WQloLyovphq', true, 'Alexandre Oliveira');
insert into authorities values ('admin@email.com.br', 'ROLE_ADM');