package com.example.vyaperclone

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.work.impl.WorkDatabaseMigrations.MIGRATION_1_2

@Database(
    entities = [ItemsEntity::class, PartyEntity::class, TransactionEntity::class, ExpenseEntity::class, AddItems::class],
    version = 5
)
abstract class VyaparDatabase : RoomDatabase() {
    abstract fun getDAO(): VyaparDAO
}