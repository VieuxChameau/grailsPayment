package payment

import grails.transaction.Transactional

@Transactional(readOnly = true)
class TransactionService {
    @Transactional
    def makePayment(Transaction transaction) {
        validateTransaction(transaction)

        def fromAccount = transaction.fromAccount
        def toAccount = transaction.toAccount
        def transactionAmount = transaction.amount

        fromAccount.sendMoney(transactionAmount)
        toAccount.receiveMoney(transactionAmount)

        fromAccount.save flush: true
        toAccount.save flush: true
        transaction.save flush: true
    }

    private void validateTransaction(Transaction transaction) {
        def fromAccount = transaction.fromAccount
        if (fromAccount == transaction.toAccount) {
            throw new IllegalStateException("Cannot make transaction to the same account")
        }

        def transactionAmount = transaction.amount
        if (!fromAccount.hasEnoughMoney(transactionAmount)) {
            throw new IllegalStateException("Not enough money to make Transaction")
        }
    }

    def sendTransactions(Account account) {
        return Transaction.findAllByFromAccount(account, [sort: 'transactionDate', order: 'desc'])
    }

    def receveiedTransactions(Account account) {
        return Transaction.findAllByToAccount(account, [sort: 'transactionDate', order: 'desc'])
    }
}
