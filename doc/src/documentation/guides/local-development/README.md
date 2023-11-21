# Local Development

For Local Development you have two options. You can either start all the services with or without security features.
For the Spring Applications (Backend & ApiGateway) this is realized via profiles.
For the Vue Application (frontend) this is realized via "modes".
Usually the Frontend is packaged within the ApiGateway and the APiGateway serves it to the user.
This process would be very time-consuming, so we implemented a way to test all the features with the vite dev server.
In the `package.json` in the scripts section we defined `serve` and `dev`, where `serve` starts with the 
`.env.developmentSecurity` file and `dev` starts with the `.env.development` file.

The table below shows the possible startup configurations for the different parts of the application.

| Service     | Startup                                | Behavior                                                                                                                                                                                                                                   |
|-------------|----------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Backend     | profiles=local,db-postgres,no-security | Starts the backend without security features and tries to connect to the postgres-DB provided by the local stack (Docker-Compose)                                                                                                          |
| Backend     | profiles=local,db-postgres             | Starts the backend with security features and tries to connect to the postgres-DB provided by the local stack (Docker-Compose). This needs an API-Gateway with active security to work.                                                    |
| API-Gateway | profiles=local,no-security             | Starts the API-Gateway without security features.                                                                                                                                                                                          |
| API-Gateway | profiles=local                         | Starts the API-Gateway with security features. It tries to connect to the local keycloak provided by the local stack (Docker-Compose).                                                                                                     |
| Frontend    | npm run dev                            | Starts the VUE frontend without security features active. Use this, if you want to develop new functionality fast and without need to login.                                                                                               |
| Frontend    | npm run serve                          | Starts the VUE frontend with security features active. Use this, if you want to test new security restrictions or make sure a specific feature is only shown specific users. When using this you need an API-Gateway with active security. |

If you want to test the frontend with security locally you have to go to the IP of the API-Gateway first (usually `localhost:8082`).
When heading to this url you should be redirected to the keycloak login where you can login and obtain your token.
After logging in you should be able to go back to the frontend dev server (usually hosted on `localhost:8081`) and use
the app normally.

