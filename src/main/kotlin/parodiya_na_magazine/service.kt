package parodiya_na_magazine

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun manageFavoritesAndPurchases(brand: Brand) {
    println("Вы выбрали ${brand.name} за ${brand.price} руб.")

    println("Хотите добавить в избранное? (y/n)")
    if (readLine()?.lowercase() == "y") {
        addToFavorites(brand.name)
    }

    println("Хотите купить? (y/n)")
    if (readLine()?.lowercase() == "y") {
        purchase(brand.name)
    }
}

fun addToFavorites(item: String) {
    if (!favorites.contains(item)) {
        favorites.add(item)
        println("$item добавлено в избранное.")
        saveFavorites(favorites)  // Сохраняем изменения в избранном
    } else {
        println("$item уже в избранном.")
    }
}

fun purchase(item: String) {
    if (favorites.contains(item)) {
        purchases.add(item)
        favorites.remove(item)
        println("$item куплено.")
        savePurchases(purchases)    // Сохраняем покупки
        saveFavorites(favorites)   // Обновляем избранное
    } else {
        println("$item не найдено в избранном.")
    }
}

// Сериализация покупок
fun savePurchases(purchases: MutableList<String>) {
    try {
        // Читаем существующие данные из файла
        val existingData = if (purchasesFile.exists()) {
            val jsonData = purchasesFile.readText()
            Json.decodeFromString<MutableList<String>>(jsonData)
        } else {
            mutableListOf<String>()
        }

        // Добавляем новые покупки, если их нет в существующем списке
        for (purchase in purchases) {
            if (!existingData.contains(purchase)) {
                existingData.add(purchase)
            }
        }

        // Сохраняем обновленный список
        val jsonData = Json.encodeToString(existingData)
        purchasesFile.writeText(jsonData)
        println("Покупки успешно сохранены.")
    } catch (e: Exception) {
        println("Ошибка при сохранении покупок: ${e.message}")
    }
}
// Загрузка покупок
fun loadPurchases(): List<String> {
    return if (purchasesFile.exists()) {
        try {
            val jsonData = purchasesFile.readText()
            Json.decodeFromString<MutableList<String>>(jsonData)
        } catch (e: Exception) {
            println("Ошибка при загрузке покупок: ${e.message}")
            mutableListOf<String>()
        }
    } else {
        mutableListOf<String>()
    }
}


// Сериализация избранных товаров
fun saveFavorites(favorites: MutableList<String>) {
    try {
        val jsonData = Json.encodeToString(favorites)
        favoritesFile.writeText(jsonData)
    } catch (e: Exception) {
        println("Ошибка при сохранении избранного: ${e.message}")
    }
}

// Загрузка избранных товаров
fun loadFavorites(): List<String> {
    return if (favoritesFile.exists()) {
        try {
            val jsonData = favoritesFile.readText()
            Json.decodeFromString<List<String>>(jsonData)
        } catch (e: Exception) {
            println("Ошибка при загрузке избранного: ${e.message}")
            emptyList()
        }
    } else {
        emptyList()
    }

}