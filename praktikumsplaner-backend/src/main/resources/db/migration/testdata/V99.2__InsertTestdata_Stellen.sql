-- Insert unified test data into PRAKTIKUMSSTELLE (replaces legacy tables)

-- Studium (current and previous)
insert into PRAKTIKUMSSTELLE (
    id, dienststelle, oertlicheAusbilder, email, taetigkeiten, dringlichkeit,
    namentlicheAnforderung, programmierkenntnisse, studiensemester,
    meldezeitraumID, assignedNwk, wuensche, richtung
)
values
    -- aktueller Zeitraum
    ('00000000-0000-0000-0000-000000000001','ITM-SLP322','Wade Douglas','wade.douglas@localhost.de','diverse Aufgaben','NACHRANGIG',null,'ja','SEMESTER1,SEMESTER2,SEMESTER3,SEMESTER4','00000000-0000-0000-0000-000000000001',null,null,'VI'),
    ('00000000-0000-0000-0000-000000000002','ITM-SLP322','Wade Douglas','wade.douglas@localhost.de','diverse Aufgaben','NACHRANGIG',null,'ja','SEMESTER1,SEMESTER2,SEMESTER3,SEMESTER4','00000000-0000-0000-0000-000000000001',null,null,'BWI'),
    ('00000000-0000-0000-0000-000000000003','ITM-SLP322','Wade Douglas','wade.douglas@localhost.de','diverse Aufgaben','NACHRANGIG',null,'ja','SEMESTER1,SEMESTER2,SEMESTER3,SEMESTER4','00000000-0000-0000-0000-000000000001',null,null,'BSC'),
    ('00000000-0000-0000-0000-000000000004','ITM-SLP322','Bobbie Valdez','Bobbie.valdez@localhost.de','diverse Aufgaben','NACHRANGIG',null,'ja','SEMESTER3,SEMESTER4','00000000-0000-0000-0000-000000000001',null,null,'LLB'),
    ('00000000-0000-0000-0000-000000000005','ITM-SLP322','Sonia Guerrero','sonia.guerrero@localhost.de','diverse Aufgaben','DRINGEND',null,'ja','SEMESTER3,SEMESTER4','00000000-0000-0000-0000-000000000001',null,null,'PUMA'),
    ('00000000-0000-0000-0000-000000000006','ITM-SLP322','Alberto Burton','alberto.burton@localhost.de','diverse Aufgaben','DRINGEND',null,'ja','SEMESTER3,SEMESTER4','00000000-0000-0000-0000-000000000001',null,null,'QE3'),
    ('00000000-0000-0000-0000-000000000007','ITM-DKL12','Brett Hoffman','brett.hoffman@localhost.de','diverse Aufgaben','NACHRANGIG',null,'ja','SEMESTER1,SEMESTER2,SEMESTER3,SEMESTER4','00000000-0000-0000-0000-000000000001',null,null,'BSC'),
    ('00000000-0000-0000-0000-000000000008','ITM-DKL12','Cecil Bradley','cecil.bradley@localhost.de','Projekt','ZWINGEND','Arthur Edwards','ja','SEMESTER3,SEMESTER4','00000000-0000-0000-0000-000000000001',null,null,'BSC'),
    ('00000000-0000-0000-0000-000000000009','ITM-DKL23','Eileen Andrews','eileen.andrews@localhost.de','Projekt','ZWINGEND','Francisco Olson','ja','SEMESTER3,SEMESTER4','00000000-0000-0000-0000-000000000001',null,null,'BSC'),
    ('00000000-0000-0000-0000-000000000010','ITM-DKL24','Alma Burton','alma.castro@localhost.de','Projektunterstützung','DRINGEND','Janet Roberts','ja','SEMESTER3,SEMESTER4','00000000-0000-0000-0000-000000000001',null,null,'BSC'),
    -- previous zeitraum
    ('00000000-0000-0000-0001-000000000001','ITM-SLP322','Wade Douglas','wade.douglas@localhost.de','diverse Aufgaben','NACHRANGIG',null,'ja','SEMESTER1,SEMESTER2,SEMESTER3,SEMESTER4','00000000-0000-0000-0000-000000000005',null,null,'VI'),
    ('00000000-0000-0000-0001-000000000002','ITM-SLP322','Wade Douglas','wade.douglas@localhost.de','diverse Aufgaben','NACHRANGIG',null,'ja','SEMESTER1,SEMESTER2,SEMESTER3,SEMESTER4','00000000-0000-0000-0000-000000000005',null,null,'BWI'),
    ('00000000-0000-0000-0001-000000000003','ITM-SLP322','Wade Douglas','wade.douglas@localhost.de','diverse Aufgaben','NACHRANGIG',null,'ja','SEMESTER1,SEMESTER2,SEMESTER3,SEMESTER4','00000000-0000-0000-0000-000000000005',null,null,'BSC'),
    ('00000000-0000-0000-0001-000000000004','ITM-SLP322','Bobbie Valdez','Bobbie.valdez@localhost.de','diverse Aufgaben','NACHRANGIG',null,'ja','SEMESTER3,SEMESTER4','00000000-0000-0000-0000-000000000005',null,null,'LLB'),
    ('00000000-0000-0000-0001-000000000005','ITM-SLP322','Sonia Guerrero','sonia.guerrero@localhost.de','diverse Aufgaben','DRINGEND',null,'ja','SEMESTER3,SEMESTER4','00000000-0000-0000-0000-000000000005',null,null,'PUMA'),
    ('00000000-0000-0000-0001-000000000006','ITM-SLP322','Alberto Burton','alberto.burton@localhost.de','diverse Aufgaben','DRINGEND',null,'ja','SEMESTER3,SEMESTER4','00000000-0000-0000-0000-000000000005',null,null,'QE3'),
    ('00000000-0000-0000-0001-000000000007','ITM-DKL12','Brett Hoffman','brett.hoffman@localhost.de','diverse Aufgaben','NACHRANGIG',null,'ja','SEMESTER1,SEMESTER2,SEMESTER3,SEMESTER4','00000000-0000-0000-0000-000000000005',null,null,'BSC'),
    ('00000000-0000-0000-0001-000000000008','ITM-DKL12','Cecil Bradley','cecil.bradley@localhost.de','Projekt','ZWINGEND','Marcus Harrison','ja','SEMESTER3,SEMESTER4','00000000-0000-0000-0000-000000000005',null,null,'BSC'),
    ('00000000-0000-0000-0001-000000000009','ITM-DKL23','Eileen Andrews','eileen.andrews@localhost.de','Projekt','ZWINGEND','Russell Long','ja','SEMESTER3,SEMESTER4','00000000-0000-0000-0000-000000000005',null,null,'BSC'),
    ('00000000-0000-0000-0001-000000000010','ITM-DKL24','Alma Burton','alma.castro@localhost.de','Projektunterstützung','DRINGEND','Lynn Holmes','ja','SEMESTER3,SEMESTER4','00000000-0000-0000-0000-000000000005',null,null,'BSC');

-- Ausbildung (current and previous)
insert into PRAKTIKUMSSTELLE (
    id, dienststelle, oertlicheAusbilder, email, taetigkeiten, dringlichkeit,
    namentlicheAnforderung, meldezeitraumID, assignedNwk, wuensche, richtung,
    projektarbeit, ausbildungsjahr, minderjaehrigMoeglich
)
values
    -- current zeitraum
    ('00000000-0000-0000-0000-000000000011','ITM-DKL31','Shannon Chavez','shannon.chavez@localhost.de','Projekt','ZWINGEND','Crystal Jordan','00000000-0000-0000-0000-000000000001',null,null,'FISI', true,'JAHR3', true),
    ('00000000-0000-0000-0000-000000000012','ITM-DKL31','Charles Gibson','charles.gibson@localhost.de','Projektunterstützung','DRINGEND','Diana Barnes','00000000-0000-0000-0000-000000000001',null,null,'QE2', false,'JAHR2', true),
    ('00000000-0000-0000-0000-000000000013','ITM-DKL32','Robert Smith','robert.smith@localhost.de','Projektunterstützung','DRINGEND',null,'00000000-0000-0000-0000-000000000001',null,null,'KFB', true,'JAHR2,JAHR3', false),
    ('00000000-0000-0000-0000-000000000014','ITM-DKL22','Hans Smith','Hans.smith@localhost.de','Projektunterstützung','DRINGEND',null,'00000000-0000-0000-0000-000000000001',null,null,'VFAK', true,'JAHR2,JAHR3', false),
    -- previous zeitraum
    ('00000000-0000-0000-0001-000000000011','ITM-DKL31','Shannon Chavez','shannon.chavez@localhost.de','Projekt','ZWINGEND','Crystal Jordan','00000000-0000-0000-0000-000000000005',null,null,'FISI', true,'JAHR3', true),
    ('00000000-0000-0000-0001-000000000012','ITM-DKL31','Charles Gibson','charles.gibson@localhost.de','Projektunterstützung','DRINGEND','Lillian Henderson','00000000-0000-0000-0000-000000000005',null,null,'QE2', false,'JAHR2', true),
    ('00000000-0000-0000-0001-000000000013','ITM-DKL32','Robert Smith','robert.smith@localhost.de','Projektunterstützung','DRINGEND',null,'00000000-0000-0000-0000-000000000005',null,null,'KFB', true,'JAHR2,JAHR3', false),
    ('00000000-0000-0000-0001-000000000014','ITM-DKL22','Hans Smith','hans.smith@localhost.de','Projektunterstützung','DRINGEND',null,'00000000-0000-0000-0000-000000000005',null,null,'VFAK', true,'JAHR2,JAHR3', false);
