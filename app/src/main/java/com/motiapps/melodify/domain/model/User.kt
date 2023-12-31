package com.motiapps.melodify.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val id: String,
    val name: String? = null,
    val creationTimestamp: Long
)
