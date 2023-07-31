package com.example.datingapp.data.repositories

import com.example.datingapp.ui.models.ProfileCard
import com.example.datingapp.utils.Resource
import kotlinx.coroutines.delay

class FakeSwipeRepository : SwipeRepository {
    override suspend fun getProfileSwipeList(profileId: Int): Resource<List<ProfileCard>> {
        delay(500)
        val list = mutableListOf<ProfileCard>()
        for (i in 1..9) {
            list.add(
                ProfileCard(
                    i, i, 178, "Female", "Алина $i",
                    18, "Звезда кантри-музыки. Обожаю гулять вечером по парку.", "Беларусь", "Минск",
                    "Овен", "Найти мужчину своей жизни", "Не занимаюсь спортом",
                    "Иногда пьющий", "Регулярно курящий", "Аллергичен на животных",
                    listOf("Уборка", "Игры", "Танцы", "Фотография", "Кино"),
                    listOf(
                        "https://sun9-79.userapi.com/impg/TejZrgXyWcVMbm8OGMLdOd6vbI7pZSKRxGtV1w/Hk4rWw5PzSw.jpg?size=1440x1439&quality=95&sign=41838d9379c00e56251dbeed0ac3a2ed&type=album",
                        "https://sun9-65.userapi.com/impg/v2XRhsGsKvSD2xGDsb_WS0mfBrk0yy0jjLOayw/TPSj4eMIyuU.jpg?size=720x957&quality=95&sign=6d3123d435a89809ec1f00e7b56814ed&type=album",
                        "https://sun9-22.userapi.com/impg/g0gRc-BI72VGLtjSO5g4mVLkgcu9ZCt4OnswWw/hN8Jr06ZWbM.jpg?size=2560x2560&quality=95&sign=c84ce23015fd829fb39c9ba284d6cc1e&type=album"
                    ),
                    listOf(
                        "L6B3{E5i9:rp03rCR?cH}+t7T1j;",
                        "L8I4ChNG1a~V00nORis9GCr?^QM{",
                        "L9AJ+irq~UM|?c\$|n%jZx]WBWCRj"
                    )
                )
            )
        }
        return Resource.Success(list)
    }
}