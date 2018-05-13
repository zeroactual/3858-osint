<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,shrink-to-fit=no">
    <title>Dashboard</title>
    <style>
        .nav-form{display:block;line-height:65px;min-height:65px;padding:0 15px}#loader{transition:all .3s ease-in-out;opacity:1;visibility:visible;position:fixed;height:100vh;width:100%;background:#fff;z-index:90000}#loader.fadeOut{opacity:0;visibility:hidden}.spinner{width:40px;height:40px;position:absolute;top:calc(50% - 20px);left:calc(50% - 20px);background-color:#333;border-radius:100%;-webkit-animation:sk-scaleout 1s infinite ease-in-out;animation:sk-scaleout 1s infinite ease-in-out}@-webkit-keyframes sk-scaleout{0%{-webkit-transform:scale(0)}100%{-webkit-transform:scale(1);opacity:0}}@keyframes sk-scaleout{0%{-webkit-transform:scale(0);transform:scale(0)}100%{-webkit-transform:scale(1);transform:scale(1);opacity:0}}</style><link href="style.css" rel="stylesheet"></head><body class="app"><div id="loader"><div class="spinner"></div></div><script>window.addEventListener('load', () => {
    const loader = document.getElementById('loader');
setTimeout(() => {
    loader.classList.add('fadeOut');
}, 300);
});</script>
<div><div class="page-container">
    <div class="header navbar"><div class="header-container">
        <ul class="nav-left"><li><span class="title">OSINT</span>
        </li></ul><ul class="nav-right"><li class="notifications"><form method="post" , action="/toggle-index" , class="nav-form"><button type="submit" class="btn cur-p btn-primary">Index Repos</button></form></li><li class="notifications"><form method="post" , action="/toggle-mapping" class="nav-form"><button type="submit" class="btn cur-p btn-primary">Index Locations</button></form></li></ul></div></div><main class="main-content bgc-grey-100"><div id="mainContent"><div class="row gap-20 masonry pos-r"><div class="masonry-sizer col-md-6"></div><div class="masonry-item w-100"><div class="row gap-20"><div class="col-md-3"><div class="layers bd bgc-white p-20"><div class="layer w-100 mB-10"><h6 class="lh-1">Repos</h6></div><div class="layer w-100"><div class="peers ai-sb fxw-nw"><div class="peer peer-greed"><span id="sparklinedash"></span></div><div class="peer"><span class="d-ib lh-0 va-m fw-600 bdrs-10em pX-15 pY-15 bgc-green-50 c-green-500">${repo_count}</span></div></div></div></div></div><div class="col-md-3"><div class="layers bd bgc-white p-20"><div class="layer w-100 mB-10"><h6 class="lh-1">Users / Orgs</h6></div><div class="layer w-100"><div class="peers ai-sb fxw-nw"><div class="peer peer-greed"><span id="sparklinedash2"></span></div><div class="peer"><span class="d-ib lh-0 va-m fw-600 bdrs-10em pX-15 pY-15 bgc-red-50 c-red-500">${user_count}</span></div></div></div></div></div><div class="col-md-3"><div class="layers bd bgc-white p-20"><div class="layer w-100 mB-10"><h6 class="lh-1">Languages</h6></div><div class="layer w-100"><div class="peers ai-sb fxw-nw"><div class="peer peer-greed"><span id="sparklinedash3"></span></div><div class="peer"><span class="d-ib lh-0 va-m fw-600 bdrs-10em pX-15 pY-15 bgc-purple-50 c-purple-500">${language_count}</span></div></div></div></div></div><div class="col-md-3"><div class="layers bd bgc-white p-20"><div class="layer w-100 mB-10"><h6 class="lh-1">Countries</h6></div><div class="layer w-100"><div class="peers ai-sb fxw-nw"><div class="peer peer-greed"><span id="sparklinedash4"></span></div><div class="peer"><span class="d-ib lh-0 va-m fw-600 bdrs-10em pX-15 pY-15 bgc-blue-50 c-blue-500">${country_count}</span></div></div></div></div></div></div></div><div class="masonry-item col-12"><div class="bd bgc-white"><div class="peers fxw-nw@lg+ ai-s">
    <div class="peer peer-greed w-70p@lg+ w-100@lg- p-20"><div class="layers"><div class="layer w-100 mB-10"><h6 class="lh-1">Location Map</h6>
    <#include "worldmap.ftl"></div>
        <div class="layer w-100"></div></div></div>
    </div></div></div>




    <div class="masonry-item col-md-6">
        <div class="bd bgc-white">
            <div class="layers">
                <div class="layer w-100">
                    <div class="bgc-light-blue-500 c-white p-20">
                        <div class="peers ai-c jc-sb gap-40">
                            <div class="peer peer-greed">
                                <h5>Repos</h5>
                            </div>
                        </div>
                    </div>
                    <div class="table-responsive p-20">
                        <table class="table">
                            <thead>
                            <tr>
                                <th class="bdwT-0">Country</th>
                                <th class="bdwT-0">Lang</th>
                                <th class="bdwT-0">Repos</th>
                            </tr>
                            </thead>
                            <tbody>
                            <#list language_country as country>
                            <tr>
                                <td class="fw-600">${country.country}</td>
                                <td>
                                    ${country.language}
                                </td>
                                <td>${country.repos}</td>
                            </tr>
                            </#list>

                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="masonry-item col-md-6">
        <div class="bd bgc-white">
            <div class="layers">
                <div class="layer w-100">
                    <div class="bgc-light-blue-500 c-white p-20">
                        <div class="peers ai-c jc-sb gap-40">
                            <div class="peer peer-greed">
                                <h5>Users</h5>
                            </div>
                        </div>
                    </div>
                    <div class="table-responsive p-20">
                        <table class="table">
                            <thead>
                            <tr>
                                <th class="bdwT-0">Name</th>
                                <th class="bdwT-0">Country</th>
                                <th class="bdwT-0">Lang</th>
                                <th class="bdwT-0">Repos</th>
                            </tr>
                            </thead>
                            <tbody>
                            <#list users as user>
                            <tr>
                                <td class="fw-600">${user.name}</td>
                                <td>${user.country}</td>
                                <td>
                                    ${user.langs}
                                </td>
                                <td>${user.repos}</td>
                            </tr>
                            </#list>

                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
    </div>
</div>
</div>
</div>
<footer class="bdT ta-c p-30 lh-0 fsz-sm c-grey-600"></footer><script type="text/javascript" src="vendor.js"></script><script type="text/javascript" src="bundle.js"></script></body></html>