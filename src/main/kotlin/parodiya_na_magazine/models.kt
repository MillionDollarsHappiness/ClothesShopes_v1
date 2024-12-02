package parodiya_na_magazine

import java.io.File


data class ProductType(val name: String, val brands: List<Brand>)
data class Brand(val name: String, val price: Int)

enum class MainMenu(val num: Int, val nameOperation: String) {
    EXIT(0, "Выйти из программы"),
    FOOTWEAR_OPERATION(1, "Обувь"),
    CLOTHING_OPERATION(2, "Одежда"),
    ACCESSORIES_OPERATION(3, "Аксессуары"),
    FAVORITES(4, "Избранное"),
    PURCHASES(5, "Корзина покупок")
}
// Файлы для хранения данных
val purchasesFile = File("purchases.json")
val favoritesFile = File("favorites.json")

// Структуры данных для покупок и избранного
val purchases = mutableListOf<String>()
val favorites = mutableListOf<String>()
