package org.colossus.exception

/**
 * Базовое исключение для ошибок, связанных с аккаунтами
 */
open class AccountException(message: String) : RuntimeException(message)

/**
 * Аккаунт не найден
 */
class AccountNotFoundException(message: String) : AccountException(message)

/**
 * Недостаточно средств
 */
class InsufficientFundsException(message: String) : AccountException(message)

/**
 * Некорректные данные перевода
 */
class InvalidTransferException(message: String) : AccountException(message)