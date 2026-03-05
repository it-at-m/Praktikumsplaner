ALTER TABLE Nwk ALTER COLUMN studiengang TYPE varchar(3);
ALTER TABLE Nwk ALTER COLUMN studiengang DROP NOT NULL;
ALTER TABLE Nwk ADD COLUMN ausbildungsrichtung varchar(4);
ALTER TABLE Nwk
    ADD CONSTRAINT check_studiengang_ausbildungsrichtung
        CHECK (
                (studiengang IS NOT NULL AND ausbildungsrichtung IS NULL)
                OR
                (studiengang IS NULL AND ausbildungsrichtung IS NOT NULL)
            );

ALTER TABLE Studiumspraktikumsstelle ALTER COLUMN studiengang TYPE varchar(3);