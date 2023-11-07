create table AUSBILDUNGSPRAKTIKUMSSTELLE
(
    id                      varchar(36)  not null,
    dienststelle            varchar(10) not null,
    oertlicheAusbilder       varchar(255) not null,
    email                   varchar(255) not null,
    taetigkeiten            varchar(5000) not null,
    dringlichkeit           varchar(10) not null,
    namentlicheAnforderung  varchar(255),
    referat                 varchar(3),
    projektarbeit           boolean not null,
    ausbildungsjahr         varchar(5) not null,
    ausbildungsrichtung     varchar(4) not null,
    primary key (id)
);

