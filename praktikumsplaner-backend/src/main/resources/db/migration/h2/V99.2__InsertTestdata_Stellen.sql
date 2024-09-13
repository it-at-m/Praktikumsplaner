insert into STUDIUMSPRAKTIKUMSSTELLE (ID, DIENSTSTELLE, OERTLICHEAUSBILDER, EMAIL, TAETIGKEITEN, DRINGLICHKEIT, NAMENTLICHEANFORDERUNG, REFERAT, PROGRAMMIERKENNTNISSE, STUDIENSEMESTER, STUDIENGANG, MELDEZEITRAUMID, ASSIGNEDNWK)
values
    -- aktueller Zeitraum
    ( '00000000-0000-0000-0000-000000000001', 'KM 822', 'Wade Douglas', 'wade.douglas@localhost.de',
     'diverse Aufgaben', 'NACHRANGIG', null, 'ITM', 'ja',
     'SEMESTER1,SEMESTER2,SEMESTER3,SEMESTER4', 'VI', '00000000-0000-0000-0000-000000000001', null),

    ( '00000000-0000-0000-0000-000000000002', 'KM 822', 'Wade Douglas', 'wade.douglas@localhost.de',
      'diverse Aufgaben', 'NACHRANGIG', null, 'ITM', 'ja',
      'SEMESTER1,SEMESTER2,SEMESTER3,SEMESTER4', 'BWI', '00000000-0000-0000-0000-000000000001', null),

    ( '00000000-0000-0000-0000-000000000003', 'KM 822', 'Wade Douglas', 'wade.douglas@localhost.de',
      'diverse Aufgaben', 'NACHRANGIG', null, 'ITM', 'ja',
      'SEMESTER1,SEMESTER2,SEMESTER3,SEMESTER4', 'BSC', '00000000-0000-0000-0000-000000000001', null),

    ( '00000000-0000-0000-0000-000000000004', 'KM 822', 'Bobbie Valdez', 'Bobbie.valdez@localhost.de',
      'diverse Aufgaben', 'NACHRANGIG', null, 'ITM', 'ja',
      'SEMESTER3,SEMESTER4', 'BSC', '00000000-0000-0000-0000-000000000001', null),

    ( '00000000-0000-0000-0000-000000000005', 'KM 822', 'Sonia Guerrero', 'sonia.guerrero@localhost.de',
      'diverse Aufgaben', 'DRINGEND', null, 'ITM', 'ja',
      'SEMESTER3,SEMESTER4', 'BSC', '00000000-0000-0000-0000-000000000001', null),

    ( '00000000-0000-0000-0000-000000000006', 'KM 822', 'Alberto Burton', 'alberto.burton@localhost.de',
      'diverse Aufgaben', 'DRINGEND', null, 'ITM', 'ja',
      'SEMESTER3,SEMESTER4', 'BSC', '00000000-0000-0000-0000-000000000001', null),

    ( '00000000-0000-0000-0000-000000000007', 'KM 12', 'Brett Hoffman', 'brett.hoffman@localhost.de',
      'diverse Aufgaben', 'NACHRANGIG', null, 'ITM', 'ja',
      'SEMESTER1,SEMESTER2,SEMESTER3,SEMESTER4', 'BSC', '00000000-0000-0000-0000-000000000001', null),

    ( '00000000-0000-0000-0000-000000000008', 'KM 12', 'Cecil Bradley', 'cecil.bradley@localhost.de',
      'Projekt', 'ZWINGEND', 'Arthur Edwards', 'ITM', 'ja',
      'SEMESTER3,SEMESTER4', 'BSC', '00000000-0000-0000-0000-000000000001', null),

    ( '00000000-0000-0000-0000-000000000009', 'KM 23', 'Eileen Andrews', 'eileen.andrews@localhost.de',
      'Projekt', 'ZWINGEND', 'Francisco Olson', 'ITM', 'ja',
      'SEMESTER3,SEMESTER4', 'BSC', '00000000-0000-0000-0000-000000000001', null),

    ( '00000000-0000-0000-0000-000000000010', 'KM 24', 'Alma Burton', 'alma.castro@localhost.de',
      'Projektunterstützung', 'DRINGEND', 'Janet Roberts', 'ITM', 'ja',
      'SEMESTER3,SEMESTER4', 'BSC', '00000000-0000-0000-0000-000000000001', null),

    -- previous zeitraum
    ( '00000000-0000-0000-0001-000000000001', 'KM 822', 'Wade Douglas', 'wade.douglas@localhost.de',
      'diverse Aufgaben', 'NACHRANGIG', null, 'ITM', 'ja',
      'SEMESTER1,SEMESTER2,SEMESTER3,SEMESTER4', 'VI', '00000000-0000-0000-0000-000000000005', null),

    ( '00000000-0000-0000-0001-000000000002', 'KM 822', 'Wade Douglas', 'wade.douglas@localhost.de',
      'diverse Aufgaben', 'NACHRANGIG', null, 'ITM', 'ja',
      'SEMESTER1,SEMESTER2,SEMESTER3,SEMESTER4', 'BWI', '00000000-0000-0000-0000-000000000005', null),

    ( '00000000-0000-0000-0001-000000000003', 'KM 822', 'Wade Douglas', 'wade.douglas@localhost.de',
      'diverse Aufgaben', 'NACHRANGIG', null, 'ITM', 'ja',
      'SEMESTER1,SEMESTER2,SEMESTER3,SEMESTER4', 'BSC', '00000000-0000-0000-0000-000000000005', null),

    ( '00000000-0000-0000-0001-000000000004', 'KM 822', 'Bobbie Valdez', 'Bobbie.valdez@localhost.de',
      'diverse Aufgaben', 'NACHRANGIG', null, 'ITM', 'ja',
      'SEMESTER3,SEMESTER4', 'BSC', '00000000-0000-0000-0000-000000000005', null),

    ( '00000000-0000-0000-0001-000000000005', 'KM 822', 'Sonia Guerrero', 'sonia.guerrero@localhost.de',
      'diverse Aufgaben', 'DRINGEND', null, 'ITM', 'ja',
      'SEMESTER3,SEMESTER4', 'BSC', '00000000-0000-0000-0000-000000000005', null),

    ( '00000000-0000-0000-0001-000000000006', 'KM 822', 'Alberto Burton', 'alberto.burton@localhost.de',
      'diverse Aufgaben', 'DRINGEND', null, 'ITM', 'ja',
      'SEMESTER3,SEMESTER4', 'BSC', '00000000-0000-0000-0000-000000000005', null),

    ( '00000000-0000-0000-0001-000000000007', 'KM 12', 'Brett Hoffman', 'brett.hoffman@localhost.de',
      'diverse Aufgaben', 'NACHRANGIG', null, 'ITM', 'ja',
      'SEMESTER1,SEMESTER2,SEMESTER3,SEMESTER4', 'BSC', '00000000-0000-0000-0000-000000000005', null),

    ( '00000000-0000-0000-0001-000000000008', 'KM 12', 'Cecil Bradley', 'cecil.bradley@localhost.de',
      'Projekt', 'ZWINGEND', 'Marcus Harrison', 'ITM', 'ja',
      'SEMESTER3,SEMESTER4', 'BSC', '00000000-0000-0000-0000-000000000005', null),

    ( '00000000-0000-0000-0001-000000000009', 'KM 23', 'Eileen Andrews', 'eileen.andrews@localhost.de',
      'Projekt', 'ZWINGEND', 'Russell Long', 'ITM', 'ja',
      'SEMESTER3,SEMESTER4', 'BSC', '00000000-0000-0000-0000-000000000005', null),

    ( '00000000-0000-0000-0001-000000000010', 'KM 24', 'Alma Burton', 'alma.castro@localhost.de',
      'Projektunterstützung', 'DRINGEND', 'Lynn Holmes', 'ITM', 'ja',
      'SEMESTER3,SEMESTER4', 'BSC', '00000000-0000-0000-0000-000000000005', null)
;

insert into AUSBILDUNGSPRAKTIKUMSSTELLE (ID, DIENSTSTELLE, OERTLICHEAUSBILDER, EMAIL, TAETIGKEITEN, DRINGLICHKEIT, NAMENTLICHEANFORDERUNG, REFERAT, PROJEKTARBEIT, AUSBILDUNGSJAHR, AUSBILDUNGSRICHTUNG, MELDEZEITRAUMID, ASSIGNEDNWK, PROGRAMMIERKENNTNISSE)
values
    -- current zeitraum
    ( '00000000-0000-0000-0000-000000000011', 'KM 31', 'Shannon Chavez', 'shannon.chavez@localhost.de',
      'Projekt', 'ZWINGEND', 'Crystal Jordan', 'ITM', true,
      'JAHR3', 'FISI', '00000000-0000-0000-0000-000000000001', null, 'true'),

    ( '00000000-0000-0000-0000-000000000012', 'KM 31', 'Charles Gibson', 'charles.gibson@localhost.de',
      'Projektunterstützung', 'DRINGEND', 'Diana Barnes', 'ITM', false,
      'JAHR2', 'FISI', '00000000-0000-0000-0000-000000000001', null, 'true'),

    ( '00000000-0000-0000-0000-000000000013', 'KM 32', 'Robert Smith', 'robert.smith@localhost.de',
      'Projektunterstützung', 'DRINGEND', null, 'ITM', true,
      'JAHR2,JAHR3', 'FISI', '00000000-0000-0000-0000-000000000001', null, 'false'),

    -- previous zeitraum
    ( '00000000-0000-0000-0001-000000000011', 'KM 31', 'Shannon Chavez', 'shannon.chavez@localhost.de',
      'Projekt', 'ZWINGEND', 'Crystal Jordan', 'ITM', true,
      'JAHR3', 'FISI', '00000000-0000-0000-0000-000000000005', null, 'true'),

    ( '00000000-0000-0000-0001-000000000012', 'KM 31', 'Charles Gibson', 'charles.gibson@localhost.de',
      'Projektunterstützung', 'DRINGEND', 'Lillian Henderson', 'ITM', false,
      'JAHR2', 'FISI', '00000000-0000-0000-0000-000000000005', null, 'true'),

    ( '00000000-0000-0000-0001-000000000013', 'KM 32', 'Robert Smith', 'robert.smith@localhost.de',
      'Projektunterstützung', 'DRINGEND', null, 'ITM', true,
      'JAHR2,JAHR3', 'FISI', '00000000-0000-0000-0000-000000000005', null, 'false')
;