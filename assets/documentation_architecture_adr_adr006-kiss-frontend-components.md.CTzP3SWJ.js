import{_ as a,c as s,j as t,a as o,G as i,a2 as r,B as c,o as d}from"./chunks/framework.cddoPPhG.js";const g=JSON.parse('{"title":"ADR-006 frontend components without complex logic","description":"","frontmatter":{},"headers":[],"relativePath":"documentation/architecture/adr/adr006-kiss-frontend-components.md","filePath":"documentation/architecture/adr/adr006-kiss-frontend-components.md","lastUpdated":1727809191000}'),l={name:"documentation/architecture/adr/adr006-kiss-frontend-components.md"};function p(m,e,h,u,f,b){const n=c("adr-status");return d(),s("div",null,[e[0]||(e[0]=t("h1",{id:"adr-006-frontend-components-without-complex-logic",tabindex:"-1"},[o("ADR-006 frontend components without complex logic "),t("a",{class:"header-anchor",href:"#adr-006-frontend-components-without-complex-logic","aria-label":'Permalink to "ADR-006 frontend components without complex logic"'},"​")],-1)),e[1]||(e[1]=t("h2",{id:"status",tabindex:"-1"},[o("Status "),t("a",{class:"header-anchor",href:"#status","aria-label":'Permalink to "Status"'},"​")],-1)),i(n,{status:"accepted"}),e[2]||(e[2]=r('<h2 id="context" tabindex="-1">Context <a class="header-anchor" href="#context" aria-label="Permalink to &quot;Context&quot;">​</a></h2><p>The term components stands for the vue components, in the form of single-file components, in this context. These contain a template, a script part and styling.</p><h2 id="decision" tabindex="-1">Decision <a class="header-anchor" href="#decision" aria-label="Permalink to &quot;Decision&quot;">​</a></h2><p>There should be no functional logic in components. The code of a component should be limited to data binding. The logic, apart from data binding, is implemented in composables or services.</p><h3 id="todo" tabindex="-1">Todo <a class="header-anchor" href="#todo" aria-label="Permalink to &quot;Todo&quot;">​</a></h3><ul><li>Components of open pull requests have to be adapted</li><li>At the time of the decision, there were no completed PRs, so no existing code had to be adapted</li></ul><h2 id="consequences" tabindex="-1">Consequences <a class="header-anchor" href="#consequences" aria-label="Permalink to &quot;Consequences&quot;">​</a></h2><p>The logic is easier to test because it can be tested independently of the components. The logic may need to be written more generically because it is not coupled to the components.</p>',8))])}const q=a(l,[["render",p]]);export{g as __pageData,q as default};
