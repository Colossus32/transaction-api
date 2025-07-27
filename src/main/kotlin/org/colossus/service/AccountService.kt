package org.colossus.service

import org.colossus.dto.TransferRequest
import org.colossus.exception.AccountException
import java.math.BigDecimal

/**
 * Сервис для работы с банковскими аккаунтами и переводами
 */
interface AccountService {
    /**
     * Получает текущий баланс пользователя
     * @param userId идентификатор пользователя
     * @return текущий баланс
     */
    fun getBalance(userId: String): BigDecimal

    /**
     * Выполняет перевод средств между аккаунтами
     * @param request данные перевода
     * @throws AccountException если перевод невозможен
     */
    @Throws(AccountException::class)
    fun transfer(request: TransferRequest)
}