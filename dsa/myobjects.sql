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

