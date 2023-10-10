create table Meldezeitraum (
                                id varchar(36) not null,
                                zeitraumname varchar(255) not null,
                                startzeitpunkt timestamp(6),
                                endzeitpunkt timestamp(6),
                                primary key (id)
);

create table TheEntity (
    id varchar(36) not null,
    textAttribute varchar(8),
    primary key (id)
);