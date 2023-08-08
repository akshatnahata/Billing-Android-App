package com.example.vyaperclone

import androidx.lifecycle.LiveData
import javax.xml.transform.dom.DOMLocator

class ItemsRepository(private val databaseDao: VyaparDAO) {

    fun getAllItems(): LiveData<List<ItemsEntity>> {
        return databaseDao.getAllItems()
    }

    fun getAllTransactions(): LiveData<List<TransactionEntity>> {
        return databaseDao.getAllTransactions()
    }

    fun getAllParties(): LiveData<List<TransactionEntity>> {
        return databaseDao.getAllParties()
    }

    fun getAllExpenses(): LiveData<List<ExpenseEntity>> {
        return databaseDao.getAllExpenses()
    }

    suspend fun addItem(itemsEntity: ItemsEntity) {
        databaseDao.insertItem(itemsEntity)
    }

    suspend fun addParty(partyEntity: TransactionEntity) {
        databaseDao.insertParty(partyEntity)
    }

    suspend fun addExpense(expenseEntity: ExpenseEntity) {
        databaseDao.insertExpense(expenseEntity)
    }

    suspend fun addTransaction(transactionEntity: TransactionEntity) {

        databaseDao.insertTransaction(transactionEntity)
        val itemList = convertBilledItemNamesToList(transactionEntity.billedItemNames)
        val itemrate = convertBilledItemRateToList(transactionEntity.billedItemRate)
        val itemQuantity = convertBilledItemQuantityToList(transactionEntity.billedItemQuantity)

        updateItems(itemList,itemrate, itemQuantity, transactionEntity.type)
        transactionEntity.partyName?.let {
            updateParty(
                it,
                transactionEntity.type!!,
                transactionEntity.total!!
            )
        }

    }

    suspend private fun updateParty(partyName: String, type: String, total: Long) {
        val party = databaseDao.getSpecificParty(partyName)
        if (type == Constants.PURCHASE) {
            if (party != null) {
                party.total = party.total?.minus(total)
            }
        } else {
            if (party != null) {
                party.total = party.total?.plus(total)
            }
        }
        if (party != null) {
            databaseDao.updateParty(party)
        }

    }

    suspend fun updateItem(itemsEntity: ItemsEntity) {
        databaseDao.updateItems(itemsEntity)
    }

    private suspend fun updateItems(
        itemList: List<String>,
        itemrate: ArrayList<Double>,
        itemQuantity: java.util.ArrayList<Int>,
        type: String?
    ) {

        for (i in itemList.indices) {

            val items = databaseDao.getSpecificItem(itemList[i])
            if (items != null) {
                if (type == Constants.PURCHASE) {
                    items.stock = items.stock?.plus(itemQuantity[i])
                } else {
                    items.stock = items.stock?.minus(itemQuantity[i])
                }
                databaseDao.updateItems(items);
            }

        }

    }

    private fun convertBilledItemNamesToList(billedItemNames: String?): List<String> {
        return billedItemNames!!.split(",")
    }

    private fun convertBilledItemQuantityToList(billedItemQuantity: String?): ArrayList<Int> {
        val data = billedItemQuantity!!.split(",")
        val list: ArrayList<Int> = ArrayList()
        for (item in data) {
            if (item.isNotBlank()) {
                list.add(item.toInt())
            }
        }
        return list;
    }
    private fun convertBilledItemRateToList(billedItemRate: String?): ArrayList<Double> {
        val data = billedItemRate!!.split(",")
        val list: ArrayList<Double> = ArrayList()
        for (item in data) {
            if (item.isNotBlank()) {
                list.add(item.toDouble())
            }
        }
        return list;
    }

    suspend fun updateTransaction(transactionEntity: TransactionEntity) {
            databaseDao.updateTransaction(transactionEntity.billNo,transactionEntity.partyName,transactionEntity.total,transactionEntity.paidAmt,transactionEntity.received,
                transactionEntity.partyContactNumber.toString()
            )
    }

    suspend fun deleteTransactionByBillNo(billNo: Int) {
            databaseDao.deleteTransactionByBillNo(billNo)
    }

}