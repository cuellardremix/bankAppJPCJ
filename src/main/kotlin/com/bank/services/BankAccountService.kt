package com.bank.services

import com.bank.model.BankAccount
import com.bank.util.BasicCrud
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.Optional
import com.bank.model.Transaction

@Service
class BankAccountService(val bankAccountDAO:BankAccountDAO):BasicCrud<String,BankAccount> {
	
override fun getById(accountNumber:String): List<BankAccount> = bankAccountDAO.findByAccountNumber(accountNumber)

override fun insert(obj:BankAccount): BankAccount = 	bankAccountDAO.insert(obj)
    
override fun getAll(pageable:Pageable): Page<BankAccount> = bankAccountDAO.findAll(pageable)

override fun update(obj:BankAccount): BankAccount = bankAccountDAO.save(obj)
}