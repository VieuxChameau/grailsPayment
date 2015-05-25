package payment

class Transaction {
    BigInteger amount
    Date transactionDate = new Date()
    Account fromAccount
    Account toAccount

    static constraints = {
        amount min: BigInteger.ZERO
    }
}
