import{_ as o,c as s,I as r,l as e,a,a4 as n,D as c,o as d}from"./chunks/framework.DiHdKOET.js";const T=JSON.parse('{"title":"ADR-005 dtos as java records","description":"","frontmatter":{},"headers":[],"relativePath":"documentation/architecture/adr/adr005-dtos-as-records.md","filePath":"documentation/architecture/adr/adr005-dtos-as-records.md","lastUpdated":1727611093000}'),i={name:"documentation/architecture/adr/adr005-dtos-as-records.md"},l=e("h1",{id:"adr-005-dtos-as-java-records",tabindex:"-1"},[a("ADR-005 dtos as java records "),e("a",{class:"header-anchor",href:"#adr-005-dtos-as-java-records","aria-label":'Permalink to "ADR-005 dtos as java records"'},"​")],-1),h=e("h2",{id:"status",tabindex:"-1"},[a("Status "),e("a",{class:"header-anchor",href:"#status","aria-label":'Permalink to "Status"'},"​")],-1),u=n('<h2 id="context" tabindex="-1">Context <a class="header-anchor" href="#context" aria-label="Permalink to &quot;Context&quot;">​</a></h2><p>Dto classes are classes that transport data between layers or services. They do not contain any business logic. The sender generates the data, and the receiver processes it. Therefore, write access after the creation is no longer necessary.</p><h2 id="decision" tabindex="-1">Decision <a class="header-anchor" href="#decision" aria-label="Permalink to &quot;Decision&quot;">​</a></h2><p>Records provide this functionality by themselves.</p><p>After creation, only read-access is possible.</p><p>By using the builder pattern, we do not have to use the all-arguments constructor all the time or have to write constructors with a reduced argument list.</p><p>We can use Lombok&#39;s <code>@Builder</code> annotation.</p><h3 id="todo" tabindex="-1">Todo <a class="header-anchor" href="#todo" aria-label="Permalink to &quot;Todo&quot;">​</a></h3><ul><li>Dtos of open pull requests have to be adapted</li><li>At the time of the decision, there were no completed PRs, so no existing code had to be adapted</li></ul><h2 id="consequences" tabindex="-1">Consequences <a class="header-anchor" href="#consequences" aria-label="Permalink to &quot;Consequences&quot;">​</a></h2><p>This way, we reduce unwanted side effects because the properties of a Dto cannot be changed. If objectfactories are used that fill only a part of the object, and other components finalize the object later, the builder must be used.</p>',11);function p(_,m,f,b,v,x){const t=c("adr-status");return d(),s("div",null,[l,h,r(t,{status:"accepted"}),u])}const D=o(i,[["render",p]]);export{T as __pageData,D as default};