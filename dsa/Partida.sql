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

