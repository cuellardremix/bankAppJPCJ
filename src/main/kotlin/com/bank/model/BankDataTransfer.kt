package com.bank.model

data class BankDataTransfer(
	val fromAccount : String="",
	val toAccount : String="",
	val amount : Double=0.0) 