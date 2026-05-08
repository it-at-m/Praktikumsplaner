ALTER TABLE nwk
    ADD COLUMN richtung VARCHAR;

UPDATE nwk SET richtung =
        CASE
            WHEN studiengang IS NOT NULL AND studiengang <> '' THEN studiengang
            WHEN ausbildungsrichtung IS NOT NULL AND ausbildungsrichtung <> '' THEN ausbildungsrichtung
            END;

ALTER TABLE nwk ALTER COLUMN richtung SET NOT NULL;

ALTER TABLE nwk
    DROP CONSTRAINT check_studiengang_ausbildungsrichtung,
    DROP COLUMN studiengang,
    DROP COLUMN ausbildungsrichtung;
