create table characters
(
    characterId          varchar(50)  not null
        primary key,
    characterName        varchar(50)  null,
    characterDescription varchar(200) null,
    characterCoins       double       null
);

