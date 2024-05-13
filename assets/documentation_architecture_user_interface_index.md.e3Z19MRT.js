import{_ as e,c as a,o as t,a4 as r}from"./chunks/framework.Bena10jp.js";const f=JSON.parse('{"title":"User interface","description":"","frontmatter":{},"headers":[],"relativePath":"documentation/architecture/user_interface/index.md","filePath":"documentation/architecture/user_interface/index.md","lastUpdated":1715591928000}'),i={name:"documentation/architecture/user_interface/index.md"},o=r('<h1 id="user-interface" tabindex="-1">User interface <a class="header-anchor" href="#user-interface" aria-label="Permalink to &quot;User interface&quot;">​</a></h1><p>Important aspects concerning the user interface will be documented here.</p><h2 id="user-feedback" tabindex="-1">User Feedback <a class="header-anchor" href="#user-feedback" aria-label="Permalink to &quot;User Feedback&quot;">​</a></h2><p>Every time the user interacts with the application in a way that the state changes, the user should be informed about the success or failure of the interaction. Currently, this is done via a snackbar if the interaction was successful, and an error dialog if something went wrong. The error dialog displays the error message sent by the backend so the messages generated in the backend should be comprehensible and really point out what went wrong.</p><h2 id="loading-animations" tabindex="-1">Loading Animations <a class="header-anchor" href="#loading-animations" aria-label="Permalink to &quot;Loading Animations&quot;">​</a></h2><p>Every time a user has to wait for the completion of a request sent to the backend, a loading animation should be displayed. The application distinguishes between two scenarios. Retreiving data from the backend and providing data to the backend. For the first scenario <a href="#skeleton-loader">skeleton loaders</a> are used, for the second scenario a <a href="#circular-loader">circular loader</a> is used.</p><h3 id="skeleton-loader" tabindex="-1">Skeleton Loader <a class="header-anchor" href="#skeleton-loader" aria-label="Permalink to &quot;Skeleton Loader&quot;">​</a></h3><p>The skeleton loader provides a first impression of the site so the user has a slight hint of what to expect when the app is done loading. So the loaders should represent the structure of the site, where will be content and where won&#39;t be content.</p><h3 id="circular-loader" tabindex="-1">Circular Loader <a class="header-anchor" href="#circular-loader" aria-label="Permalink to &quot;Circular Loader&quot;">​</a></h3><p>The circular loader provides the user with the information that his submitted data is being processed at the moment. The loader will be hidden when the request ended.</p><h2 id="form-validation" tabindex="-1">Form Validation <a class="header-anchor" href="#form-validation" aria-label="Permalink to &quot;Form Validation&quot;">​</a></h2><p>All forms that require user input shall validate their fields to ensure valid inputs. This is not only to ensure valid data gets to the backend, but also to provide a good user experience where the user is not able to submit a form until all data meets the requirements. So if the user uses the app as intended, they will get information about invalid data before sending it and getting an error.</p><p>All the rules should match the limitations in the backend and should provide a comprehensible message why the input is invalid.</p>',13),n=[o];function s(d,h,l,c,u,p){return t(),a("div",null,n)}const b=e(i,[["render",s]]);export{f as __pageData,b as default};
