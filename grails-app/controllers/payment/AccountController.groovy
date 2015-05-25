package payment

import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class AccountController {

    TransactionService transactionService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond(Account.list(params), model: [accountCount: Account.count()])
    }

    def show(Account account) {
        def fromTransactions = transactionService.sendTransactions(account)
        def toTransactions = transactionService.receveiedTransactions(account)

        def map = [account: account, sendTransactions: fromTransactions, receivedTransactions: toTransactions]
        render(view: 'show', model: map)
    }

    def create() {
        respond(new Account(params))
    }

    @Transactional
    def save(Account account) {
        if (account == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (account.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond account.errors, view: 'create'
            return
        }

        account.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: ['Account', account.id])
                redirect account
            }
            '*' { respond account, [status: CREATED] }
        }
    }


    @Transactional
    def delete(Account account) {

        if (account == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        account.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: ['Account', account.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: ['Account', params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}
