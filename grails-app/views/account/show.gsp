<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="payment">
    <g:set var="entityName" value="Account"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body>
<a href="#show-account" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                              default="Skip to content&hellip;"/></a>

<div id="show-account" class="content scaffold-show" role="main">
    <h1><g:message code="default.show.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <f:display bean="account"/>

    Send Transactions
    <table>
        <tr>
            <th>Amount</th>
            <th>To</th>
            <th>Date</th>
        </tr>
        <g:each in="${sendTransactions}" var="sendTransaction">
            <tr>
                <td>${sendTransaction.amount} £</td>
                <td>${sendTransaction.toAccount.name}</td>
                <td>${sendTransaction.transactionDate}</td>
            </tr>
        </g:each>
    </table>

    Received Transactions
    <table>
        <tr>
            <th>Amount</th>
            <th>From</th>
            <th>Date</th>
        </tr>
        <g:each in="${receivedTransactions}" var="receivedTransaction">
            <tr>
                <td>${receivedTransaction.amount} £</td>
                <td>${receivedTransaction.fromAccount.name}</td>
                <td>${receivedTransaction.transactionDate}</td>
            </tr>
        </g:each>
    </table>

    <g:form resource="${account}" method="DELETE">
        <fieldset class="buttons">
            <input class="delete" type="submit"
                   value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                   onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
        </fieldset>
    </g:form>
</div>
</body>
</html>