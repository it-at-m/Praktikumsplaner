# Database Migration

Our database migrations are handled by flyway.
This way we can version our DDL files.
They are automatically applied when the backend starts and there are new, not yet applied, migrations.

For this to work if you have to make changes to the database you have to create a new SQL script in the migration folder.
The migrations folder is located in the backend `src/main/resources/db/migration`.
The scripts name has to follow this convention `V<version_number>__<description_split_with_underscores>`.
The `<version_number>` should be the next available number, so if there is a `V10_desc.sql` then the next script should 
be named `V11_desc.sql`. The version number has to be unique, so if there are several people developing and need to add
SQL scripts then they have to communicate which version numbers they are using.
As this is not guaranteed you have to especially careful when reviewing and merging PRs with changes to the migration 
files. You can see that there is a naming conflict when the file is not `added` but `modified` according to the PR.

SQL migration scripts can NOT be modified after they have been applied to the db. If you find an error later, then you 
have to write a new migration to correct the old one.

Our database is a postgres database so we use the postgres sql dialect in the sql scripts.