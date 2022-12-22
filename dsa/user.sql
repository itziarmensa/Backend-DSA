create table user
(
    userId      varchar(50) not null
        primary key,
    userName    varchar(50) null,
    userSurname varchar(50) null,
    userBirth   varchar(50) null,
    email       varchar(50) null,
    password    varchar(50) null,
    constraint email
        unique (email)
);

