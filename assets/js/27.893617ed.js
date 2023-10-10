(window.webpackJsonp=window.webpackJsonp||[]).push([[27],{316:function(e,n,t){"use strict";t.r(n);var r=t(3),s=function(e){e.options.__data__block__={mermaid_382ee18a:"graph LR;\n\nsubgraph Praktiumsplaner\n\n    subgraph Dockercontext\n        Frontend\n        Backend\n        Keycloak\n        DB-Keycloak\n        Init-Keycloak\n    \n        Frontend --\x3e|fetch data| Backend\n        Frontend --\x3e|authenticate| Keycloak\n        Backend --\x3e |check permission| Keycloak\n        Keycloak --\x3e|persisting| DB-Keycloak\n        Init-Keycloak --\x3e|configures| Keycloak\n    end\n    \n end\n \n Browser --\x3e|localhost:8080| Frontend\n"}},a=Object(r.a)({},(function(){var e=this,n=e._self._c;return n("ContentSlotsDistributor",{attrs:{"slot-key":e.$parent.slotKey}},[n("h1",{attrs:{id:"technisches-setup"}},[n("a",{staticClass:"header-anchor",attrs:{href:"#technisches-setup"}},[e._v("#")]),e._v(" Technisches Setup")]),e._v(" "),n("p",[e._v("Im Folgenden wird das technische Setup beschrieben.")]),e._v(" "),n("p",[n("strong",[e._v("Inhaltsverzeichnis")])]),e._v(" "),n("p"),n("div",{staticClass:"table-of-contents"},[n("ul",[n("li",[n("a",{attrs:{href:"#lokale-infrastruktur"}},[e._v("lokale Infrastruktur")]),n("ul",[n("li",[n("a",{attrs:{href:"#voraussetzungen"}},[e._v("Voraussetzungen")])]),n("li",[n("a",{attrs:{href:"#verwendung-der-anwendung-mittels-docker"}},[e._v("Verwendung der Anwendung mittels Docker")])]),n("li",[n("a",{attrs:{href:"#frontendentwicklung-in-progress"}},[e._v("Frontendentwicklung (in Progress)")])]),n("li",[n("a",{attrs:{href:"#backendentwicklung-in-progress"}},[e._v("Backendentwicklung (in Progress)")])])])])])]),n("p"),e._v(" "),n("h2",{attrs:{id:"lokale-infrastruktur"}},[n("a",{staticClass:"header-anchor",attrs:{href:"#lokale-infrastruktur"}},[e._v("#")]),e._v(" lokale Infrastruktur")]),e._v(" "),n("p",[e._v("Für die Entwicklung wird eine lokale Infrastruktur im stack Ordner bereitgestellt.")]),e._v(" "),n("h3",{attrs:{id:"voraussetzungen"}},[n("a",{staticClass:"header-anchor",attrs:{href:"#voraussetzungen"}},[e._v("#")]),e._v(" Voraussetzungen")]),e._v(" "),n("p",[e._v("Folgende Programme / Tools müssen installiert sein:")]),e._v(" "),n("ul",[n("li",[e._v("Docker (zur Nutzung der bereitgestellten Images)")])]),e._v(" "),n("h3",{attrs:{id:"verwendung-der-anwendung-mittels-docker"}},[n("a",{staticClass:"header-anchor",attrs:{href:"#verwendung-der-anwendung-mittels-docker"}},[e._v("#")]),e._v(" Verwendung der Anwendung mittels Docker")]),e._v(" "),n("p",[e._v("Im Ordner "),n("code",[e._v("stack")]),e._v(" gibt es ein "),n("code",[e._v("docker compose")]),e._v("-File über dass alle notwendigen Container aufgebaut werden.\nDazu den Befehl "),n("code",[e._v("docker compose --profile frontend up -d")]),e._v(" verwenden.")]),e._v(" "),n("Mermaid",{attrs:{id:"mermaid_382ee18a",graph:e.$dataBlock.mermaid_382ee18a}}),n("p",[n("strong",[e._v("⚠ Hinweis")])]),e._v(" "),n("p",[e._v("Beim ersten Start wird das Frontend nicht erfolgreich mit starten. Das liegt daran dass der Keycloak noch beim Start\nfrom Frontend noch nicht vollständig eingerichtet ist. Die Einrichtung vom Keycloak ist abgeschlossen wenn der Container\n`init-keycloak``wieder gestoppt ist. Danach kann das Frontend gestartet werden.")]),e._v(" "),n("p",[e._v("Sobald alle Services, ausgenommen vom "),n("code",[e._v("init-*")]),e._v(" gestartet sind kann auf die Anwendung via http://localhost:8080\nzugegriffen werden. Zur Authentifizierung den Benutzer "),n("code",[e._v("testuser")]),e._v(" mit dem Passwort "),n("code",[e._v("test")]),e._v(" verwenden.")]),e._v(" "),n("p",[n("strong",[e._v("⚠ Proxyhinweis")])]),e._v(" "),n("p",[e._v("Wenn im Browser ein Proxy eingerichtet ist bitte darauf achten dass dieser nicht "),n("code",[e._v("kubernetes.docker.internal")]),e._v(" auflöst.")]),e._v(" "),n("h3",{attrs:{id:"frontendentwicklung-in-progress"}},[n("a",{staticClass:"header-anchor",attrs:{href:"#frontendentwicklung-in-progress"}},[e._v("#")]),e._v(" Frontendentwicklung (in Progress)")]),e._v(" "),n("p",[n("em",[e._v("TBD")])]),e._v(" "),n("h3",{attrs:{id:"backendentwicklung-in-progress"}},[n("a",{staticClass:"header-anchor",attrs:{href:"#backendentwicklung-in-progress"}},[e._v("#")]),e._v(" Backendentwicklung (in Progress)")]),e._v(" "),n("p",[n("em",[e._v("TBD")])])],1)}),[],!1,null,null,null);"function"==typeof s&&s(a);n.default=a.exports}}]);