package com.example.vyaperclone

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import kotlinx.parcelize.Parceler

@Parcelize
@Entity(tableName = "ItemsTable")
data class ItemsEntity(
    @ColumnInfo(name = "itemName") var name: String?,
    @ColumnInfo(name = "type") var type: String?,
    @ColumnInfo(name = "itemCode") var itemCode: String?,
    @ColumnInfo(name = "sacCode") var sacCode: String?,
    @ColumnInfo(name = "salePrice") var salePrice: Long?,
    @ColumnInfo(name = "purchasePrice") var purchasePrice: Long?,
    @ColumnInfo(name = "taxRate") var taxRate: Float?,
    @ColumnInfo(name = "stock") var stock: Long?,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = null,
) : Parcelable {

    companion object : Parceler<ItemsEntity> {
        override fun ItemsEntity.write(parcel: Parcel, flags: Int) {
            parcel.writeString(name)
            parcel.writeString(type)
            parcel.writeString(itemCode)
            parcel.writeString(sacCode)
            parcel.writeLong(salePrice ?: 0L)
            parcel.writeLong(purchasePrice ?: 0L)
            parcel.writeFloat(taxRate ?: 0f)
            parcel.writeLong(stock ?: 0L)
            parcel.writeValue(id)
        }

        override fun create(parcel: Parcel): ItemsEntity {
            return ItemsEntity(
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readLong(),
                parcel.readLong(),
                parcel.readFloat(),
                parcel.readLong(),
                parcel.readValue(Int::class.java.classLoader) as? Int
            )
        }
    }
}
