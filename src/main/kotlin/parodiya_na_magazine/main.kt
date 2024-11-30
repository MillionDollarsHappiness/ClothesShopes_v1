package parodiya_na_magazine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


fun main() = runBlocking {
    val exitFlow = MutableStateFlow(false)

    // Загрузка покупок и избранного при запуске программы
    var purchases = loadPurchases().toMutableList()
    var favorites = loadFavorites().toMutableList()

    launch {
        while (true) {
            if (exitFlow.value) {
                println("Выход из программы.")
                break
            }

            println("Главное меню:")
            MainMenu.values().forEach { menu ->
                println("${menu.ordinal}: ${menu.nameOperation}")
            }

            val choice = readLine()?.toIntOrNull()

            when (choice) {
                MainMenu.FOOTWEAR_OPERATION.ordinal -> showProductTypes("footwear")
                MainMenu.CLOTHING_OPERATION.ordinal -> showProductTypes("clothing")
                MainMenu.ACCESSORIES_OPERATION.ordinal -> showProductTypes("accessories")
                MainMenu.FAVORITES.ordinal -> showFavorites(favorites, purchases)
                MainMenu.PURCHASES.ordinal -> showPurchases(purchases)
                MainMenu.EXIT.ordinal -> {
                    exitFlow.value = true
                }
                else -> println("Неверный выбор, попробуйте снова.")
            }

            // Обновляем актуальные данные при каждом цикле
            purchases = loadPurchases().toMutableList()
            favorites = loadFavorites().toMutableList()
        }
    }

    // Сохранение покупок и избранного при выходе
    savePurchases(purchases)
    saveFavorites(favorites)

    joinAll()
}

fun buyAllFavorites(favorites: MutableList<String>, purchases: MutableList<String>) {
    if (favorites.isNotEmpty()) {
        println("Вы купили все товары из избранного!")
        // Добавление всех товаров в список покупок
        purchases.addAll(favorites)
        favorites.clear() // Очистить избранное после покупки
        // Сохраняем изменения в файле сразу
        savePurchases(purchases)
        saveFavorites(favorites)
    }
}

fun purchaseFromFavorites(favorites: MutableList<String>, purchases: MutableList<String>) {
    println("Введите номер товара для покупки:")
    val index = readLine()?.toIntOrNull()

    if (index != null && index in 1..favorites.size) {
        val item = favorites[index - 1]
        println("Вы купили товар: $item")
        purchases.add(item)
        favorites.removeAt(index - 1)
        // Сохраняем изменения в файле сразу
        savePurchases(purchases)
        saveFavorites(favorites)
    } else {
        println("Неверный выбор, попробуйте снова.")
    }
}
