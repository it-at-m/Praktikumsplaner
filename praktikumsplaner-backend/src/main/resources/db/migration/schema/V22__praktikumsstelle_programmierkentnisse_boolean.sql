ALTER TABLE praktikumsstelle
    ALTER COLUMN programmierkenntnisse TYPE BOOLEAN
        USING (
        CASE
            WHEN programmierkenntnisse IS NULL THEN FALSE
            ELSE lower(programmierkenntnisse::text) IN ('ja', 'true')
            END
        ),
    ALTER COLUMN programmierkenntnisse SET NOT NULL;
