package com.brunohensel.core

/**
 * It will explicitly represent that a function may not always have an answer in the return type.
 * [None] undefined value
 * [Some] defined value
 *
 * fun mean(xs: List<Double): Option<Double> =
 *  if(xs.isEmpty()) None
 *  else Some(xs.sum() / xs.size())
 *
 *  The return type now reflects the possibility that the result may not always be defined.
 *
 *  It acts like [Either] but in this case we don't care about the exception that may or may not
 *  be thrown in the process.
 */
sealed class Option<out A> {
    object None : Option<Nothing>()
    data class Some<out A>(val get: A) : Option<A>()
}

/**
 * Extension function to make it easy to get access to the value of the Some data constructor.
 */
fun <A> Option<A>.getOrNull(): A? =
    when (this) {
        is Option.None -> null
        is Option.Some -> this.get
    }
