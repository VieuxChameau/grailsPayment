package payment

class Account {
    String name;
    String email;
    BigInteger balance = 200

    static constraints = {
        name blank: false, unique: true
        email email: true, blank: false, unique: true
        balance min: BigInteger.ZERO
    }

    boolean hasEnoughMoney(BigInteger amount) {
        return amount <= balance
    }

    def sendMoney(final BigInteger transactionAmount) {
        balance -= transactionAmount
    }

    def receiveMoney(final BigInteger transactionAmount) {
        balance += transactionAmount
    }
}
