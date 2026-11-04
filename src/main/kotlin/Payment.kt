package de.thws.fiw.kotlin.portfolio2

sealed class PaymentResult {
    object Success : PaymentResult()
    data class Failure(val reason: String) : PaymentResult()
}

interface PaymentStrategy {
    fun process(transaction: Transaction): PaymentResult
}

class PaypalPayment : PaymentStrategy {
    override fun process(transaction: Transaction): PaymentResult {
        return if (transaction.amount.amount > 1000.00) {
            val errorMessage = "Paypal Zahlartlimit von 1.000€ überschritten"
            transaction.updateStatus(TransactionStatus.Failed(errorMessage))
            PaymentResult.Failure(errorMessage)
        } else {
            transaction.updateStatus(TransactionStatus.Completed)
            PaymentResult.Success
        }
    }
}

class CreditcardPayment : PaymentStrategy {
    override fun process(transaction: Transaction): PaymentResult {
        return if (transaction.amount.amount > 10000.00) {
            val errorMessage = "KK Zahlartlimit von 10.000€ überschritten"
            transaction.updateStatus(TransactionStatus.Failed(errorMessage))
            PaymentResult.Failure(errorMessage)
        } else {
            transaction.updateStatus(TransactionStatus.Completed)
            PaymentResult.Success
        }
    }
}