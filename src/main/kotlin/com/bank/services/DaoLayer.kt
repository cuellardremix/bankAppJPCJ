package com.bank.services

import com.bank.model.BankAccount
import com.bank.model.Transaction
import org.springframework.data.mongodb.repository.MongoRepository

interface BankAccountDAO:MongoRepository<BankAccount,String>{
	fun findByAccountNumber(accountNumber:String):List<BankAccount>

}
interface TransactionDAO:MongoRepository<Transaction,Long>{
//	fun findByBankAccount(id:String):List<Transaction>
		fun findByFromAccount(fromAccount:String):List<Transaction>
		fun findByToAccount(toAccount:String):List<Transaction>
}