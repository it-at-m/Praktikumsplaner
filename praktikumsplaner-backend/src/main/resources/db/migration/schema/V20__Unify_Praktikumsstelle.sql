-- Create unified table PRAKTIKUMSSTELLE
create table PRAKTIKUMSSTELLE
(
    id                         varchar(36)  not null primary key,
    dienststelle               varchar(10)  not null,
    oertlicheAusbilder        varchar(255) not null,
    erwFuehrungszeugnisVorhanden boolean    not null default false,
    email                      varchar(255) not null,
    taetigkeiten               varchar(5000) not null,
    dringlichkeit              varchar(10)  not null,
    namentlicheAnforderung     varchar(255),
    meldezeitraumID            varchar(36)  not null,
    assignedNwk                varchar(36),
    planstelleVorhanden        boolean      not null default false,
    wuensche                   varchar(5000),

    -- Unified Richtung
    richtung                   varchar(10)  not null,

    -- STUDIUM-only
    programmierkenntnisse      varchar(255),
    studiensemester            varchar(255),

    -- AUSBILDUNG-only
    projektarbeit              boolean      not null default false,
    ausbildungsjahr            varchar(255),
    minderjaehrigMoeglich      boolean
);

-- Add constraints
alter table PRAKTIKUMSSTELLE add constraint FK_PRAKTIKUMSSTELLE_MZ foreign key (meldezeitraumID) references MELDEZEITRAUM(id);
alter table PRAKTIKUMSSTELLE add constraint FK_PRAKTIKUMSSTELLE_NWK foreign key (assignedNwk) references NWK(id);

-- Data migration from old tables
insert into PRAKTIKUMSSTELLE (id, dienststelle, oertlicheAusbilder, erwFuehrungszeugnisVorhanden, email, taetigkeiten, dringlichkeit,
                               namentlicheAnforderung, meldezeitraumID, assignedNwk, planstelleVorhanden, wuensche,
                               richtung, programmierkenntnisse, studiensemester, projektarbeit, ausbildungsjahr, minderjaehrigMoeglich)
select s.id,
       s.dienststelle,
       s.oertlicheAusbilder,
       s.erwFuehrungszeugnisVorhanden,
       s.email,
       s.taetigkeiten,
       s.dringlichkeit,
       s.namentlicheAnforderung,
       s.meldezeitraumID,
       s.assignedNwk,
       s.planstelleVorhanden,
       s.wuensche,
       s.studiengang as richtung,
       s.programmierkenntnisse,
       s.studiensemester,
       null as projektarbeit,
       null as ausbildungsjahr,
       null as minderjaehrigMoeglich
from STUDIUMSPRAKTIKUMSSTELLE s;

insert into PRAKTIKUMSSTELLE (id, dienststelle, oertlicheAusbilder, erwFuehrungszeugnisVorhanden, email, taetigkeiten, dringlichkeit,
                               namentlicheAnforderung, meldezeitraumID, assignedNwk, planstelleVorhanden, wuensche,
                               richtung, programmierkenntnisse, studiensemester, projektarbeit, ausbildungsjahr, minderjaehrigMoeglich)
select a.id,
       a.dienststelle,
       a.oertlicheAusbilder,
       a.erwFuehrungszeugnisVorhanden,
       a.email,
       a.taetigkeiten,
       a.dringlichkeit,
       a.namentlicheAnforderung,
       a.meldezeitraumID,
       a.assignedNwk,
       a.planstelleVorhanden,
       a.wuensche,
       a.ausbildungsrichtung as richtung,
       a.programmierkenntnisse,
       null as studiensemester,
       a.projektarbeit,
       a.ausbildungsjahr,
       a.minderjaehrigMoeglich
from AUSBILDUNGSPRAKTIKUMSSTELLE a;

-- Drop old tables
drop table STUDIUMSPRAKTIKUMSSTELLE;
drop table AUSBILDUNGSPRAKTIKUMSSTELLE;
