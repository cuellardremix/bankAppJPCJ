package com.bank.util

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.Optional

interface BasicCrud<K,T> {
    fun getAll(pageable: Pageable): Page<T>
    fun getById(accountNumber:K):List<T>
    fun insert(obj:T):T
    fun update(obj:T):T
//    fun deleteById(id: K):Optional<T>
}