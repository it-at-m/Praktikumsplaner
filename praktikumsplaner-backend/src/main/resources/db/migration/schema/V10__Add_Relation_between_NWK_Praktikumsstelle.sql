alter table ausbildungspraktikumsstelle  add column assignedNWK varchar(36);
alter table ausbildungspraktikumsstelle add foreign key (assignedNWK) references NWK (id);
alter table ausbildungspraktikumsstelle add constraint uniqueNWKPerMeldezeitraumAPS unique (assignedNWK, meldezeitraumId);

alter table studiumspraktikumsstelle  add column assignedNWK varchar(36);
alter table studiumspraktikumsstelle add foreign key (assignedNWK) references NWK (id);
alter table studiumspraktikumsstelle add constraint uniqueNWKPerMeldezeitraumSPS unique (assignedNWK, meldezeitraumId);
