create table User
(
    userId      varchar(50) not null
        primary key,
    userName    varchar(50) null,
    userSurname varchar(50) null,
    userBirth   varchar(50) null,
    coins       double      null,
    points      int         null,
    email       varchar(50) null,
    password    varchar(50) null,
    language    varchar(50) null,
    constraint email
        unique (email)
);

create table ObjectType
(
    objectTypeId          varchar(50) not null
        primary key,
    objectTypeDescription varchar(50) null
);

create table MyObjects
(
    objectId          varchar(50)  not null
        primary key,
    objectName        varchar(50)  null,
    objectDescription varchar(200) null,
    objectCoins       double       null,
    objectTypeId      varchar(50)  null,
    constraint MyObjects_ibfk_1
        foreign key (objectTypeId) references ObjectType (objectTypeId)
);

create index objectTypeId
    on MyObjects (objectTypeId);

create table UserMyObjects
(
    userId   varchar(50) null,
    objectId varchar(50) null,
    constraint UserMyObjects_ibfk_1
        foreign key (userId) references User (userId),
    constraint UserMyObjects_ibfk_2
        foreign key (objectId) references MyObjects (objectId)
);

create index objectId
    on UserMyObjects (objectId);

create index userId
    on UserMyObjects (userId);

create table Characters
(
    characterId          varchar(50)  not null
        primary key,
    characterName        varchar(50)  null,
    characterDescription varchar(200) null,
    characterCoins       double       null
);

create table UserCharacters
(
    userId      varchar(50) null,
    characterId varchar(50) null,
    constraint UserCharacters_ibfk_1
        foreign key (userId) references User (userId),
    constraint UserCharacters_ibfk_2
        foreign key (characterId) references Characters (characterId)
);

create index characterId
    on UserCharacters (characterId);

create index userId
    on UserCharacters (userId);

create table Partida
(
    partidaId   varchar(50) not null
        primary key,
    email       varchar(50) null,
    objectId    varchar(50) null,
    characterId varchar(50) null,
    mapa        int         null,
    level       int         null,
    points      int         null,
    finished    tinyint(1)  null
);

create table Faqs
(
    question varchar(200) null,
    answer   varchar(200) null
);

create table Issue
(
    informer varchar(50)  null,
    message  varchar(200) null,
    date     varchar(50)  null
);

create table Information
(
    date    varchar(50)  null,
    title   varchar(50)  null,
    message varchar(200) null,
    sender  varchar(50)  null
);