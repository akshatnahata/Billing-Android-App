package com.example.vyaperclone

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "addItemsTable")
data class AddItems(
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "itemsName") val itemsName: String,
    @ColumnInfo(name = "quantity") val quantity: Int,
    @ColumnInfo(name = "unit") val unit: String,
    @ColumnInfo(name = "rate") val rate: Double,
    @ColumnInfo(name = "taxExcluded") val taxExcluded: Double
)
{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id1")
    var id1: Int? = null
}