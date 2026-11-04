package de.thws.fiw.kotlin.portfolio2

sealed class TransactionStatus {
    object Pending : TransactionStatus()
    object Completed : TransactionStatus()
    data class Failed(val reason: String) : TransactionStatus()
}

class Transaction(
    val id: TransactionId,
    val amount: Money,
    val sender: Email,
    val receiver: Email
) {
    var status: TransactionStatus = TransactionStatus.Pending
        private set

    init {
        require(sender != receiver) {"Absender und Empänger dürfen nicht gleich sein."}
    }

    fun updateStatus(newStatus: TransactionStatus) {
        this.status = newStatus
    }
}