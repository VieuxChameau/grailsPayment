package payment

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(TransactionService)
@Mock([Account, Transaction])
class TransactionServiceSpec extends Specification {
    def Account firstAccount
    def Account secondAccount

    def setup() {
        firstAccount = new Account(name: "John", email: "john@gmail.com")
        secondAccount = new Account(name: "Boris", email: "boris@gmail.com")
        firstAccount.save()
        secondAccount.save()

        def transaction = new Transaction(amount: 42, toAccount: secondAccount, fromAccount: firstAccount)
        transaction.save()
    }

    void "test makePayment"() {
        given: "A Transaction"
        def validTransaction = new Transaction(amount: 42, fromAccount: firstAccount, toAccount: secondAccount)

        when: "the service is called with a valid transaction"
        service.makePayment(validTransaction)

        then: "the accounts balance should be"
        firstAccount.balance == 158
        secondAccount.balance == 242

        //given: "An invalid Transaction"
        def invalidTransaction = new Transaction(amount: 42, fromAccount: firstAccount, toAccount: firstAccount)

        when: "the transaction is made to the same account"
        service.makePayment(invalidTransaction)

        then: "the transaction should be reject"
        def e = thrown(IllegalStateException)
        e.message == "Cannot make transaction to the same account"

        // given: "An invalid Transaction"
        def incorrectTransaction = new Transaction(amount: 500, fromAccount: firstAccount, toAccount: secondAccount)

        when: "the transaction amount is higher than account balance"
        service.makePayment(incorrectTransaction)

        then: "the transaction should be reject"
        def exception = thrown(IllegalStateException)
        exception.message == "Not enough money to make Transaction"
    }

    void "test sendTransactions"() {
        given: "An account"

        when: "the service is called"
        def emptyTransactions = service.sendTransactions(secondAccount)

        then: "Transactions should be empty"
        emptyTransactions.isEmpty()

        when: "the service is called"
        def transactions = service.sendTransactions(firstAccount)

        then: "should have one transaction"
        transactions.size == 1
    }

    void "test receivedTransactions"() {
        given: "An account"

        when: "the service is called"
        def emptyTransactions = service.receveiedTransactions(firstAccount)

        then: "Transactions should be empty"
        emptyTransactions.isEmpty()

        when: "the service is called"
        def transactions = service.receveiedTransactions(secondAccount)

        then: "should have one transaction"
        transactions.size == 1
    }
}
