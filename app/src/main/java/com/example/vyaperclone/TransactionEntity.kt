package com.example.vyaperclone

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactionsTable")
data class TransactionEntity(
    @ColumnInfo(name = "billNo") var billNo: Int?,
    @ColumnInfo(name = "type") var type: String?,
    @ColumnInfo(name = "partyName") var partyName: String?,
    @ColumnInfo(name = "billedItemNames") var billedItemNames: String?,
    @ColumnInfo(name = "billedItemRate") var billedItemRate: String?,
    @ColumnInfo(name = "billedItemQuantity") var billedItemQuantity: String?,
    @ColumnInfo(name = "paidAmt") var paidAmt: Long?,
    @ColumnInfo(name = "received") var received: Long?,
    @ColumnInfo(name = "total") var total: Long?,
    @ColumnInfo(name = "partyContactNumber") var partyContactNumber: String?,
    @ColumnInfo(name = "partyBillingAddress") var partyBillingAddress: String?, ) {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}