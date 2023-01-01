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

