package ru.netology

fun main() {
    println(calculateCommission("Mastercard", 50_000, 30_000)) // Пример вызова
    println(calculateCommission("Visa", 0, 5_000))
    println(calculateCommission("Mir", 100_000, 50_000))
    println(calculateCommission("Mastercard", 500_000, 150_000)) // Должен отклониться из-за превышения месячного лимита
}

fun calculateCommission(cardType: String = "Mir", previousTransfers: Int = 0, transferAmount: Int): String {
    val dailyLimit = 150_000
    val monthlyLimit = 600_000
    val mastercardLimit = 75_000 // Льготный порог для Mastercard

    if (transferAmount > dailyLimit || previousTransfers + transferAmount > monthlyLimit) {
        return "Перевод отклонён: превышен лимит."
    }

    val commission = when (cardType) {
        "Mastercard" -> {
            val totalTransfers = previousTransfers + transferAmount
            when {
                previousTransfers >= mastercardLimit -> transferAmount * 0.006 + 20 // Льготный порог превышен ранее
                totalTransfers <= mastercardLimit -> 0.0 // Льготный порог не превышен
                else -> (totalTransfers - mastercardLimit) * 0.006 + 20 // Превышение порога текущим платежом
            }
        }
        "Visa" -> (transferAmount * 0.0075).coerceAtLeast(35.0)
        "Mir" -> 0.0
        else -> return "Ошибка: неизвестный тип карты"
    }

    return "Комиссия за перевод: $commission руб."
}

