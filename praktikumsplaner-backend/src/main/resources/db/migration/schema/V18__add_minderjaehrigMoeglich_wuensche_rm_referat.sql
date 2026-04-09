ALTER TABLE ausbildungspraktikumsstelle
    DROP COLUMN referat;
ALTER TABLE studiumspraktikumsstelle
    DROP COLUMN referat;

ALTER TABLE ausbildungspraktikumsstelle
    ADD COLUMN wuensche VARCHAR(5000);
ALTER TABLE studiumspraktikumsstelle
    ADD COLUMN wuensche VARCHAR(5000);

ALTER TABLE ausbildungspraktikumsstelle
    ADD COLUMN minderjaehrigMoeglich boolean NOT NULL DEFAULT false;
