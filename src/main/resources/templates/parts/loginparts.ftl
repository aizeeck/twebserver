<#macro login path>
    <form action=${path} method="post">
        <div class="form-group">
            <label for="inputEmail">Email address</label>
            <input type="email" name="username" class="form-control" id="inputEmail" aria-describedby="emailHelp" placeholder="Enter email">
            <small id="emailHelp" class="form-text text-muted">Email address will be used only to provide you this service.</small>
        </div>
        <div class="form-group">
            <label for="inputPassword">Password</label>
            <input type="password" name="password" class="form-control" id="inputPassword" placeholder="Password">
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <button type="submit" class="btn btn-primary">Login</button>
    </form>
</#macro>

<#macro logout>
    <form method="post" action="/logout">
        <button type="submit" class="btn btn-primary">Log Out</button>
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
    </form>
</#macro>