package labs.pooh.eaterslab.repository.dao

import labs.pooh.client.models.MenuOptionTag

data class MenuOptionTagDao(
    val name: String,
    val id: Int?
)

fun MenuOptionTag.toDao() = name?.let { MenuOptionTagDao(it, id) }
