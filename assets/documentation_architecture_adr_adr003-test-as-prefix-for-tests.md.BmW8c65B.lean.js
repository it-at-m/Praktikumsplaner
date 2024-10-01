import{_ as o,c as r,j as t,a,G as i,a2 as d,B as n,o as c}from"./chunks/framework.cddoPPhG.js";const q=JSON.parse('{"title":"ADR-003 test as a prefix for test case methods","description":"","frontmatter":{},"headers":[],"relativePath":"documentation/architecture/adr/adr003-test-as-prefix-for-tests.md","filePath":"documentation/architecture/adr/adr003-test-as-prefix-for-tests.md","lastUpdated":1727809191000}'),h={name:"documentation/architecture/adr/adr003-test-as-prefix-for-tests.md"};function l(p,e,f,m,u,x){const s=n("adr-status");return c(),r("div",null,[e[0]||(e[0]=t("h1",{id:"adr-003-test-as-a-prefix-for-test-case-methods",tabindex:"-1"},[a("ADR-003 "),t("code",null,"test"),a(" as a prefix for test case methods "),t("a",{class:"header-anchor",href:"#adr-003-test-as-a-prefix-for-test-case-methods","aria-label":'Permalink to "ADR-003 `test` as a prefix for test case methods"'},"​")],-1)),e[1]||(e[1]=t("h2",{id:"status",tabindex:"-1"},[a("Status "),t("a",{class:"header-anchor",href:"#status","aria-label":'Permalink to "Status"'},"​")],-1)),i(s,{status:"accepted"}),e[2]||(e[2]=d('<h2 id="context" tabindex="-1">Context <a class="header-anchor" href="#context" aria-label="Permalink to &quot;Context&quot;">​</a></h2><p>The code should have a certain uniformity. This also applies to the tests. Experiences show that there were different spellings for the method names of the test cases. Some test methods started with <code>test</code> others did not.</p><h2 id="decision" tabindex="-1">Decision <a class="header-anchor" href="#decision" aria-label="Permalink to &quot;Decision&quot;">​</a></h2><p>All test methods start with the prefix <code>test</code>.</p><p>Corresponding to lowerCamelCase, the method name continues afterward with an uppercase letter.</p><h3 id="todo" tabindex="-1">Todo <a class="header-anchor" href="#todo" aria-label="Permalink to &quot;Todo&quot;">​</a></h3><ul><li>The code for existing PRs has to be adapted</li><li>At the time of the decision, there were no completed PRs, so no existing code had to be adapted</li></ul><h2 id="consequences" tabindex="-1">Consequences <a class="header-anchor" href="#consequences" aria-label="Permalink to &quot;Consequences&quot;">​</a></h2><p>Other than code uniformity and a slightly longer method name, no implications are noticeable.</p>',9))])}const P=o(h,[["render",l]]);export{q as __pageData,P as default};
