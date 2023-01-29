DROP TABLE Information;
DROP TABLE Issue;
DROP TABLE Faqs;
DROP TABLE Partida;
DROP TABLE UserCharacters;
DROP TABLE Characters;
DROP TABLE UserMyObjects;
DROP TABLE MyObjects;
DROP TABLE ObjectType;
DROP TABLE User;

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

INSERT INTO ObjectType VALUES ("1","Object Type 1");
INSERT INTO ObjectType VALUES ("2","Object Type 2");
INSERT INTO ObjectType VALUES ("3","Object Type 3");

INSERT INTO MyObjects VALUES ("1","Espada", "Espada con poderes", 3.1,"1");
INSERT INTO MyObjects VALUES ("2","Anillo", "Anillo teletransportador", 2.7, "2");
INSERT INTO MyObjects VALUES ("3","Traje", "Traje invisible", 4.5, "3");
INSERT INTO MyObjects VALUES ("4","Gafas", "Gafas visión del futuro", 5.25,"2");
INSERT INTO MyObjects VALUES ("5","Pistola", "Pistola laser", 1.35, "2");
INSERT INTO MyObjects VALUES ("6","Capa", "Capa voladora", 5, "1");

INSERT INTO Characters VALUES ("1","Red Hat Boy", "Character 1", 10);
INSERT INTO Characters VALUES ("2","The Boy", "Character 2", 15);
INSERT INTO Characters VALUES ("3","Cute Girl", "Character 3", 20);
INSERT INTO Characters VALUES ("4","Dino", "Character 4", 25);
INSERT INTO Characters VALUES ("5","Santa Claus", "Character 5", 30);
INSERT INTO Characters VALUES ("6","The Zombie", "Character 6", 35);
INSERT INTO Characters VALUES ("7","Jack O'Lantern", "Character 7", 40);
INSERT INTO Characters VALUES ("8","The Knight", "Character 8", 45);
INSERT INTO Characters VALUES ("9","Cat", "Character 9", 50);
INSERT INTO Characters VALUES ("10","Dog", "Character 10", 55);
INSERT INTO Characters VALUES ("11","The Robot", "Character 11", 60);
INSERT INTO Characters VALUES ("12","Ninja Boy", "Character 12", 65);
INSERT INTO Characters VALUES ("13","Ninja Girl", "Character 13", 70);
INSERT INTO Characters VALUES ("14","Adventure Girl", "Character 14", 75);
INSERT INTO Characters VALUES ("15","Adventure Boy", "Character 15", 80);

INSERT INTO Faqs VALUES ("¿Cómo se pasa el minijuego Empareja y Despeja?","Juntando parejas en horizontal, vertical o diagonal hasta que no queden cartas");
INSERT INTO Faqs VALUES ("¿Tiene el juego alguna clase de puntuación?","Todavía no");
INSERT INTO Faqs VALUES ("¿Cuántos minijuegos hay?","En total hay 10 minijuegos");
INSERT INTO Faqs VALUES ("¿Cuántos personajes hay?","En total hay 15 personajes");
INSERT INTO Faqs VALUES ("¿Cuántos minijuegos hay en cada mapa?","Hay 5 minijuegos por mapa");
INSERT INTO Faqs VALUES ("¿Habrá nuevos personajes?","De momento, no se ha planeado ampliar el número de personajes, pero podrían crearse más en futuras actualizaciones");
INSERT INTO Faqs VALUES ("¿Habrá nuevos objetos?","De momento, no se ha planeado ampliar el número de objetos, pero podrían crearse más en futuras actualizaciones");
INSERT INTO Faqs VALUES ("¿Quién ha creado el juego?","El juego está creado por 4 estudiantes de DSA de la EETAC: Anna, Itziar, Lluc, Óscar y Pau");
INSERT INTO Faqs VALUES ("¿Cómo se pueden obtener monedas?","Las monedas se obtienen jugando y ganando en los distintos minijuegos");