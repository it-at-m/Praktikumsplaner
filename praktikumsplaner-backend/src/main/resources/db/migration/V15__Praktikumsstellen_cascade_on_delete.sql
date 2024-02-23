ALTER TABLE public.studiumspraktikumsstelle DROP CONSTRAINT studiumspraktikumsstelle_meldezeitraumid_fkey;

ALTER TABLE public.studiumspraktikumsstelle
    ADD CONSTRAINT studiumspraktikumsstelle_meldezeitraum_fkey_cascade FOREIGN KEY (meldezeitraumid)
        REFERENCES public.meldezeitraum (id) MATCH SIMPLE
        ON DELETE CASCADE;

ALTER TABLE public.ausbildungspraktikumsstelle DROP CONSTRAINT ausbildungspraktikumsstelle_meldezeitraumid_fkey;

ALTER TABLE public.ausbildungspraktikumsstelle
    ADD CONSTRAINT ausbildungspraktikumsstelle_meldezeitraum_fkey_cascade FOREIGN KEY (meldezeitraumid)
        REFERENCES public.meldezeitraum (id) MATCH SIMPLE
        ON DELETE CASCADE;