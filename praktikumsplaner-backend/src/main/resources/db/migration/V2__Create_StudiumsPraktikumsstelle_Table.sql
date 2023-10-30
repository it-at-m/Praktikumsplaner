create table STUDIUMSPRAKTIKUMSSTELLE
(
    id                      varchar(36)  not null,
    dienststelle            varchar(10) not null,
    oertlicheAusbilder       varchar(255) not null,
    email                   varchar(255) not null,
    taetigkeiten            varchar(5000) not null,
    dringlichkeit           varchar(10) not null,
    namentlicheAnforderung  varchar(255),
    referat                 varchar(3),
    programmierkenntnisse   varchar(4) not null,
    studiensemester         varchar(9) not null,
    studienart              varchar(3) not null,
    primary key (id)
);

