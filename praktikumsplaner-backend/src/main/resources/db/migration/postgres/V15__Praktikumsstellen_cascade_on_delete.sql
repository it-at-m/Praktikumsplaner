ALTER TABLE studiumspraktikumsstelle DROP CONSTRAINT studiumspraktikumsstelle_meldezeitraumid_fkey;

ALTER TABLE studiumspraktikumsstelle
    ADD CONSTRAINT studiumspraktikumsstelle_meldezeitraum_fkey_cascade FOREIGN KEY (meldezeitraumid)
        REFERENCES meldezeitraum (id) MATCH SIMPLE
        ON DELETE CASCADE;

ALTER TABLE ausbildungspraktikumsstelle DROP CONSTRAINT ausbildungspraktikumsstelle_meldezeitraumid_fkey;

ALTER TABLE ausbildungspraktikumsstelle
    ADD CONSTRAINT ausbildungspraktikumsstelle_meldezeitraum_fkey_cascade FOREIGN KEY (meldezeitraumid)
        REFERENCES meldezeitraum (id) MATCH SIMPLE
        ON DELETE CASCADE;