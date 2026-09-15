CREATE TABLE praktikumsstelle_ausbilder(
    praktikumsstelle_id             VARCHAR(36) NOT NULL,
    position                        INTEGER NOT NULL,
    name                            VARCHAR(255) NOT NULL,
    email                           VARCHAR(255) NOT NULL,
    erwFuehrungszeugnisVorhanden  BOOLEAN NOT NULL DEFAULT FALSE,
    minderjaehrigMoeglich          BOOLEAN NOT NULL DEFAULT FALSE,
    PRIMARY KEY (praktikumsstelle_id, position),
    FOREIGN KEY (praktikumsstelle_id) REFERENCES praktikumsstelle(id) ON DELETE CASCADE
);

INSERT INTO praktikumsstelle_ausbilder
    SELECT id, 0, oertlicheausbilder, email, erwfuehrungszeugnisvorhanden, minderjaehrigmoeglich
    FROM praktikumsstelle;

ALTER TABLE praktikumsstelle
    DROP COLUMN oertlicheAusbilder,
    DROP COLUMN email,
    DROP COLUMN erwfuehrungszeugnisvorhanden,
    DROP COLUMN minderjaehrigmoeglich;
