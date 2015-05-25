package payment

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(TransactionController)
@Mock([Transaction, TransactionService])
class TransactionControllerSpec extends Specification {

    def transactionService = Mock(TransactionService)

    def setup() {
        controller.transactionService = transactionService
    }

    def populateValidParams(params) {
        assert params != null
        params.amount = 42
    }

    void "Test the create action returns the correct model"() {
        when: "The create action is executed"
        controller.create()

        then: "The model is correctly created"
        model.transaction != null
    }

    void "Test the save action correctly persists an instance"() {

        when: "The save action is executed with an invalid instance"
        request.contentType = FORM_CONTENT_TYPE
        request.method = 'POST'
        def transaction = new Transaction()
        transaction.validate()
        controller.save(transaction)

        then: "The create view is rendered again with the correct model"
        model.transaction != null
        view == 'create'

        /*when: "The save action is executed with a valid instance"
        response.reset()
        populateValidParams(params)
        transaction = new Transaction(params)

        controller.save(transaction)

        then: "A redirect is issued to the show action"
        response.redirectedUrl == '/transaction/show/1'
        controller.flash.message != null*/
    }

    void "Test that the show action returns the correct model"() {
        when: "The show action is executed with a null domain"
        controller.show(null)

        then: "A 404 error is returned"
        response.status == 404

        when: "A domain instance is passed to the show action"
        populateValidParams(params)
        def transaction = new Transaction(params)
        controller.show(transaction)

        then: "A model is populated containing the domain instance"
        model.transaction == transaction
    }


}
