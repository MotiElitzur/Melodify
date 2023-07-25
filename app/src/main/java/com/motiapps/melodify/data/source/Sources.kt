package com.motiapps.melodify.data.source

import androidx.room.RoomDatabase

data class Sources(
    val roomDatabase: RoomDatabase,
    val firestore: String,
)
