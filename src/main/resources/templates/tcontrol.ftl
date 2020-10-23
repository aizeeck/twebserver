<#import "parts/common.ftl" as c>
<@c.page "T control">
    <div>
        <svg class="bi bi-bar-chart-fill" width="1em" height="1em" viewBox="0 0 16 16" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
            <rect width="4" height="5" x="1" y="10" rx="1"/>
            <rect width="4" height="9" x="6" y="6" rx="1"/>
            <rect width="4" height="14" x="11" y="1" rx="1"/>
        </svg>
        <a href="/device/alldevconsumptionhourly">Show hourly consumption (all devices)</a>
    </div>
    <div>
        <svg class="bi bi-bar-chart-fill" width="1em" height="1em" viewBox="0 0 16 16" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
            <rect width="4" height="5" x="1" y="10" rx="1"/>
            <rect width="4" height="9" x="6" y="6" rx="1"/>
            <rect width="4" height="14" x="11" y="1" rx="1"/>
        </svg>
        <a href="/device/alldevconsumptiondaily">Show daily consumption (all devices)</a>
    </div>
    <div>
        <svg class="bi bi-bar-chart-fill" width="1em" height="1em" viewBox="0 0 16 16" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
            <rect width="4" height="5" x="1" y="10" rx="1"/>
            <rect width="4" height="9" x="6" y="6" rx="1"/>
            <rect width="4" height="14" x="11" y="1" rx="1"/>
        </svg>
        <a href="/device/alldevconsumptionmonthly">Show monthly consumption (all devices)</a>
    </div>
    <#list devices as d>
    <div class="card text-white bg-dark mb-3" style="max-width: 30rem;">
        <div class="card-header">
            <a href="/device/hourlyconsumption/${d.gettId()}" class="card-link">${d.getName()} - hourly</a>
            <a href="/device/dailyconsumption/${d.gettId()}"    class="card-link">${d.getName()} - daily</a>
        </div>
        <div class="card-body">
            <h5 class="card-title">${d.getHeater_state()} - ${d.getMode()}</h5>
            <p class="card-text">
            <table class="table">
                <thead>
                    <tr>
                        <th scope="col">Ref gradus</th>
                        <th scope="col">Gradus</th>
                        <th scope="col">Date</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>${d.getRef_gradus()}</td>
                        <td>${d.getGradus()}</td>
                        <td>${d.getObsDate()?string["dd.MM.yyyy / HH:mm:ss"]}</td>
                    </tr>
                </tbody>
            </table>
            </p>
        </div>
    </div>
</#list>

<div>
    <svg class="bi bi-server" width="1em" height="1em" viewBox="0 0 16 16" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
        <path d="M13 2c0-1.105-2.239-2-5-2S3 .895 3 2s2.239 2 5 2 5-.895 5-2z"/>
        <path d="M13 3.75c-.322.24-.698.435-1.093.593C10.857 4.763 9.475 5 8 5s-2.857-.237-3.907-.657A4.881 4.881 0 013 3.75V6c0 1.105 2.239 2 5 2s5-.895 5-2V3.75z"/>
        <path d="M13 7.75c-.322.24-.698.435-1.093.593C10.857 8.763 9.475 9 8 9s-2.857-.237-3.907-.657A4.881 4.881 0 013 7.75V10c0 1.105 2.239 2 5 2s5-.895 5-2V7.75z"/>
        <path d="M13 11.75c-.322.24-.698.435-1.093.593-1.05.42-2.432.657-3.907.657s-2.857-.237-3.907-.657A4.883 4.883 0 013 11.75V14c0 1.105 2.239 2 5 2s5-.895 5-2v-2.25z"/>
    </svg>
    <a href="/tcontrol/sync">Start sync</a>
</div>

</@c.page>