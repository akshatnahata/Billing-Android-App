package com.example.vyaperclone

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface VyaparDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(itemsEntity: ItemsEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertParty(partyEntity: TransactionEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transactionEntity: TransactionEntity)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expenseEntity: ExpenseEntity)


    @Query("SELECT * FROM ItemsTable")
    fun getAllItems(): LiveData<List<ItemsEntity>>

    @Query("SELECT * FROM transactionsTable")
    fun getAllTransactions(): LiveData<List<TransactionEntity>>

    @Query("SELECT * FROM partyTable")
    fun getAllParties(): LiveData<List<TransactionEntity>>

    @Query("SELECT * FROM expensetable")
    fun getAllExpenses(): LiveData<List<ExpenseEntity>>

    @Query("SELECT * FROM ItemsTable WHERE itemName= :itemName")
    suspend fun getSpecificItem(itemName: String): ItemsEntity?

    @Query("SELECT * FROM partyTable WHERE partyName= :partyName")
    suspend fun getSpecificParty(partyName: String): TransactionEntity?

    @Update
    suspend fun updateItems(itemsEntity: ItemsEntity)

    @Update
    suspend fun updateParty(partyEntity: TransactionEntity)

    @Query("UPDATE transactionsTable SET partyName = :partyName, total = :total, paidAmt = :paidAmt, received = :received, partyContactNumber = :partyContactNumber WHERE billNo = :billNo")
    suspend fun updateTransaction(
        billNo: Int?,
        partyName: String?,
        total: Long?,
        paidAmt: Long?,
        received: Long?,
        partyContactNumber: String
    )
    @Query("DELETE FROM transactionsTable WHERE billNo = :billNo")
    suspend fun deleteTransactionByBillNo(billNo: Int)

    @Insert
    suspend fun insertAddItems(addItems: AddItems)

    @Query("SELECT * FROM addItemsTable")
    fun getAllAddItems():LiveData<List<AddItems>>

}