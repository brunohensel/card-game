package com.brunohensel.core

/**
 * Encoding success and failure conditions.
 * This is a data type that encodes whatever information we want about failures, which lets us
 * track a reason for that failure.
 *
 * [E] represents the error type
 * [A] represents a generic type
 *
 * The Either data type represents, in a very general way, values that can be one of two things,
 * Success or Failure. By convention the [Right] constructor is reserved for the success case,
 * and [Left] is used for failure.
 *
 * It acts like Result<T> from Kotlin standard library. This is only available from version 1.5.0
 * onwards without to have to do any tricks.
 */
sealed class Either<out E, out A> {
    data class Left<out E>(val value: E) : Either<E, Nothing>()
    data class Right<out A>(val value: A) : Either<Nothing, A>()
}