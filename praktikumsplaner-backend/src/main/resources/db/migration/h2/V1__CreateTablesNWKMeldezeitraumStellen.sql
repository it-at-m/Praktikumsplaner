create table nwk
(
    id                  varchar(36)          not null
        primary key,
    vorname             varchar(255)         not null,
    nachname            varchar(255)         not null,
    studiengang         varchar(3),
    jahrgang            varchar(5)           not null,
    vorlesungstage      varchar(255),
    active              boolean default true not null,
    ausbildungsrichtung varchar(4),
    constraint check_studiengang_ausbildungsrichtung
        check (((studiengang IS NOT NULL) AND (ausbildungsrichtung IS NULL)) OR
               ((studiengang IS NULL) AND (ausbildungsrichtung IS NOT NULL)))
);

create table meldezeitraum
(
    id             varchar(36)  not null
        primary key,
    zeitraumname   varchar(255) not null,
    startzeitpunkt timestamp(6) not null,
    endzeitpunkt   timestamp(6) not null
);

create table ausbildungspraktikumsstelle
(
    id                     varchar(36)           not null
        primary key,
    dienststelle           varchar(10)           not null,
    oertlicheausbilder     varchar(255)          not null,
    email                  varchar(255)          not null,
    taetigkeiten           varchar(5000)         not null,
    dringlichkeit          varchar(10)           not null,
    namentlicheanforderung varchar(255),
    referat                varchar(3),
    projektarbeit          boolean               not null,
    ausbildungsjahr        varchar(255)          not null,
    ausbildungsrichtung    varchar(4)            not null,
    meldezeitraumid        varchar(36)           not null
        constraint ausbildungspraktikumsstelle_meldezeitraum_fkey_cascade
            references meldezeitraum
            on delete cascade,
    planstellevorhanden    boolean default false not null,
    assignednwk            varchar(36)
        references nwk,
    programmierkenntnisse  varchar(5),
    constraint uniquenwkpermeldezeitraumaps
        unique (assignednwk, meldezeitraumid)
);

create table studiumspraktikumsstelle
(
    id                     varchar(36)           not null
        primary key,
    dienststelle           varchar(10)           not null,
    oertlicheausbilder     varchar(255)          not null,
    email                  varchar(255)          not null,
    taetigkeiten           varchar(5000)         not null,
    dringlichkeit          varchar(10)           not null,
    namentlicheanforderung varchar(255),
    referat                varchar(3),
    programmierkenntnisse  varchar(5)            not null,
    studiensemester        varchar(255)          not null,
    studiengang            varchar(3)            not null,
    meldezeitraumid        varchar(36)           not null
        constraint studiumspraktikumsstelle_meldezeitraum_fkey_cascade
            references meldezeitraum
            on delete cascade,
    planstellevorhanden    boolean default false not null,
    assignednwk            varchar(36)
        references nwk,
    constraint uniquenwkpermeldezeitraumsps
        unique (assignednwk, meldezeitraumid)
);