package de.thws.fiw.kotlin.portfolio2

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

class TransactionTest {

    @Test
    fun testMoneyThrowsExceptionOnNegativeAmmount() {
        assertNull(Money.create(-10.00))
//        assertThrows<IllegalArgumentException> {
//            Money(-10.0)
//        }Die Exceptions sollen in Stufe 3 nicht mehr geworfen werden
    }

    @Test
    fun testMoneyCreatedSuccessfullyWithValidAmount() {
        val money = Money.create(50.50)!!
        assertEquals(50.50, money.amount)
    }

    @Test
    fun testEmailThrowsExceptionOnInvalidFormat() {
        assertNull(Email.create("dies-ist-keine-mailadresse"))
//        assertThrows<IllegalArgumentException> {
//            Email("dies-ist-keine-mailadresse")
//        }Die Exceptions sollen in Stufe 3 nicht mehr geworfen werden
    }

    @Test
    fun testEmailCreatedSuccessfullyWithValidFormat() {
        val email = Email.create("test@thws.de")!!
        assertEquals("test@thws.de", email.value)
    }

    @Test
    fun testTransactionCreatedSuccessfullyWithValidData() {
        val id = TransactionId.create("ABC+123")!!
        val amount = Money.create(19.99)!!
        val sender = Email.create("sender@thws.de")!!
        val receiver = Email.create("receiver@thws.de")!!

        val transaction = Transaction(id, amount, sender, receiver)

        assertNotNull(transaction)
        assertEquals(id, transaction.id)
    }

    @Test
    fun testTransacrionThrowsExeptionWhenSenderAndReceiverAreIdentical() {
        val id = TransactionId.create("ABC+123")!!
        val amount = Money.create(19.99)!!
        val identicEmail = Email.create("identisch@thws.de")!!

        assertThrows<IllegalArgumentException> {
            Transaction(id, amount, identicEmail, identicEmail)
        }
    }

    @Test
    fun testTransactionIdFactoryValidation() {
        assertNull(TransactionId.create("   "))

        val id = TransactionId.create("ABC+123")
        assertNotNull(id)
        assertEquals("ABC+123", id?.value)
    }
}