ALTER TABLE praktikumsstelle
    ALTER COLUMN programmierkenntnisse TYPE BOOLEAN
        USING lower(programmierkenntnisse::text) IN ('ja', 'true')
