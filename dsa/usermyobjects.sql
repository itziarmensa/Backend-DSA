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

