import{_ as d,D as c,c as l,b as s,w as e,a7 as n,a4 as a,o as t,I as r,a as i}from"./chunks/framework.Bena10jp.js";const w=JSON.parse('{"title":"Local Development","description":"","frontmatter":{},"headers":[],"relativePath":"documentation/guides/local-development/index.md","filePath":"documentation/guides/local-development/index.md","lastUpdated":1715591928000}'),p={name:"documentation/guides/local-development/index.md"},h=a(`<h1 id="local-development" tabindex="-1">Local Development <a class="header-anchor" href="#local-development" aria-label="Permalink to &quot;Local Development&quot;">​</a></h1><p>This page should make it easier to start developing locally.</p><h2 id="prerequisites" tabindex="-1">Prerequisites <a class="header-anchor" href="#prerequisites" aria-label="Permalink to &quot;Prerequisites&quot;">​</a></h2><p>The following programs / tools must be installed:</p><ul><li>Docker (to use the provided images)</li></ul><h2 id="stack-folder" tabindex="-1">Stack folder <a class="header-anchor" href="#stack-folder" aria-label="Permalink to &quot;Stack folder&quot;">​</a></h2><p>The Repository contains a <code>stack</code> folder. In this stack folder you can find a docker compose file. This docker compose file is configured to deploy all the necessary services for the application. There is also a folder with keycloak migrations and <code>.env</code> files for the different services.</p><h3 id="docker-compose-file" tabindex="-1">Docker Compose File <a class="header-anchor" href="#docker-compose-file" aria-label="Permalink to &quot;Docker Compose File&quot;">​</a></h3><table><thead><tr><th>Service</th><th>Description</th></tr></thead><tbody><tr><td>pp-frontend</td><td>The frontend if deployed</td></tr><tr><td>pp-backend</td><td>The backend if deployed</td></tr><tr><td>postgres-backend</td><td>Postgres Database for the application</td></tr><tr><td>keycloak</td><td>Local SSO service</td></tr><tr><td>init-keycloak</td><td>Keycloak initializer to apply the keycloak migrations</td></tr><tr><td>postgres-keycloak</td><td>Postgres Database for the keycloak service</td></tr><tr><td>postgres-pg-admin</td><td>PgAdmin to access the database</td></tr><tr><td>mail-maildev</td><td>Local Mail-Client</td></tr><tr><td>mail-mailpit</td><td>Local SMTP-Server</td></tr></tbody></table><p>Use docker compose to start the infrastructure</p><div class="language- vp-adaptive-theme"><button title="Copy Code" class="copy"></button><span class="lang"></span><pre class="shiki shiki-themes github-light github-dark vp-code"><code><span class="line"><span># Starts the infrastructure services like keycloak and db for backend</span></span>
<span class="line"><span>docker compose up -d</span></span>
<span class="line"><span></span></span>
<span class="line"><span># Starts all services</span></span>
<span class="line"><span>docker compose --profile full up -d</span></span>
<span class="line"><span></span></span>
<span class="line"><span># Includes the backend service on startup</span></span>
<span class="line"><span>docker compose --profile backend up -d</span></span>
<span class="line"><span></span></span>
<span class="line"><span># Includes the frontend service on startup</span></span>
<span class="line"><span>docker compose --profile frontend up -d</span></span></code></pre></div>`,11),u=a('<p>The frontend and Backend are only started if docker compose is run with the profile full. If you want to develop locally it is sugessted to start docker compose without this profile and start those parts via your IDE. The <code>.env</code> files for the frontend and backend are used when these services are deployed. If you start them via your IDE they are not used.</p><p><strong>⚠ Note</strong></p><p>The frontend will not start successfully at the first start. This is because the keycloak is not yet fully set up when the frontend is started. The setup of the keycloak is complete when the container <code>init-keycloak</code> is stopped again. The frontend can then be started afterward.</p><p>As soon as all services except <code>init-*</code> have been started, the application can be accessed via <a href="http://localhost:8080" target="_blank" rel="noreferrer">http://localhost:8080</a>. For authentication, use the user <code>testleitung</code> with the password <code>test</code>.</p><p><strong>⚠ Proxy note</strong></p><p>If a proxy is set up in the browser, please make sure that it does not resolve <code>kubernetes.docker.internal</code>.</p><h3 id="keycloak" tabindex="-1">Keycloak <a class="header-anchor" href="#keycloak" aria-label="Permalink to &quot;Keycloak&quot;">​</a></h3><p>An admin user and a test user are created by <code>init-keycloak</code>. You can change the configuration via the keycloak ui.</p><h4 id="configuration-migration" tabindex="-1">configuration migration <a class="header-anchor" href="#configuration-migration" aria-label="Permalink to &quot;configuration migration&quot;">​</a></h4><p>Realm, client user and other configuration should be done by the migration client. Its config files are located in <code>keycloak\\migration</code>. The main file is <code>keycloak-changelog.yml</code>. It contains the list of migration files that should be applied. For more information check <a href="https://mayope.github.io/keycloakmigration/migrations/client/" target="_blank" rel="noreferrer">here</a>.</p><h3 id="postgres" tabindex="-1">Postgres <a class="header-anchor" href="#postgres" aria-label="Permalink to &quot;Postgres&quot;">​</a></h3><h4 id="connection-to-postgresql-db-in-docker" tabindex="-1">Connection to Postgresql-DB in Docker <a class="header-anchor" href="#connection-to-postgresql-db-in-docker" aria-label="Permalink to &quot;Connection to Postgresql-DB in Docker&quot;">​</a></h4>',12),k=a('<p>There is a database for the backend in the infrastructure provided. The <code>db-postgres</code> profile must be used to connect the backend to it during development. It is configured so that a connection to the infrastructure is established by default.</p><h2 id="local-development-1" tabindex="-1">Local Development <a class="header-anchor" href="#local-development-1" aria-label="Permalink to &quot;Local Development&quot;">​</a></h2><p>For Local Development you have two options. You can either start all the services with or without security features. For the Spring Applications (Backend &amp; ApiGateway) this is realized via profiles. For the Vue Application (frontend) this is realized via &quot;modes&quot;. Usually the Frontend is packaged within the ApiGateway and the ApiGateway serves it to the user. This process would be very time-consuming, so we implemented a way to test all the features with the vite dev server. In the <code>package.json</code> in the scripts section we defined <code>security</code> and <code>serve</code>, where <code>security</code> starts with the <code>.env.developmentSecurity</code> file and <code>serve</code> starts with the <code>.env.development</code> file.</p><p>The table below shows the possible startup configurations for the different parts of the application.</p><table><thead><tr><th>Service</th><th>Startup</th><th>Behavior</th></tr></thead><tbody><tr><td>Backend</td><td>profiles=local,db-postgres,no-security</td><td>Starts the backend without security features and tries to connect to the postgres-DB provided by the local stack (Docker-Compose)</td></tr><tr><td>Backend</td><td>profiles=local,db-postgres</td><td>Starts the backend with security features and tries to connect to the postgres-DB provided by the local stack (Docker-Compose). This needs an API-Gateway with active security to work.</td></tr><tr><td>API-Gateway</td><td>profiles=local,no-security</td><td>Starts the API-Gateway without security features.</td></tr><tr><td>API-Gateway</td><td>profiles=local</td><td>Starts the API-Gateway with security features. It tries to connect to the local keycloak provided by the local stack (Docker-Compose).</td></tr><tr><td>Frontend</td><td>npm run serve</td><td>Starts the VUE frontend without security features active. Use this, if you want to develop new functionality fast and without need to login.</td></tr><tr><td>Frontend</td><td>npm run security</td><td>Starts the VUE frontend with security features active. Use this, if you want to test new security restrictions or make sure a specific feature is only shown specific users. When using this you need an API-Gateway with active security.</td></tr></tbody></table><p>If you want to test the frontend with security locally you have to go to the IP of the API-Gateway first (usually <code>localhost:8082</code>). When heading to this url you should be redirected to the keycloak login where you can login and obtain your token. After logging in you should be able to go back to the frontend dev server (usually hosted on <code>localhost:8081</code>) and use the app normally.</p>',6);function f(m,y,g,b,v,A){const o=c("Mermaid");return t(),l("div",null,[h,(t(),s(n,null,{default:e(()=>[r(o,{id:"mermaid-118",class:"mermaid my-class",graph:"graph%20LR%3B%0A%0Asubgraph%20Praktiumsplaner%0A%0A%20%20%20%20subgraph%20Dockercontext%0A%20%20%20%20%20%20%20%20Frontend%0A%20%20%20%20%20%20%20%20Backend%0A%20%20%20%20%20%20%20%20DBBackend%5BDB-Backend%5D%0A%20%20%20%20%20%20%20%20Keycloak%0A%20%20%20%20%20%20%20%20DB-Keycloak%0A%20%20%20%20%20%20%20%20Init-Keycloak%0A%20%20%20%20%0A%20%20%20%20%20%20%20%20Frontend%20--%3E%7Cfetch%20data%7C%20Backend%0A%20%20%20%20%20%20%20%20Backend%20--%3E%20%7Ccheck%20permission%7C%20Keycloak%0A%20%20%20%20%20%20%20%20Backend%20--%3E%7Cpersisting%7C%20DBBackend%0A%20%20%20%20%20%20%20%20Frontend%20--%3E%7Cauthenticate%7C%20Keycloak%0A%20%20%20%20%20%20%20%20Keycloak%20--%3E%7Cpersisting%7C%20DB-Keycloak%0A%20%20%20%20%20%20%20%20Init-Keycloak%20--%3E%7Cconfigures%7C%20Keycloak%0A%20%20%20%20end%0A%20%20%20%20%0A%20end%0A%20%0A%20Browser%20--%3E%7Clocalhost%3A8080%7C%20Frontend%0A"})]),fallback:e(()=>[i(" Loading... ")]),_:1})),u,(t(),s(n,null,{default:e(()=>[r(o,{id:"mermaid-155",class:"mermaid my-class",graph:"graph%20LR%0A%0Asubgraph%20Praktiumsplaner%0A%0A%20%20%20%20subgraph%20IDE%0A%20%20%20%20%20%20%20%20IDEBackend%5BBackend%5D%0A%20%20%20%20end%0A%0A%20%20%20%20subgraph%20Dockercontext%0A%20%20%20%20%20%20%20%20DBBackend%5BDB%20Backend%5D%0A%20%20%20%20end%0A%0A%20%20%20%20IDEBackend%20--%3E%7Clocalhost%3A5432%7C%20DBBackend%0A%20%20%20%20%0A%20end%0A"})]),fallback:e(()=>[i(" Loading... ")]),_:1})),k])}const D=d(p,[["render",f]]);export{w as __pageData,D as default};
