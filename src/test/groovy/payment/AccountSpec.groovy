package payment

import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(Account)
class AccountSpec extends Specification {

    void "test hasEnoughMoney"() {
        given: "An account"
        Account account = new Account(name: "John", email: "john@gmail.com")

        when: "the balance is sufficient"
        def hasEnoughMoney = account.hasEnoughMoney(150)

        then: "Should return true"
        hasEnoughMoney

        when: "the balance is not sufficient"
        hasEnoughMoney = account.hasEnoughMoney(242)

        then: "Should return false"
        !hasEnoughMoney
    }

    void "test receiveMoney"() {
        given: "An account"
        Account account = new Account(name: "John", email: "john@gmail.com")

        when: "receiving money"
        account.receiveMoney(150)

        then: "The balance Should increase"
        account.balance == 350
    }

    void "test sencMoney"() {
        given: "An account"
        Account account = new Account(name: "John", email: "john@gmail.com")

        when: "sending money"
        account.sendMoney(150)

        then: "the balance should decrease"
        account.balance == 50
    }
}
