package de.thws.fiw.kotlin.portfolio2

data class Money private constructor(val amount: Double) {//Double/Float ist bei Geld problematisch, siehe PRG1 ortfolio
    init {
        require(amount >= 0.00) {"Der Geldbetrag darf nicht negativ sein."}
    }

    companion object {
        fun create(amount: Double): Money? {
            return if (amount >= 0.00) Money(amount) else null
        }
    }
}

data class Email private constructor(val value: String) {
    init {
        require(value.contains("@") && value.contains(".")) {"Ungültiges Email-Format: $value"}
    }

    companion object {
        fun create(value: String): Email? {
            return if (value.contains("@") && value.contains(".")) Email(value) else null
        }
    }
}

data class TransactionId private constructor(val value: String) {
    init {
        require(value.isNotBlank()) {"TransactionID darf nicht leer sein."}
    }

    companion object {
        fun create(value: String): TransactionId? {
            return if (value.isNotBlank()) TransactionId(value) else null
        }
    }
}