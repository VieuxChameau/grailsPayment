package payment

import static org.springframework.http.HttpStatus.CREATED

class TransactionController {

    static allowedMethods = [save: "POST"]

    TransactionService transactionService

    def create() {
        respond new Transaction(params)
    }

    def show(Transaction transaction) {
        respond transaction
    }

    def save(Transaction transaction) {
        try {
            transactionService.makePayment(transaction)

            request.withFormat {
                form multipartForm {
                    flash.message = message(code: 'default.created.message', args: ['Transaction', transaction.id])
                    redirect transaction
                }
                '*' { respond transaction, [status: CREATED] }
            }
        } catch (e) {
            log.error "Error: ${e.message}", e
            flash.message = message(code: 'transaction.created.fail', args: e.getMessage())
            respond transaction.errors, view: 'create'
        }
    }
}
