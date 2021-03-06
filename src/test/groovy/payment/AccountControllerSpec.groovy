package payment

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(AccountController)
@Mock([Account, TransactionService])
class AccountControllerSpec extends Specification {
    def setup() {
        controller.transactionService = Mock(TransactionService)
    }

    def populateValidParams(params) {
        assert params != null
        params.name = "John"
        params.email = "John@gmail.com"
    }

    void "Test the index action returns the correct model"() {

        when: "The index action is executed"
        controller.index()

        then: "The model is correct"
        !model.accountList
        model.accountCount == 0
    }

    void "Test the create action returns the correct model"() {
        when: "The create action is executed"
        controller.create()

        then: "The model is correctly created"
        model.account != null
    }

    void "Test the save action correctly persists an instance"() {

        when: "The save action is executed with an invalid instance"
        request.contentType = FORM_CONTENT_TYPE
        request.method = 'POST'
        def account = new Account()
        account.validate()
        controller.save(account)

        then: "The create view is rendered again with the correct model"
        model.account != null
        view == 'create'

        when: "The save action is executed with a valid instance"
        response.reset()
        populateValidParams(params)
        account = new Account(params)

        controller.save(account)

        then: "A redirect is issued to the show action"
        response.redirectedUrl == '/account/show/1'
        controller.flash.message != null
        Account.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when: "A domain instance is passed to the show action"
        populateValidParams(params)
        def account = new Account(params)
        controller.show(account)

        then: "A model is populated containing the domain instance"
        model.account == account
    }


    void "Test that the delete action deletes an instance if it exists"() {
        when: "The delete action is called for a null instance"
        request.contentType = FORM_CONTENT_TYPE
        request.method = 'DELETE'
        controller.delete(null)

        then: "A 404 is returned"
        response.redirectedUrl == '/account/index'
        flash.message != null

        when: "A domain instance is created"
        response.reset()
        populateValidParams(params)
        def account = new Account(params).save(flush: true)

        then: "It exists"
        Account.count() == 1

        when: "The domain instance is passed to the delete action"
        controller.delete(account)

        then: "The instance is deleted"
        Account.count() == 0
        response.redirectedUrl == '/account/index'
        flash.message != null
    }
}
