<#import "parts/common.ftl" as c>
<@c.page "T login">

<form action="/tcontrol" method="post">
    <div><label> User Name : <input type="email" name="email"/> </label></div>
    <div><label> Password: <input type="password" name="password"/> </label></div>
    <input type="hidden" name="_csrf" value="${_csrf.token}" />
    <div><input type="submit" value="Sign Into Tesy"/></div>
</form>
</@c.page>