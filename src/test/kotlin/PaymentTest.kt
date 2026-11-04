package de.thws.fiw.kotlin.portfolio2

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

class PaymentTest {
    private val validID = TransactionId.create("ABC-123")!!
    private val validSender = Email.create("sender@thws.de")!!
    private val validReceiver = Email.create("receiver@thws.de")!!

    @Test
    fun testPaypalPaymentSuccess() {
        val transaction = Transaction(validID, Money.create(500.00)!!, validSender, validReceiver)
        val provider = PaypalPayment()
        val result = provider.process(transaction)

        assertEquals(PaymentResult.Success, result)
    }

    @Test
    fun testPaypalPaymentFailureWhenLimitExceeded() {
        val transaction = Transaction(validID, Money.create(1500.00)!!, validSender, validReceiver)
        val provider = PaypalPayment()
        val result = provider.process(transaction)

        assertTrue(result is PaymentResult.Failure)
        assertEquals("Paypal Zahlartlimit von 1.000€ überschritten", (result as PaymentResult.Failure).reason)
    }

    @Test
    fun testCreditcardPaymentSuccess() {
        val transaction = Transaction(validID, Money.create(5000.00)!!, validSender, validReceiver)
        val provider = CreditcardPayment()
        val result = provider.process(transaction)

        assertEquals(PaymentResult.Success, result)
    }

    @Test
    fun testCreditcardPaymentFailureWhenLimitExceeded() {
        val transaction = Transaction(validID, Money.create(15000.00)!!, validSender, validReceiver)
        val provider = CreditcardPayment()
        val result = provider.process(transaction)

        assertTrue(result is PaymentResult.Failure)
        assertEquals("KK Zahlartlimit von 10.000€ überschritten", (result as PaymentResult.Failure).reason)
    }

    @Test
    fun testPaymentResultsEvalutionWithWhen() {
        val successResult : PaymentResult = PaymentResult.Success
        val failureResult : PaymentResult = PaymentResult.Failure("Fehler")
        val successMessage = when (successResult) {
            is PaymentResult.Success -> "Erfolgreich"
            is PaymentResult.Failure -> "Fehlgeschlagen"
        }
        assertEquals("Erfolgreich", successMessage)

        val failureMessage = when (failureResult) {
            is PaymentResult.Success -> "Erfolgreich"
            is PaymentResult.Failure -> "Fehlgeschlagen: ${failureResult.reason}"
        }
        assertEquals("Fehlgeschlagen: Fehler", failureMessage)
    }

    @Test
    fun testTransactionStatusChangesToCompletedOnSuccess() {
        val transaction = Transaction(validID, Money.create(100.00)!!, validSender, validReceiver)
        val provider = PaypalPayment()

        assertEquals(TransactionStatus.Pending, transaction.status)
        provider.process(transaction)
        assertEquals(TransactionStatus.Completed, transaction.status)
    }

    @Test
    fun testTransactionStatusChangesToFailedOnLimitExceeded() {
        val transaction = Transaction(validID, Money.create(2000.00)!!, validSender, validReceiver)
        val provider = PaypalPayment()

        provider.process(transaction)

        assertTrue(transaction.status is TransactionStatus.Failed)
        assertEquals("Paypal Zahlartlimit von 1.000€ überschritten", (transaction.status as TransactionStatus.Failed).reason)
    }
}