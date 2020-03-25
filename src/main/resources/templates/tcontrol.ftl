<#import "parts/common.ftl" as c>
<@c.page "T control">
    <div>
        <a href="/device/alldevconsumptionhourly">Show hourly consumption (all devices)</a>
    </div>
    <div>
        <a href="/device/alldevconsumptiondaily">Show daily consumption (all devices)</a>
    </div>
    <div>
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

<a href="/tcontrol/sync">Start sync</a>

</@c.page>