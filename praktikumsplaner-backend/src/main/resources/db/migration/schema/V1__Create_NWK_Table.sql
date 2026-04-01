create table NWK
(
    id             varchar(36)  not null,
    vorname        varchar(255) not null,
    nachname       varchar(255) not null,
    studiengang    varchar(4),
    jahrgang       char(5),
    vorlesungstage varchar(27),
    primary key (id)
);

