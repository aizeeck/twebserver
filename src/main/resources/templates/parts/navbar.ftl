<#include "security.ftl">
<#import "loginparts.ftl" as l>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <a class="navbar-brand" href="/">TWebServer</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>

  <div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav mr-auto">
      <#if (!known)>
        <li class="nav-item">
          <a class="nav-link" href="/login">Login</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="/registration">Regirstration</a>
        </li>
      </#if>
      <#if (known)>
        <li class="nav-item">
          <a class="nav-link" href="/tcontrol">T control</a>
        </li>
        <li class="nav-item">
          <@l.logout/>
        </li>
      </#if>
    </ul>

    <div class="navbar-text mr-3">${name}</div>

  </div>
</nav>