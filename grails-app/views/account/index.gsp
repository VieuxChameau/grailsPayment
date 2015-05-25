<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="payment">
        <g:set var="entityName" value="Account" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#list-account" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div id="list-account" class="content scaffold-list" role="main">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <f:table collection="${accountList}" />

            <div class="pagination">
                <g:paginate total="${accountCount ?: 0}" />
            </div>
        </div>
    </body>
</html>