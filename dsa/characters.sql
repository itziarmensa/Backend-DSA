create table characters
(
    characterId          varchar(50)  not null
        primary key,
    characterName        varchar(50)  null,
    characterDescription varchar(200) null,
    characterCoins       double       null,
    diceId               varchar(50)  null,
    constraint characters_ibfk_1
        foreign key (diceId) references dice (diceId)
);

create index diceId
    on characters (diceId);

