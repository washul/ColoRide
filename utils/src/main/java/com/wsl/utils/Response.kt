package com.wsl.utils

// Credits to Alex Hart -> https://proandroiddev.com/kotlins-nothing-type-946de7d464fb
// Composes 2 functions
/**
 * Represents a value of one of two possible types (a disjoint union).
 * Instances of [Response] are either an instance of [Failure] or [Success].
 * FP Convention dictates that [Failure] is used for "failure"
 * and [Success] is used for "success".
 *
 * @see Failure
 * @see Success
 */
sealed class Response<out L, out R> {
    /** * Represents the left side of [Response] class which by convention is a "Failure". */
    data class Failure<out L>(val a: L) : Response<L, Nothing>()

    /** * Represents the right side of [Response] class which by convention is a "Success". */
    data class Success<out R>(val b: R) : Response<Nothing, R>()

    /**
     * Returns true if this is a Success, false otherwise.
     * @see Success
     */
    val isSuccess get() = this is Success<R>

    /**
     * Returns true if this is a Failure, false otherwise.
     * @see Failure
     */
    val isFailure get() = this is Failure<L>

    /**
     * Creates a Failure type.
     * @see Failure
     */
    fun <L> failure(a: L) = Failure(a)


    /**
     * Creates a Success type.
     * @see Success
     */
    fun <R> success(b: R) = Success(b)

    /**
     * Applies fnL if this is a Failure or fnR if this is a Success.
     * @see Failure
     * @see Success
     */
    inline fun fold(fnL: (L) -> Unit, fnR: (R) -> Unit): Any =
        when (this) {
            is Failure -> fnL(a)
            is Success -> fnR(b)
        }

    /**
     * Returns the encapsulated value if this instance represents a Success otherwise null
     */
    fun getOrNull() = if (this is Success) b else null
}

/**
 * Right-biased flatMap() FP convention which means that Success is assumed to be the default case
 * to operate on. If it is Failure... return the Failure value unchanged.
 */
inline fun <T, L, R> Response<L, R>.flatMap(fn: (R) -> Response<L, T>): Response<L, T> =
    when (this) {
        is Response.Failure -> Response.Failure(a)
        is Response.Success -> fn(b)
    }


/**
 * Right-biased map() FP convention which means that Success is assumed to be the default case
 * to operate on. If it is Failure... return the Failure value unchanged.
 */
inline fun <T, L, R> Response<L, R>.map(fn: (R) -> (T)): Response<L, T> =
    when (this) {
        is Response.Failure -> Response.Failure(a)
        is Response.Success -> Response.Success(fn(b))
    }

/** Returns the value from this `Success` or the given argument if this is a `Failure`.
 *  Success(12).getOrElse(17) RETURNS 12 and Failure(12).getOrElse(17) RETURNS 17
 */
fun <L, R> Response<L, R>.getOrElse(value: R): R =
    when (this) {
        is Response.Failure -> value
        is Response.Success -> b
    }

/**
 * Left-biased onFailure() FP convention dictates that when this class is Failure, it'll perform
 * the onFailure functionality passed as a parameter, but, overall will still return an either
 * object so you chain calls.
 */
inline fun <L, R> Response<L, R>.onFailure(fn: (failure: L) -> Unit): Response<L, R> =
    this.apply { if (this is Response.Failure) fn(a) }

/**
 * Right-biased onSuccess() FP convention dictates that when this class is Success, it'll perform
 * the onSuccess functionality passed as a parameter, but, overall will still return an either
 * object so you chain calls.
 */
inline fun <L, R> Response<L, R>.onSuccess(fn: (success: R) -> Unit): Response<L, R> =
    this.apply { if (this is Response.Success) fn(b) }