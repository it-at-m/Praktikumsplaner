insert into NWK (ID, VORNAME, NACHNAME, STUDIENGANG, JAHRGANG, VORLESUNGSTAGE, ACTIVE, AUSBILDUNGSRICHTUNG)
values
    -- Studium
    -- starting the current year
    -- VI
    (  '00000000-0000-0000-0000-000000000001', 'James', 'Smith', 'VI',  concat(concat(to_char(current_date, 'YY'), '/'), to_char(dateadd(YEAR, 3, current_date), 'YY')), null,true, null),
    (  '00000000-0000-0000-0000-000000000002', 'John', 'Smith', 'VI',  concat(concat(to_char(current_date, 'YY'), '/'), to_char(dateadd(YEAR, 3, current_date), 'YY')), null,true, null),
    (  '00000000-0000-0000-0000-000000000003', 'Linda', 'Miller', 'VI',  concat(concat(to_char(current_date, 'YY'), '/'), to_char(dateadd(YEAR, 3, current_date), 'YY')), null,true, null),
    (  '00000000-0000-0000-0000-000000000004', 'Dorothy', 'Wilson', 'VI',  concat(concat(to_char(current_date, 'YY'), '/'), to_char(dateadd(YEAR, 3, current_date), 'YY')), null,true, null),
    (  '00000000-0000-0000-0000-000000000005', 'Kyle', 'Bryant', 'VI',  concat(concat(to_char(current_date, 'YY'), '/'), to_char(dateadd(YEAR, 3, current_date), 'YY')), null,false, null),
    -- BSC
    (  '00000000-0000-0000-0001-000000000001', 'Michelle', 'Jackson', 'BSC',  concat(concat(to_char(current_date, 'YY'), '/'), to_char(dateadd(YEAR, 3, current_date), 'YY')), null,true, null),
    (  '00000000-0000-0000-0001-000000000002', 'Kenneth', 'Thompson', 'BSC',  concat(concat(to_char(current_date, 'YY'), '/'), to_char(dateadd(YEAR, 3, current_date), 'YY')), null,true, null),
    (  '00000000-0000-0000-0001-000000000003', 'Nancy', 'White', 'BSC',  concat(concat(to_char(current_date, 'YY'), '/'), to_char(dateadd(YEAR, 3, current_date), 'YY')), null,true, null),
    (  '00000000-0000-0000-0001-000000000004', 'George', 'Lopez', 'BSC',  concat(concat(to_char(current_date, 'YY'), '/'), to_char(dateadd(YEAR, 3, current_date), 'YY')), null,true, null),
    (  '00000000-0000-0000-0001-000000000005', 'Elaine', 'Ford', 'BSC',  concat(concat(to_char(current_date, 'YY'), '/'), to_char(dateadd(YEAR, 3, current_date), 'YY')), null,false, null),
    -- BWI
    (  '00000000-0000-0000-0002-000000000001', 'Walter', 'Allen', 'BWI',  concat(concat(to_char(current_date, 'YY'), '/'), to_char(dateadd(YEAR, 3, current_date), 'YY')), 'MONDAY,TUESDAY,WEDNESDAY',true, null),
    (  '00000000-0000-0000-0002-000000000002', 'Cynthia', 'King', 'BWI',  concat(concat(to_char(current_date, 'YY'), '/'), to_char(dateadd(YEAR, 3, current_date), 'YY')), 'MONDAY,TUESDAY,WEDNESDAY',true, null),
    (  '00000000-0000-0000-0002-000000000003', 'Kevin', 'Adams', 'BWI',  concat(concat(to_char(current_date, 'YY'), '/'), to_char(dateadd(YEAR, 3, current_date), 'YY')), 'MONDAY,TUESDAY,WEDNESDAY',true, null),
    (  '00000000-0000-0000-0002-000000000004', 'Amanda', 'Roberts', 'BWI',  concat(concat(to_char(current_date, 'YY'), '/'), to_char(dateadd(YEAR, 3, current_date), 'YY')), 'MONDAY,TUESDAY,WEDNESDAY',true, null),
    (  '00000000-0000-0000-0002-000000000005', 'Eddie', 'Ruiz', 'BWI',  concat(concat(to_char(current_date, 'YY'), '/'), to_char(dateadd(YEAR, 3, current_date), 'YY')), 'MONDAY,TUESDAY,WEDNESDAY',false, null),

    -- starting the year before
    -- VI
    (  '00000000-0000-0001-0000-000000000001', 'Christine', 'Roberts', 'VI',  concat(concat(to_char(dateadd(YEAR, -1, current_date), 'YY'), '/'), to_char(dateadd(YEAR, 2, current_date), 'YY')), null,true, null),
    (  '00000000-0000-0001-0000-000000000002', 'Douglas', 'Roberts', 'VI',  concat(concat(to_char(dateadd(YEAR, -1, current_date), 'YY'), '/'), to_char(dateadd(YEAR, 2, current_date), 'YY')), null,true, null),
    (  '00000000-0000-0001-0000-000000000003', 'Marie', 'Roberts', 'VI',  concat(concat(to_char(dateadd(YEAR, -1, current_date), 'YY'), '/'), to_char(dateadd(YEAR, 2, current_date), 'YY')), null,true, null),
    (  '00000000-0000-0001-0000-000000000004', 'Henry', 'Roberts', 'VI',  concat(concat(to_char(dateadd(YEAR, -1, current_date), 'YY'), '/'), to_char(dateadd(YEAR, 2, current_date), 'YY')), null,true, null),
    (  '00000000-0000-0001-0000-000000000005', 'Barry', 'Woods', 'VI',  concat(concat(to_char(dateadd(YEAR, -1, current_date), 'YY'), '/'), to_char(dateadd(YEAR, 2, current_date), 'YY')), null,false, null),
    -- BSC
    (  '00000000-0000-0001-0001-000000000001', 'Janet', 'Roberts', 'BSC',  concat(concat(to_char(dateadd(YEAR, -1, current_date), 'YY'), '/'), to_char(dateadd(YEAR, 2, current_date), 'YY')), null,true, null),
    (  '00000000-0000-0001-0001-000000000002', 'Carl', 'Parker', 'BSC',  concat(concat(to_char(dateadd(YEAR, -1, current_date), 'YY'), '/'), to_char(dateadd(YEAR, 2, current_date), 'YY')), null,true, null),
    (  '00000000-0000-0001-0001-000000000003', 'Catherine', 'Collins', 'BSC',  concat(concat(to_char(dateadd(YEAR, -1, current_date), 'YY'), '/'), to_char(dateadd(YEAR, 2, current_date), 'YY')), null,true, null),
    (  '00000000-0000-0001-0001-000000000004', 'Arthur', 'Edwards', 'BSC',  concat(concat(to_char(dateadd(YEAR, -1, current_date), 'YY'), '/'), to_char(dateadd(YEAR, 2, current_date), 'YY')), null,true, null),
    (  '00000000-0000-0001-0001-000000000005', 'Francisco', 'Olson', 'BSC',  concat(concat(to_char(dateadd(YEAR, -1, current_date), 'YY'), '/'), to_char(dateadd(YEAR, 2, current_date), 'YY')), null,false, null),
    -- BWI
    (  '00000000-0000-0001-0002-000000000001', 'Frances', 'Stewart', 'BWI',  concat(concat(to_char(dateadd(YEAR, -1, current_date), 'YY'), '/'), to_char(dateadd(YEAR, 2, current_date), 'YY')), 'WEDNESDAY,THURSDAY,FRIDAY',true, null),
    (  '00000000-0000-0001-0002-000000000002', 'Roger', 'Flores', 'BWI',  concat(concat(to_char(dateadd(YEAR, -1, current_date), 'YY'), '/'), to_char(dateadd(YEAR, 2, current_date), 'YY')), 'WEDNESDAY,THURSDAY,FRIDAY',true, null),
    (  '00000000-0000-0001-0002-000000000003', 'Diane', 'Morris', 'BWI',  concat(concat(to_char(dateadd(YEAR, -1, current_date), 'YY'), '/'), to_char(dateadd(YEAR, 2, current_date), 'YY')), 'WEDNESDAY,THURSDAY,FRIDAY',true, null),
    (  '00000000-0000-0001-0002-000000000004', 'Alice', 'Nguyen', 'BWI',  concat(concat(to_char(dateadd(YEAR, -1, current_date), 'YY'), '/'), to_char(dateadd(YEAR, 2, current_date), 'YY')), 'WEDNESDAY,THURSDAY,FRIDAY',true, null),
    (  '00000000-0000-0001-0002-000000000005', 'Joanne', 'Snyder', 'BWI',  concat(concat(to_char(dateadd(YEAR, -1, current_date), 'YY'), '/'), to_char(dateadd(YEAR, 2, current_date), 'YY')), 'WEDNESDAY,THURSDAY,FRIDAY',false, null),

    -- starting the two years before
    -- VI
    (  '00000000-0000-0002-0000-000000000001', 'Tammy', 'James', 'VI',  concat(concat(to_char(dateadd(YEAR, -2, current_date), 'YY'), '/'), to_char(dateadd(YEAR, 1, current_date), 'YY')), null,true, null),
    (  '00000000-0000-0002-0000-000000000002', 'Irene', 'Reyes', 'VI',  concat(concat(to_char(dateadd(YEAR, -2, current_date), 'YY'), '/'), to_char(dateadd(YEAR, 1, current_date), 'YY')), null,true, null),
    (  '00000000-0000-0002-0000-000000000003', 'Randy', 'Cruz', 'VI',  concat(concat(to_char(dateadd(YEAR, -2, current_date), 'YY'), '/'), to_char(dateadd(YEAR, 1, current_date), 'YY')), null,true, null),
    (  '00000000-0000-0002-0000-000000000004', 'Eugene', 'Hughes', 'VI',  concat(concat(to_char(dateadd(YEAR, -2, current_date), 'YY'), '/'), to_char(dateadd(YEAR, 1, current_date), 'YY')), null,true, null),
    (  '00000000-0000-0002-0000-000000000005', 'Esther', 'Mason', 'VI',  concat(concat(to_char(dateadd(YEAR, -2, current_date), 'YY'), '/'), to_char(dateadd(YEAR, 1, current_date), 'YY')), null,false, null),
    -- BSC
    (  '00000000-0000-0002-0001-000000000001', 'Rachel', 'Price', 'BSC',  concat(concat(to_char(dateadd(YEAR, -2, current_date), 'YY'), '/'), to_char(dateadd(YEAR, 1, current_date), 'YY')), null,true, null),
    (  '00000000-0000-0002-0001-000000000002', 'Marilyn', 'Myers', 'BSC',  concat(concat(to_char(dateadd(YEAR, -2, current_date), 'YY'), '/'), to_char(dateadd(YEAR, 1, current_date), 'YY')), null,true, null),
    (  '00000000-0000-0002-0001-000000000003', 'Russell', 'Long', 'BSC',  concat(concat(to_char(dateadd(YEAR, -2, current_date), 'YY'), '/'), to_char(dateadd(YEAR, 1, current_date), 'YY')), null,true, null),
    (  '00000000-0000-0002-0001-000000000004', 'Martin', 'Foster', 'BSC',  concat(concat(to_char(dateadd(YEAR, -2, current_date), 'YY'), '/'), to_char(dateadd(YEAR, 1, current_date), 'YY')), null,true, null),
    (  '00000000-0000-0002-0001-000000000005', 'Marcus', 'Harrison', 'BSC',  concat(concat(to_char(dateadd(YEAR, -2, current_date), 'YY'), '/'), to_char(dateadd(YEAR, 1, current_date), 'YY')), null,false, null),
    -- BWI
    (  '00000000-0000-0002-0002-000000000001', 'Anne', 'Sanders', 'BWI',  concat(concat(to_char(dateadd(YEAR, -2, current_date), 'YY'), '/'), to_char(dateadd(YEAR, 1, current_date), 'YY')), 'WEDNESDAY,THURSDAY,FRIDAY',true, null),
    (  '00000000-0000-0002-0002-000000000002', 'Jacqueline', 'Ross', 'BWI',  concat(concat(to_char(dateadd(YEAR, -2, current_date), 'YY'), '/'), to_char(dateadd(YEAR, 1, current_date), 'YY')), 'WEDNESDAY,THURSDAY,FRIDAY',true, null),
    (  '00000000-0000-0002-0002-000000000003', 'Craig', 'Morales', 'BWI',  concat(concat(to_char(dateadd(YEAR, -2, current_date), 'YY'), '/'), to_char(dateadd(YEAR, 1, current_date), 'YY')), 'WEDNESDAY,THURSDAY,FRIDAY',true, null),
    (  '00000000-0000-0002-0002-000000000004', 'Alan', 'Powell', 'BWI',  concat(concat(to_char(dateadd(YEAR, -2, current_date), 'YY'), '/'), to_char(dateadd(YEAR, 1, current_date), 'YY')), 'WEDNESDAY,THURSDAY,FRIDAY',true, null),
    (  '00000000-0000-0002-0002-000000000005', 'Tom', 'Olson', 'BWI',  concat(concat(to_char(dateadd(YEAR, -2, current_date), 'YY'), '/'), to_char(dateadd(YEAR, 1, current_date), 'YY')), 'WEDNESDAY,THURSDAY,FRIDAY',false, null),

    -- Ausbildung
    -- starting the current year
    (  '00000000-0001-0000-0000-000000000001', 'Earl', 'Perry', null,  concat(concat(to_char(dateadd(YEAR, 0, current_date), 'YY'), '/'), to_char(dateadd(YEAR, 3, current_date), 'YY')), null,true, 'FISI'),
    (  '00000000-0001-0000-0000-000000000002', 'Paula', 'Butler', null,  concat(concat(to_char(dateadd(YEAR, 0, current_date), 'YY'), '/'), to_char(dateadd(YEAR, 3, current_date), 'YY')), null,true, 'FISI'),
    (  '00000000-0001-0000-0000-000000000003', 'Diana', 'Barnes', null,  concat(concat(to_char(dateadd(YEAR, 0, current_date), 'YY'), '/'), to_char(dateadd(YEAR, 3, current_date), 'YY')), null,true, 'FISI'),
    (  '00000000-0001-0000-0000-000000000004', 'Antonio', 'Fisher', null,  concat(concat(to_char(dateadd(YEAR, 0, current_date), 'YY'), '/'), to_char(dateadd(YEAR, 3, current_date), 'YY')), null,true, 'FISI'),
    (  '00000000-0001-0000-0000-000000000005', 'Elaine', 'Stevens', null,  concat(concat(to_char(dateadd(YEAR, 0, current_date), 'YY'), '/'), to_char(dateadd(YEAR, 3, current_date), 'YY')), null,false, 'FISI'),

    -- starting the year before
    (  '00000000-0001-0001-0000-000000000001', 'Lillian', 'Henderson', null,  concat(concat(to_char(dateadd(YEAR, -1, current_date), 'YY'), '/'), to_char(dateadd(YEAR, 2, current_date), 'YY')), null,true, 'FISI'),
    (  '00000000-0001-0001-0000-000000000002', 'Bryan', 'Coleman', null,  concat(concat(to_char(dateadd(YEAR, -1, current_date), 'YY'), '/'), to_char(dateadd(YEAR, 2, current_date), 'YY')), null,true, 'FISI'),
    (  '00000000-0001-0001-0000-000000000003', 'Robin', 'Simmons', null,  concat(concat(to_char(dateadd(YEAR, -1, current_date), 'YY'), '/'), to_char(dateadd(YEAR, 2, current_date), 'YY')), null,true, 'FISI'),
    (  '00000000-0001-0001-0000-000000000004', 'Luis', 'Patterson', null,  concat(concat(to_char(dateadd(YEAR, -1, current_date), 'YY'), '/'), to_char(dateadd(YEAR, 2, current_date), 'YY')), null,true, 'FISI'),
    (  '00000000-0001-0001-0000-000000000005', 'Charlotte', 'Stevens', null,  concat(concat(to_char(dateadd(YEAR, -1, current_date), 'YY'), '/'), to_char(dateadd(YEAR, 2, current_date), 'YY')), null,false, 'FISI'),

    -- starting the two years before
    (  '00000000-0001-0002-0000-000000000001', 'Crystal', 'Jordan', null,  concat(concat(to_char(dateadd(YEAR, -2, current_date), 'YY'), '/'), to_char(dateadd(YEAR, 1, current_date), 'YY')), null,true, 'FISI'),
    (  '00000000-0001-0002-0000-000000000002', 'Gladys', 'Reynolds', null,  concat(concat(to_char(dateadd(YEAR, -2, current_date), 'YY'), '/'), to_char(dateadd(YEAR, 1, current_date), 'YY')), null,true, 'FISI'),
    (  '00000000-0001-0002-0000-000000000003', 'Nathan', 'Hamilton', null,  concat(concat(to_char(dateadd(YEAR, -2, current_date), 'YY'), '/'), to_char(dateadd(YEAR, 1, current_date), 'YY')), null,true, 'FISI'),
    (  '00000000-0001-0002-0000-000000000004', 'Florence', 'Graham', null,  concat(concat(to_char(dateadd(YEAR, -2, current_date), 'YY'), '/'), to_char(dateadd(YEAR, 1, current_date), 'YY')), null,true, 'FISI'),
    (  '00000000-0001-0002-0000-000000000005', 'Joel', 'Tucker', null,  concat(concat(to_char(dateadd(YEAR, -2, current_date), 'YY'), '/'), to_char(dateadd(YEAR, 1, current_date), 'YY')), null,false, 'FISI')
;