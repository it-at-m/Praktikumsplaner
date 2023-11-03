create table MELDEZEITRAUM
(
    id             varchar(36)  not null,
    zeitraumName   varchar(255) not null,
    startzeitpunkt timestamp(6) not null,
    endZeitpunkt   timestamp(6) not null,
    primary key (id)
);

