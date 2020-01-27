package com.bank.services

import com.bank.model.Transaction
import com.bank.util.BasicCrud
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class TransactionService(val bankAccountDAO:BankAccountDAO, private val transasctionDAO:TransactionDAO):BasicCrud<String,Transaction> {

override fun getById(id:String): List<Transaction> = transasctionDAO.findByFromAccount(id)
	
override fun insert(obj:Transaction): Transaction = transasctionDAO.insert(obj)
	
override fun getAll(pageable:Pageable): Page<Transaction> = transasctionDAO.findAll(pageable)
	
override fun update(obj:Transaction): Transaction = transasctionDAO.save(obj)
	
		fun getAllTransactionsByFromAccount(fromAccount:String) = transasctionDAO.findByFromAccount(fromAccount)
	fun getAllTransactionsByToAccount(toAccount:String) = transasctionDAO.findByFromAccount(toAccount)
}