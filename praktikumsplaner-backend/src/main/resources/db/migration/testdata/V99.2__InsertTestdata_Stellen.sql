insert into STUDIUMSPRAKTIKUMSSTELLE (ID, DIENSTSTELLE, OERTLICHEAUSBILDER, EMAIL, TAETIGKEITEN, DRINGLICHKEIT, NAMENTLICHEANFORDERUNG, PROGRAMMIERKENNTNISSE, STUDIENSEMESTER, STUDIENGANG, MELDEZEITRAUMID, ASSIGNEDNWK)
values
    -- aktueller Zeitraum
    ( '00000000-0000-0000-0000-000000000001', 'ITM-SLP322', 'Wade Douglas', 'wade.douglas@localhost.de',
     'diverse Aufgaben', 'NACHRANGIG', null, 'ja',
     'SEMESTER1,SEMESTER2,SEMESTER3,SEMESTER4', 'VI', '00000000-0000-0000-0000-000000000001', null),

    ( '00000000-0000-0000-0000-000000000002', 'ITM-SLP322', 'Wade Douglas', 'wade.douglas@localhost.de',
      'diverse Aufgaben', 'NACHRANGIG', null, 'ja',
      'SEMESTER1,SEMESTER2,SEMESTER3,SEMESTER4', 'BWI', '00000000-0000-0000-0000-000000000001', null),

    ( '00000000-0000-0000-0000-000000000003', 'ITM-SLP322', 'Wade Douglas', 'wade.douglas@localhost.de',
      'diverse Aufgaben', 'NACHRANGIG', null, 'ja',
      'SEMESTER1,SEMESTER2,SEMESTER3,SEMESTER4', 'BSC', '00000000-0000-0000-0000-000000000001', null),

    ( '00000000-0000-0000-0000-000000000004', 'ITM-SLP322', 'Bobbie Valdez', 'Bobbie.valdez@localhost.de',
      'diverse Aufgaben', 'NACHRANGIG', null, 'ja',
      'SEMESTER3,SEMESTER4', 'LLB', '00000000-0000-0000-0000-000000000001', null),

    ( '00000000-0000-0000-0000-000000000005', 'ITM-SLP322', 'Sonia Guerrero', 'sonia.guerrero@localhost.de',
      'diverse Aufgaben', 'DRINGEND', null, 'ja',
      'SEMESTER3,SEMESTER4', 'PUMA', '00000000-0000-0000-0000-000000000001', null),

    ( '00000000-0000-0000-0000-000000000006', 'ITM-SLP322', 'Alberto Burton', 'alberto.burton@localhost.de',
      'diverse Aufgaben', 'DRINGEND', null, 'ja',
      'SEMESTER3,SEMESTER4', 'QE3', '00000000-0000-0000-0000-000000000001', null),

    ( '00000000-0000-0000-0000-000000000007', 'ITM-DKL12', 'Brett Hoffman', 'brett.hoffman@localhost.de',
      'diverse Aufgaben', 'NACHRANGIG', null, 'ja',
      'SEMESTER1,SEMESTER2,SEMESTER3,SEMESTER4', 'BSC', '00000000-0000-0000-0000-000000000001', null),

    ( '00000000-0000-0000-0000-000000000008', 'ITM-DKL12', 'Cecil Bradley', 'cecil.bradley@localhost.de',
      'Projekt', 'ZWINGEND', 'Arthur Edwards', 'ja',
      'SEMESTER3,SEMESTER4', 'BSC', '00000000-0000-0000-0000-000000000001', null),

    ( '00000000-0000-0000-0000-000000000009', 'ITM-DKL23', 'Eileen Andrews', 'eileen.andrews@localhost.de',
      'Projekt', 'ZWINGEND', 'Francisco Olson', 'ja',
      'SEMESTER3,SEMESTER4', 'BSC', '00000000-0000-0000-0000-000000000001', null),

    ( '00000000-0000-0000-0000-000000000010', 'ITM-DKL24', 'Alma Burton', 'alma.castro@localhost.de',
      'Projektunterstützung', 'DRINGEND', 'Janet Roberts', 'ja',
      'SEMESTER3,SEMESTER4', 'BSC', '00000000-0000-0000-0000-000000000001', null),

    -- previous zeitraum
    ( '00000000-0000-0000-0001-000000000001', 'ITM-SLP322', 'Wade Douglas', 'wade.douglas@localhost.de',
      'diverse Aufgaben', 'NACHRANGIG', null, 'ja',
      'SEMESTER1,SEMESTER2,SEMESTER3,SEMESTER4', 'VI', '00000000-0000-0000-0000-000000000005', null),

    ( '00000000-0000-0000-0001-000000000002', 'ITM-SLP322', 'Wade Douglas', 'wade.douglas@localhost.de',
      'diverse Aufgaben', 'NACHRANGIG', null, 'ja',
      'SEMESTER1,SEMESTER2,SEMESTER3,SEMESTER4', 'BWI', '00000000-0000-0000-0000-000000000005', null),

    ( '00000000-0000-0000-0001-000000000003', 'ITM-SLP322', 'Wade Douglas', 'wade.douglas@localhost.de',
      'diverse Aufgaben', 'NACHRANGIG', null, 'ja',
      'SEMESTER1,SEMESTER2,SEMESTER3,SEMESTER4', 'BSC', '00000000-0000-0000-0000-000000000005', null),

    ( '00000000-0000-0000-0001-000000000004', 'ITM-SLP322', 'Bobbie Valdez', 'Bobbie.valdez@localhost.de',
      'diverse Aufgaben', 'NACHRANGIG', null, 'ja',
      'SEMESTER3,SEMESTER4', 'LLB', '00000000-0000-0000-0000-000000000005', null),

    ( '00000000-0000-0000-0001-000000000005', 'ITM-SLP322', 'Sonia Guerrero', 'sonia.guerrero@localhost.de',
      'diverse Aufgaben', 'DRINGEND', null, 'ja',
      'SEMESTER3,SEMESTER4', 'PUMA', '00000000-0000-0000-0000-000000000005', null),

    ( '00000000-0000-0000-0001-000000000006', 'ITM-SLP322', 'Alberto Burton', 'alberto.burton@localhost.de',
      'diverse Aufgaben', 'DRINGEND', null, 'ja',
      'SEMESTER3,SEMESTER4', 'QE3', '00000000-0000-0000-0000-000000000005', null),

    ( '00000000-0000-0000-0001-000000000007', 'ITM-DKL12', 'Brett Hoffman', 'brett.hoffman@localhost.de',
      'diverse Aufgaben', 'NACHRANGIG', null, 'ja',
      'SEMESTER1,SEMESTER2,SEMESTER3,SEMESTER4', 'BSC', '00000000-0000-0000-0000-000000000005', null),

    ( '00000000-0000-0000-0001-000000000008', 'ITM-DKL12', 'Cecil Bradley', 'cecil.bradley@localhost.de',
      'Projekt', 'ZWINGEND', 'Marcus Harrison', 'ja',
      'SEMESTER3,SEMESTER4', 'BSC', '00000000-0000-0000-0000-000000000005', null),

    ( '00000000-0000-0000-0001-000000000009', 'ITM-DKL23', 'Eileen Andrews', 'eileen.andrews@localhost.de',
      'Projekt', 'ZWINGEND', 'Russell Long', 'ja',
      'SEMESTER3,SEMESTER4', 'BSC', '00000000-0000-0000-0000-000000000005', null),

    ( '00000000-0000-0000-0001-000000000010', 'ITM-DKL24', 'Alma Burton', 'alma.castro@localhost.de',
      'Projektunterstützung', 'DRINGEND', 'Lynn Holmes', 'ja',
      'SEMESTER3,SEMESTER4', 'BSC', '00000000-0000-0000-0000-000000000005', null)
;

insert into AUSBILDUNGSPRAKTIKUMSSTELLE (ID, DIENSTSTELLE, OERTLICHEAUSBILDER, EMAIL, TAETIGKEITEN, DRINGLICHKEIT, NAMENTLICHEANFORDERUNG, PROJEKTARBEIT, AUSBILDUNGSJAHR, AUSBILDUNGSRICHTUNG, MELDEZEITRAUMID, ASSIGNEDNWK, PROGRAMMIERKENNTNISSE)
values
    -- current zeitraum
    ( '00000000-0000-0000-0000-000000000011', 'ITM-DKL31', 'Shannon Chavez', 'shannon.chavez@localhost.de',
      'Projekt', 'ZWINGEND', 'Crystal Jordan', true,
      'JAHR3', 'FISI', '00000000-0000-0000-0000-000000000001', null, 'true'),

    ( '00000000-0000-0000-0000-000000000012', 'ITM-DKL31', 'Charles Gibson', 'charles.gibson@localhost.de',
      'Projektunterstützung', 'DRINGEND', 'Diana Barnes', false,
      'JAHR2', 'QE2', '00000000-0000-0000-0000-000000000001', null, 'true'),

    ( '00000000-0000-0000-0000-000000000013', 'ITM-DKL32', 'Robert Smith', 'robert.smith@localhost.de',
      'Projektunterstützung', 'DRINGEND', null, true,
      'JAHR2,JAHR3', 'KFB', '00000000-0000-0000-0000-000000000001', null, 'false'),

    ( '00000000-0000-0000-0000-000000000014', 'ITM-DKL22', 'Hans Smith', 'Hans.smith@localhost.de',
      'Projektunterstützung', 'DRINGEND', null, true,
      'JAHR2,JAHR3', 'VFAK', '00000000-0000-0000-0000-000000000001', null, 'false'),

    -- previous zeitraum
    ( '00000000-0000-0000-0001-000000000011', 'ITM-DKL31', 'Shannon Chavez', 'shannon.chavez@localhost.de',
      'Projekt', 'ZWINGEND', 'Crystal Jordan', true,
      'JAHR3', 'FISI', '00000000-0000-0000-0000-000000000005', null, 'true'),

    ( '00000000-0000-0000-0001-000000000012', 'ITM-DKL31', 'Charles Gibson', 'charles.gibson@localhost.de',
      'Projektunterstützung', 'DRINGEND', 'Lillian Henderson', false,
      'JAHR2', 'QE2', '00000000-0000-0000-0000-000000000005', null, 'true'),

    ( '00000000-0000-0000-0001-000000000013', 'ITM-DKL32', 'Robert Smith', 'robert.smith@localhost.de',
      'Projektunterstützung', 'DRINGEND', null, true,
      'JAHR2,JAHR3', 'KFB', '00000000-0000-0000-0000-000000000005', null, 'false'),

    ( '00000000-0000-0000-0001-000000000014', 'ITM-DKL22', 'Hans Smith', 'hans.smith@localhost.de',
      'Projektunterstützung', 'DRINGEND', null, true,
      'JAHR2,JAHR3', 'VFAK', '00000000-0000-0000-0000-000000000005', null, 'false')
;