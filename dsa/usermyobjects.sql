create table usermyobjects
(
    userId   varchar(50) null,
    objectId varchar(50) null,
    constraint usermyobjects_ibfk_1
        foreign key (userId) references user (userId),
    constraint usermyobjects_ibfk_2
        foreign key (objectId) references myobjects (objectId)
);

create index objectId
    on usermyobjects (objectId);

create index userId
    on usermyobjects (userId);

