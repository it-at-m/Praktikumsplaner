-- Studium
insert into PRAKTIKUMSSTELLE (ID, DIENSTSTELLE, TAETIGKEITEN, DRINGLICHKEIT, NAMENTLICHEANFORDERUNG, PROGRAMMIERKENNTNISSE, STUDIENSEMESTER, RICHTUNG, MELDEZEITRAUMID, ASSIGNEDNWK, PROJEKTARBEIT, PLANSTELLEVORHANDEN, WUENSCHE)
values
    -- current Zeitraum
     ( '00000000-0000-0000-0000-000000000001', 'ITM-SLP322', 'diverse Aufgaben', 'NACHRANGIG', null, false,
       'SEMESTER1,SEMESTER2,SEMESTER3,SEMESTER4', 'VI', '00000000-0000-0000-0000-000000000001', null, false, false, null),

     ( '00000000-0000-0000-0000-000000000002', 'ITM-SLP322', 'diverse Aufgaben', 'NACHRANGIG', null, true,
       'SEMESTER1,SEMESTER2,SEMESTER3,SEMESTER4', 'BWI', '00000000-0000-0000-0000-000000000001', null, false, false, null),

     ( '00000000-0000-0000-0000-000000000003', 'ITM-SLP322', 'diverse Aufgaben', 'NACHRANGIG', null, true,
       'SEMESTER1,SEMESTER2,SEMESTER3,SEMESTER4', 'BSC', '00000000-0000-0000-0000-000000000001', null, false, false, null),

     ( '00000000-0000-0000-0000-000000000004', 'ITM-SLP322', 'diverse Aufgaben', 'NACHRANGIG', null, false,
       'SEMESTER3,SEMESTER4', 'LLB', '00000000-0000-0000-0000-000000000001', null, false, false, null),

     ( '00000000-0000-0000-0000-000000000005', 'ITM-SLP322', 'diverse Aufgaben', 'DRINGEND', null, false,
       'SEMESTER3,SEMESTER4', 'PUMA', '00000000-0000-0000-0000-000000000001', null, false, false, null),

     ( '00000000-0000-0000-0000-000000000006', 'ITM-SLP322', 'diverse Aufgaben', 'DRINGEND', null, false,
       'SEMESTER3,SEMESTER4', 'QE3', '00000000-0000-0000-0000-000000000001', null, false, false, null),

     ( '00000000-0000-0000-0000-000000000007', 'ITM-DKL12', 'diverse Aufgaben', 'NACHRANGIG', null, true,
       'SEMESTER1,SEMESTER2,SEMESTER3,SEMESTER4', 'BSC', '00000000-0000-0000-0000-000000000001', null, false, false, null),

     ( '00000000-0000-0000-0000-000000000008', 'ITM-DKL12', 'Projekt', 'ZWINGEND', 'Arthur Edwards', true,
       'SEMESTER3,SEMESTER4', 'BSC', '00000000-0000-0000-0000-000000000001', null, false, false, null),

     ( '00000000-0000-0000-0000-000000000009', 'ITM-DKL23', 'Projekt', 'ZWINGEND', 'Francisco Olson', true,
       'SEMESTER3,SEMESTER4', 'BSC', '00000000-0000-0000-0000-000000000001', null, false, false, null),

     ( '00000000-0000-0000-0000-000000000010', 'ITM-DKL24', 'Projektunterstützung', 'DRINGEND', 'Janet Roberts', true,
       'SEMESTER3,SEMESTER4', 'BSC', '00000000-0000-0000-0000-000000000001', null, false, false, null),

    -- previous zeitraum
     ( '00000000-0000-0000-0001-000000000001', 'ITM-SLP322', 'diverse Aufgaben', 'NACHRANGIG', null, true,
       'SEMESTER1,SEMESTER2,SEMESTER3,SEMESTER4', 'VI', '00000000-0000-0000-0000-000000000005', null, false, false, null),

     ( '00000000-0000-0000-0001-000000000002', 'ITM-SLP322', 'diverse Aufgaben', 'NACHRANGIG', null, true,
       'SEMESTER1,SEMESTER2,SEMESTER3,SEMESTER4', 'BWI', '00000000-0000-0000-0000-000000000005', null, false, false, null),

     ( '00000000-0000-0000-0001-000000000003', 'ITM-SLP322', 'diverse Aufgaben', 'NACHRANGIG', null, true,
       'SEMESTER1,SEMESTER2,SEMESTER3,SEMESTER4', 'BSC', '00000000-0000-0000-0000-000000000005', null, false, false, null),

     ( '00000000-0000-0000-0001-000000000004', 'ITM-SLP322', 'diverse Aufgaben', 'NACHRANGIG', null, true,
       'SEMESTER3,SEMESTER4', 'LLB', '00000000-0000-0000-0000-000000000005', null, false, false, null),

     ( '00000000-0000-0000-0001-000000000005', 'ITM-SLP322', 'diverse Aufgaben', 'DRINGEND', null, false,
       'SEMESTER3,SEMESTER4', 'PUMA', '00000000-0000-0000-0000-000000000005', null, false, false, null),

     ( '00000000-0000-0000-0001-000000000006', 'ITM-SLP322', 'diverse Aufgaben', 'DRINGEND', null, false,
       'SEMESTER3,SEMESTER4', 'QE3', '00000000-0000-0000-0000-000000000005', null, false, false, null),

     ( '00000000-0000-0000-0001-000000000007', 'ITM-DKL12', 'diverse Aufgaben', 'NACHRANGIG', null, true,
       'SEMESTER1,SEMESTER2,SEMESTER3,SEMESTER4', 'BSC', '00000000-0000-0000-0000-000000000005', null, false, false, null),

     ( '00000000-0000-0000-0001-000000000008', 'ITM-DKL12', 'Projekt', 'ZWINGEND', 'Marcus Harrison', true,
       'SEMESTER3,SEMESTER4', 'BSC', '00000000-0000-0000-0000-000000000005', null, false, false, null),

     ( '00000000-0000-0000-0001-000000000009', 'ITM-DKL23', 'Projekt', 'ZWINGEND', 'Russell Long', true,
       'SEMESTER3,SEMESTER4', 'BSC', '00000000-0000-0000-0000-000000000005', null, false, false, null),

     ( '00000000-0000-0000-0001-000000000010', 'ITM-DKL24', 'Projektunterstützung', 'DRINGEND', 'Lynn Holmes', true,
       'SEMESTER3,SEMESTER4', 'BSC', '00000000-0000-0000-0000-000000000005', null, false, false, null)
;

-- Ausbildung
insert into PRAKTIKUMSSTELLE (ID, DIENSTSTELLE, TAETIGKEITEN, DRINGLICHKEIT, NAMENTLICHEANFORDERUNG, PROJEKTARBEIT, AUSBILDUNGSJAHR, RICHTUNG, MELDEZEITRAUMID, ASSIGNEDNWK, PROGRAMMIERKENNTNISSE, PLANSTELLEVORHANDEN, WUENSCHE, STUDIENSEMESTER)
values
    -- current zeitraum
     ( '00000000-0000-0000-0000-000000000011', 'ITM-DKL31', 'Projekt', 'ZWINGEND', 'Crystal Jordan', true,
       'JAHR3', 'FISI', '00000000-0000-0000-0000-000000000001', null, true, false, null, null),

     ( '00000000-0000-0000-0000-000000000012', 'ITM-DKL31', 'Projektunterstützung', 'DRINGEND', 'Diana Barnes', false,
       'JAHR2', 'QE2', '00000000-0000-0000-0000-000000000001', null, true, false, null, null),

     ( '00000000-0000-0000-0000-000000000013', 'ITM-DKL32', 'Projektunterstützung', 'DRINGEND', null, true,
       'JAHR2,JAHR3', 'KFB', '00000000-0000-0000-0000-000000000001', null, false, false, null, null),

     ( '00000000-0000-0000-0000-000000000014', 'ITM-DKL22', 'Projektunterstützung', 'DRINGEND', null, true,
       'JAHR2,JAHR3', 'VFAK', '00000000-0000-0000-0000-000000000001', null, false, false, null, null),

    -- previous zeitraum
     ( '00000000-0000-0000-0001-000000000011', 'ITM-DKL31', 'Projekt', 'ZWINGEND', 'Crystal Jordan', true,
       'JAHR3', 'FISI', '00000000-0000-0000-0000-000000000005', null, true, false, null, null),

     ( '00000000-0000-0000-0001-000000000012', 'ITM-DKL31', 'Projektunterstützung', 'DRINGEND', 'Lillian Henderson', false,
       'JAHR2', 'QE2', '00000000-0000-0000-0000-000000000005', null, true, false, null, null),

     ( '00000000-0000-0000-0001-000000000013', 'ITM-DKL32', 'Projektunterstützung', 'DRINGEND', null, true,
       'JAHR2,JAHR3', 'KFB', '00000000-0000-0000-0000-000000000005', null, false, false, null, null),

     ( '00000000-0000-0000-0001-000000000014', 'ITM-DKL22', 'Projektunterstützung', 'DRINGEND', null, true,
       'JAHR2,JAHR3', 'VFAK', '00000000-0000-0000-0000-000000000005', null, false, false, null, null)
;

INSERT INTO praktikumsstelle_ausbilder (praktikumsstelle_id, position, name, email, erwFuehrungszeugnisVorhanden, minderjaehrigMoeglich)
VALUES
    ('00000000-0000-0000-0000-000000000001', 0, 'Wade Douglas', 'wade.douglas@localhost.de', false, false),
    ('00000000-0000-0000-0000-000000000001', 1, 'Second Instructor', 'second.instructor@localhost.de', true, true),
    ('00000000-0000-0000-0000-000000000002', 0, 'Wade Douglas', 'wade.douglas@localhost.de', false, false),
    ('00000000-0000-0000-0000-000000000003', 0, 'Wade Douglas', 'wade.douglas@localhost.de', false, false),
    ('00000000-0000-0000-0000-000000000004', 0, 'Bobbie Valdez', 'Bobbie.valdez@localhost.de', false, false),
    ('00000000-0000-0000-0000-000000000005', 0, 'Sonia Guerrero', 'sonia.guerrero@localhost.de', false, false),
    ('00000000-0000-0000-0000-000000000006', 0, 'Alberto Burton', 'alberto.burton@localhost.de', false, false),
    ('00000000-0000-0000-0000-000000000007', 0, 'Brett Hoffman', 'brett.hoffman@localhost.de', false, false),
    ('00000000-0000-0000-0000-000000000008', 0, 'Cecil Bradley', 'cecil.bradley@localhost.de', false, false),
    ('00000000-0000-0000-0000-000000000009', 0, 'Eileen Andrews', 'eileen.andrews@localhost.de', false, false),
    ('00000000-0000-0000-0000-000000000010', 0, 'Alma Burton', 'alma.castro@localhost.de', false, false),
    ('00000000-0000-0000-0001-000000000001', 0, 'Wade Douglas', 'wade.douglas@localhost.de', false, false),
    ('00000000-0000-0000-0001-000000000002', 0, 'Wade Douglas', 'wade.douglas@localhost.de', false, false),
    ('00000000-0000-0000-0001-000000000003', 0, 'Wade Douglas', 'wade.douglas@localhost.de', false, false),
    ('00000000-0000-0000-0001-000000000004', 0, 'Bobbie Valdez', 'Bobbie.valdez@localhost.de', false, false),
    ('00000000-0000-0000-0001-000000000005', 0, 'Sonia Guerrero', 'sonia.guerrero@localhost.de', false, false),
    ('00000000-0000-0000-0001-000000000006', 0, 'Alberto Burton', 'alberto.burton@localhost.de', false, false),
    ('00000000-0000-0000-0001-000000000007', 0, 'Brett Hoffman', 'brett.hoffman@localhost.de', false, false),
    ('00000000-0000-0000-0001-000000000008', 0, 'Cecil Bradley', 'cecil.bradley@localhost.de', false, false),
    ('00000000-0000-0000-0001-000000000009', 0, 'Eileen Andrews', 'eileen.andrews@localhost.de', false, false),
    ('00000000-0000-0000-0001-000000000010', 0, 'Alma Burton', 'alma.castro@localhost.de', false, false),
    ('00000000-0000-0000-0000-000000000011', 0, 'Shannon Chavez', 'shannon.chavez@localhost.de', false, false),
    ('00000000-0000-0000-0000-000000000012', 0, 'Charles Gibson', 'charles.gibson@localhost.de', false, false),
    ('00000000-0000-0000-0000-000000000013', 0, 'Robert Smith', 'robert.smith@localhost.de', false, false),
    ('00000000-0000-0000-0000-000000000014', 0, 'Hans Smith', 'Hans.smith@localhost.de', false, false),
    ('00000000-0000-0000-0001-000000000011', 0, 'Shannon Chavez', 'shannon.chavez@localhost.de', false, false),
    ('00000000-0000-0000-0001-000000000012', 0, 'Charles Gibson', 'charles.gibson@localhost.de', false, false),
    ('00000000-0000-0000-0001-000000000013', 0, 'Robert Smith', 'robert.smith@localhost.de', false, false),
    ('00000000-0000-0000-0001-000000000014', 0, 'Hans Smith', 'hans.smith@localhost.de', false, false);
