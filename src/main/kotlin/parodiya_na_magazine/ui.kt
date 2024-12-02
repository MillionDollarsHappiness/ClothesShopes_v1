package parodiya_na_magazine
fun showProductTypes(type: String) {
    val productTypes = when (type) {
        "footwear" -> listOf(
            ProductType("Кроссовки", listOf(Brand("Nike", 7000), Brand("Adidas", 6500))),
            ProductType("Ботинки", listOf(Brand("Timberland", 8000), Brand("Dr. Martens", 7500)))
        )
        "clothing" -> listOf(
            ProductType("Футболки", listOf(Brand("H&M", 1500), Brand("Zara", 2000))),
            ProductType("Джинсы", listOf(Brand("Levi's", 3000), Brand("Gap", 2500)))
        )
        "accessories" -> listOf(
            ProductType("Ремни", listOf(Brand("Gucci", 5000), Brand("Calvin Klein", 4000))),
            ProductType("Сумки", listOf(Brand("Chanel", 20000), Brand("Prada", 18000)))
        )
        else -> emptyList()
    }
    if (productTypes.isEmpty()) {
        println("Неизвестная категория товаров.")
        return
    }

    while (true) {
        println("Выберите раздел:")
        productTypes.forEachIndexed { index, product ->
            println("${index + 1}: ${product.name}")
        }
        println("${productTypes.size + 1}: Вернуться в главное меню")

        val typeChoice = readLine()?.toIntOrNull()
        if (typeChoice != null && typeChoice in 1..productTypes.size) {
            showBrands(productTypes[typeChoice - 1])
            break
        } else if (typeChoice == productTypes.size + 1) {
            return
        } else {
            println("Неверный выбор. Попробуйте снова.")
        }
    }
}
fun showBrands(productType: ProductType) {
    while (true) {
        println("Выберите бренд:")

        productType.brands.forEachIndexed { index, brand ->
            println("${index + 1}: ${brand.name} - ${brand.price} руб.")
        }

        println("${productType.brands.size + 1}: Вернуться в главное меню")

        val brandChoice = readLine()?.toIntOrNull()

        when {
            brandChoice != null && brandChoice in 1..productType.brands.size -> {
                val selectedBrand = productType.brands[brandChoice - 1]
                manageFavoritesAndPurchases(selectedBrand)
                break
            }
            brandChoice == productType.brands.size + 1 -> {
                return
            }
            else -> {
                println("Неверный выбор. Пожалуйста, выберите номер бренда из списка или вернитесь в главное меню.")
            }
        }
    }
}

fun showFavorites(favorites: MutableList<String>, purchases: MutableList<String>) {
    if (favorites.isEmpty()) {
        println("Избранное пусто.")
    } else {
        println("Избранное:")
        favorites.forEach { item ->
            println("  - $item (можно купить!)")
        }

        println("Что вы хотите сделать?")
        println("(1) Купить все товары из избранного")
        println("(2) Выбрать конкретный товар для покупки")
        println("(3) Вернуться в главное меню")

        val action = readLine()?.toIntOrNull()
        when(action) {
            1 -> buyAllFavorites(favorites, purchases) // Обновите список покупок
            2 -> purchaseFromFavorites(favorites, purchases) // Обновите список покупок
            3 -> return
            else -> println("Неверный выбор, попробуйте снова.")
        }
    }
}

fun showPurchases(purchases: MutableList<String>) {
    if (purchases.isEmpty()) {
        println("Покупки пусты.")
    } else {
        println("Покупки:")
        purchases.forEach { item ->
            println("  - $item")
        }
    }
}
fun purchaseFromFavorites(favorites: MutableList<String>, purchases: MutableList<String>) {
    println("Введите номер товара для покупки из избранного:")

    // Печатаем список избранных товаров с номерами
    favorites.forEachIndexed { index, item ->
        println("${index + 1}. $item")
    }

    // Получаем ввод пользователя
    val index = readLine()?.toIntOrNull()

    if (index != null && index in 1..favorites.size) {
        // Индекс из ввода пользователя, вычитаем 1, так как индексация с 0
        val item = favorites[index - 1]
        println("Вы купили товар: $item")

        // Добавляем товар в список покупок
        purchases.add(item)

        // Удаляем товар из избранного
        favorites.removeAt(index - 1)

        // Сохраняем изменения в покупках и избранном
        savePurchases(purchases)
        saveFavorites(favorites)
    } else {
        println("Неверный выбор, попробуйте снова.")
    }
}
fun buyAllFavorites(favorites: MutableList<String>, purchases: MutableList<String>) {
    if (favorites.isNotEmpty()) {
        println("Вы купили все товары из избранного!")

        // Перемещаем все товары из избранного в покупки
        purchases.addAll(favorites)

        // Очищаем список избранных товаров
        favorites.clear()

        // Сохраняем изменения в списках покупок и избранного
        savePurchases(purchases)
        saveFavorites(favorites)
    } else {
        println("В вашем избранном нет товаров для покупки!")
    }
}