ALTER TABLE praktikumsstelle DROP CONSTRAINT fk_praktikumsstelle_mz;
ALTER TABLE praktikumsstelle DROP CONSTRAINT fk_praktikumsstelle_nwk;

ALTER TABLE nwk ALTER COLUMN id TYPE UUID USING id::uuid;
ALTER TABLE praktikumsstelle ALTER COLUMN id TYPE UUID USING id::uuid;
ALTER TABLE praktikumsstelle ALTER COLUMN meldezeitraumID TYPE UUID USING meldezeitraumID::uuid;
ALTER TABLE praktikumsstelle ALTER COLUMN assignedNwk TYPE UUID USING assignedNwk::uuid;
ALTER TABLE meldezeitraum ALTER COLUMN id TYPE UUID USING id::uuid;

ALTER TABLE praktikumsstelle ADD CONSTRAINT fk_praktikumsstelle_mz FOREIGN KEY (meldezeitraumID) REFERENCES meldezeitraum(id);
ALTER TABLE praktikumsstelle ADD CONSTRAINT fk_praktikumsstelle_nwk FOREIGN KEY (assignedNwk) REFERENCES nwk(id);