package com.peto.droidmorning.data.mapper

import com.peto.droidmorning.domain.model.User
import io.github.jan.supabase.auth.user.UserInfo

fun UserInfo.toDomainModel(): User =
    User(
        id = id,
        email = email,
        phone = phone,
        createdAt = createdAt.toString(),
    )
