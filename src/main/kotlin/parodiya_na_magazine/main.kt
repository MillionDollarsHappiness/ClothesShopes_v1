package parodiya_na_magazine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.system.exitProcess

fun main() = runBlocking {
    val exitFlow = MutableStateFlow(false)

    // Загрузка покупок и избранного при запуске программы
    var purchases = loadPurchases().toMutableList()
    var favorites = loadFavorites().toMutableList()

    launch {
        exitFlow.collect { exit ->
            if (exit) {
                println("Выход из программы.")
                exitProcess(0)
            }
        }
    }

    launch {
        while (!exitFlow.value) {
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

            purchases = loadPurchases().toMutableList()
            favorites = loadFavorites().toMutableList()
        }
    }

    // Сохранение покупок и избранного при выходе
    savePurchases(purchases)
    saveFavorites(favorites)
}
