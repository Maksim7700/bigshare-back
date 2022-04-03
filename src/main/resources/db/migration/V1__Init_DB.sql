create table role
(
    role_id bigint not null auto_increment,
    role    varchar(255),
    primary key (role_id)
);

create table user
(
    user_id    bigint not null auto_increment,
    avatar_url varchar(255),
    created    datetime,
    email      varchar(255),
    first_name varchar(255),
    last_name  varchar(255),
    modified   datetime,
    password   varchar(255),
    username   varchar(255),
    primary key (user_id)
);

create table user_role
(
    user_id bigint not null,
    role_id bigint not null,
    primary key (user_id, role_id)
);

alter table user_role
    add constraint user_role_fk foreign key (role_id) references role (role_id);
alter table user_role
    add constraint user_user_fk foreign key (user_id) references user (user_id);

alter table user add constraint us_name unique (username)