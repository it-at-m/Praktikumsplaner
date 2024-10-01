import{_ as a,c as t,a2 as i,o}from"./chunks/framework.cddoPPhG.js";const u=JSON.parse('{"title":"Database Migration","description":"","frontmatter":{},"headers":[],"relativePath":"documentation/guides/database-migration/index.md","filePath":"documentation/guides/database-migration/index.md","lastUpdated":1727809191000}'),n={name:"documentation/guides/database-migration/index.md"};function s(r,e,d,c,h,l){return o(),t("div",null,e[0]||(e[0]=[i('<h1 id="database-migration" tabindex="-1">Database Migration <a class="header-anchor" href="#database-migration" aria-label="Permalink to &quot;Database Migration&quot;">​</a></h1><p>Our database migrations are handled by Flyway. This way, we can version our DDL files. They are automatically applied when the backend starts, and there are new, not yet applied, migrations.</p><p>For this to work, if you have to make changes to the database, you have to create a new SQL script in the migration folder. The migrations folder is located in the backend <code>src/main/resources/db/migration</code>. The scripts name has to follow this convention: <code>V&lt;version_number&gt;__&lt;description_split_with_underscores&gt;</code>. The <code>&lt;version_number&gt;</code> should be the next available number, so if there is a <code>V10__desc.sql</code> then the next script should be named <code>V11__desc.sql</code>. The version number has to be unique, so if there are several people developing and need to add SQL scripts, then they have to communicate which version numbers they are using. As this is not guaranteed, you have to be especially careful when reviewing and merging PRs with changes to the migration files. You can see that there is a naming conflict when the file is not <code>added</code> but <code>modified</code> according to the PR.</p><p>SQL migration scripts can NOT be modified after they have been applied to the database. If you find an error later, then you have to write a new migration to correct the old one.</p><p>Our database is a PostgreSQL database, so we use the PostgreSQL dialect in the SQL scripts.</p>',5)]))}const p=a(n,[["render",s]]);export{u as __pageData,p as default};
