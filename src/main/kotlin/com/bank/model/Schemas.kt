package com.bank.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.Date

@Document data class BankAccount(@Id val accountNumber:String, var balance:Double,val owner:String, val createdAt:Date){
	
fun addFunds(ammount:Double){
    balance+=ammount;
  }

}

@Document data class Transaction(@Id val id:Long, val fromAccount:String, val toAccount:String, val amount:Double,val sentAt:Date)