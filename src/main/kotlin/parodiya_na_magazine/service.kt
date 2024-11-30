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
    favorites.add(item)
    println("$item добавлено в избранное.")
    saveFavorites(favorites)
}

fun purchase(item: String) {
    purchases.add(item)
    favorites.remove(item)
    println("$item куплено.")
    savePurchases(purchases)
    saveFavorites(favorites)
}
// Сериализация покупок
fun savePurchases(purchases: MutableList<String>) {
    val jsonData = Json.encodeToString(purchases)
    purchasesFile.writeText(jsonData)
}

// Загрузка покупок
fun loadPurchases(): MutableList<String> {
    return if (purchasesFile.exists()) {
        val jsonData = purchasesFile.readText()
        Json.decodeFromString<MutableList<String>>(jsonData) // Декодируем как MutableList
    } else {
        mutableListOf() // Возвращаем пустой MutableList, если файл не существует
    }
}

// Сериализация избранных товаров
fun saveFavorites(favorites: MutableList<String>) {
    val jsonData = Json.encodeToString(favorites)
    favoritesFile.writeText(jsonData)
}

// Загрузка избранных товаров
fun loadFavorites(): MutableList<String> {
    return if (favoritesFile.exists()) {
        val jsonData = favoritesFile.readText()
        Json.decodeFromString<MutableList<String>>(jsonData) // Декодируем как MutableList
    } else {
        mutableListOf() // Возвращаем пустой MutableList, если файл не существует
    }
}



