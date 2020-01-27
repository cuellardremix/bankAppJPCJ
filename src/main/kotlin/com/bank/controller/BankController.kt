package com.bank.controller

import com.bank.model.BankAccount
import com.bank.model.BankDataTransfer
import com.bank.model.BankMessage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.GetMapping
import com.bank.services.BankAccountService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.Date
import com.bank.model.Transaction
import java.util.concurrent.atomic.AtomicLong
import com.bank.services.TransactionService

@RestController
class BankController(private val bankAccountService:BankAccountService, private val transactionService:TransactionService) {
	val atomicLong:AtomicLong=AtomicLong();
	
	@GetMapping("/test")
	fun getAll(pageable: Pageable): String {
		var a:BankAccount=BankAccount("9937716254",200.00,"99377162",Date())
		bankAccountService.insert(a);
 a=BankAccount("993771625429",300.00,"993772",Date())
		bankAccountService.insert(a);
 a=BankAccount("993771625423",400.00,"993773",Date())
		bankAccountService.insert(a);
				a=BankAccount("993771",-200.00,"99377162",Date())
		bankAccountService.insert(a);
				a=BankAccount("993771123",-600.00,"99377162",Date())
		bankAccountService.insert(a);
				a=BankAccount("993771543",-700.00,"99377162",Date())
		bankAccountService.insert(a);
				a=BankAccount("99377108978",500.00,"99377162",Date())
		bankAccountService.insert(a);
		
		bankAccountService.getAll(pageable)
		transactionService.getAll(pageable)
		return "success"
	}

	
	@PostMapping("/transfer")
	fun transferMoney(@RequestBody transferData: BankDataTransfer ):BankMessage?{
		if(transferData.fromAccount?.equals("") || transferData.toAccount.equals("")){
						return BankMessage("fail","No data",0.0);
		}
		
		val	froms:List<BankAccount> =bankAccountService.getById(transferData.fromAccount)
		val	tos:List<BankAccount> =bankAccountService.getById(transferData.toAccount)
		if(froms.isEmpty() || tos.isEmpty()){
			return BankMessage("fail","No data",0.0);
		}
		val fromAccount:BankAccount?=froms.first();
		val toAccount:BankAccount?=froms.first();
		
		if(fromAccount?.balance!!.compareTo(-500)<0){
			return BankMessage("fail","The Account ${toAccount?.accountNumber} doesn't have enough funds",toAccount!!.balance);
		}else{
			toAccount?.addFunds(transferData.amount);
			fromAccount?.addFunds(-transferData.amount);
			val	transactionFrom:Transaction=Transaction(atomicLong.incrementAndGet(),fromAccount.accountNumber,toAccount!!.accountNumber,transferData.amount,Date())
			val	transactionTo:Transaction=Transaction(atomicLong.incrementAndGet(),fromAccount.accountNumber,toAccount!!.accountNumber,transferData.amount,Date())
			transactionService.insert(transactionFrom)
			transactionService.insert(transactionTo)
			bankAccountService.update(toAccount);
			bankAccountService.update(fromAccount);
			return BankMessage("succes","The transfer from ${fromAccount?.accountNumber} to ${toAccount!!.accountNumber} was successfull",toAccount!!.balance);			
		}
	}
	
	@GetMapping("/allTransactionsFromAccount")
		fun getAllTransactionsFromAccount(@RequestParam(name="accountNumber",defaultValue="", required=true) accountNumber:String):List<Transaction>{
				val	froms:List<Transaction> = transactionService.getAllTransactionsByFromAccount(accountNumber)
				val	tos:List<Transaction> =transactionService.getAllTransactionsByToAccount(accountNumber)
				if(froms.isEmpty() || tos.isEmpty()){
					return  mutableListOf<Transaction>()
				}
				return	froms.orEmpty() + tos.orEmpty()
		}
		@GetMapping("/allReceivedTransactionsFromAccount")
		fun getAllReceivendTransactionsFromAccount(@RequestParam(name="accountNumber",defaultValue="", required=true) accountNumber:String):List<Transaction>{
			val trs:List<Transaction> = transactionService.getAllTransactionsByFromAccount(accountNumber)
			if(trs.isEmpty()){
				return  mutableListOf<Transaction>()
			}
			return	trs
		}
		@GetMapping("/allSentTransactionsFromAccount")
		fun getAllSentTransactionsFromAccount(@RequestParam(name="accountNumber",defaultValue="", required=true) accountNumber:String):List<Transaction>{
			val trs:List<Transaction> = transactionService.getAllTransactionsByToAccount(accountNumber)
			if(trs.isEmpty()){
				return  mutableListOf<Transaction>()
			}
			return	trs
			
		}

		@GetMapping("/balance")
		fun getBalanceFromAccount(@RequestParam(name="accountNumber",defaultValue="", required=true) accountNumber:String):BankAccount{
			var acc:List<BankAccount> = 	bankAccountService.getById(accountNumber)
				if(acc.isEmpty()){
				return  BankAccount("",0.0,"",Date())
			}
			return acc?.first();
		}	
}