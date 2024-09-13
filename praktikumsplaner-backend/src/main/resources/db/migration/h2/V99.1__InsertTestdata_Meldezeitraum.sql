insert into MELDEZEITRAUM (ID, ZEITRAUMNAME, STARTZEITPUNKT, ENDZEITPUNKT)
values
    ( '00000000-0000-0000-0000-000000000001', concat('Jahr ', to_char(dateadd(YEAR, 0, current_date), 'YY')), parsedatetime(concat('01-01-', to_char(dateadd(year, 0, current_date), 'yyyy')), 'dd-MM-yyyy'), parsedatetime(concat('31-12-', to_char(dateadd(year, 0, current_date), 'yyyy')), 'dd-MM-yyyy') ),

    ( '00000000-0000-0000-0000-000000000002', concat('Jahr ', to_char(dateadd(YEAR, 1, current_date), 'YY')), parsedatetime(concat('01-01-', to_char(dateadd(year, 1, current_date), 'yyyy')), 'dd-MM-yyyy'), parsedatetime(concat('31-12-', to_char(dateadd(year, 1, current_date), 'yyyy')), 'dd-MM-yyyy') ),

    ( '00000000-0000-0000-0000-000000000003', concat('Jahr ', to_char(dateadd(YEAR, 2, current_date), 'YY')), parsedatetime(concat('01-01-', to_char(dateadd(year, 2, current_date), 'yyyy')), 'dd-MM-yyyy'), parsedatetime(concat('31-12-', to_char(dateadd(year, 2, current_date), 'yyyy')), 'dd-MM-yyyy') ),

    ( '00000000-0000-0000-0000-000000000004', concat('Jahr ', to_char(dateadd(YEAR, 3, current_date), 'YY')), parsedatetime(concat('01-01-', to_char(dateadd(year, 3, current_date), 'yyyy')), 'dd-MM-yyyy'), parsedatetime(concat('31-12-', to_char(dateadd(year, 3, current_date), 'yyyy')), 'dd-MM-yyyy') ),

    ( '00000000-0000-0000-0000-000000000005', concat('Jahr ', to_char(dateadd(YEAR, -1, current_date), 'YY')), parsedatetime(concat('01-01-', to_char(dateadd(year, -1, current_date), 'yyyy')), 'dd-MM-yyyy'), parsedatetime(concat('31-12-', to_char(dateadd(year, -1, current_date), 'yyyy')), 'dd-MM-yyyy') ),

    ( '00000000-0000-0000-0000-000000000006', concat('Jahr ', to_char(dateadd(YEAR, -2, current_date), 'YY')), parsedatetime(concat('01-01-', to_char(dateadd(year, -2, current_date), 'yyyy')), 'dd-MM-yyyy'), parsedatetime(concat('31-12-', to_char(dateadd(year, -2, current_date), 'yyyy')), 'dd-MM-yyyy') ),

    ( '00000000-0000-0000-0000-000000000007', concat('Jahr ', to_char(dateadd(YEAR, -3, current_date), 'YY')), parsedatetime(concat('01-01-', to_char(dateadd(year, -3, current_date), 'yyyy')), 'dd-MM-yyyy'), parsedatetime(concat('31-12-', to_char(dateadd(year, -3, current_date), 'yyyy')), 'dd-MM-yyyy') )
;