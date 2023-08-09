package com.wsl.utils

// (1) import kotlin-reflect extension functions
import kotlin.reflect.*
import kotlin.reflect.full.*
// (2) define alias for Mapper function
typealias Mapper<I, O> = (I) -> O
/**
 * Mapper that can convert one data class into another data class.
 *
 * @param <I> inType (convert from)
 * @param <O> outType (convert to)
 */
// (3) class that gets input and output class and implements Mapper   (-> 2)
class DataClassMapper<I : Any, O : Any>(
    private val inType: KClass<I>,
    private val outType: KClass<O>
) : Mapper<I, O> {

    companion object {
        // (4) provide reified constructor that does not require passing of KClass attributes
        inline operator fun <reified I : Any, reified O : Any> invoke() = DataClassMapper(I::class, O::class)

        fun <I : Any, O : Any> setMapper(mapper: Mapper<I, O>) = object : Mapper<Set<I>, Set<O>> {
            override fun invoke(data: Set<I>): Set<O> =
                data.map(mapper).toSet()
        }
    }
    // (5) get constructor of target data class
    private val outConstructor = outType.constructors.first()
    // (6) get properties from input type (lazily)
    private val inPropertiesByName by lazy { inType.memberProperties.associateBy { it.name } }

    // (8) get value of source data via reflection or call registered converter
    private fun argFor(parameter: KParameter, data: I): Any? {
        // get value from input data ...
        val value = inPropertiesByName[parameter.name]?.get(data) ?: return null

        // if a special mapper is registered, use it, otherwise keep value
        return fieldMappers[parameter.name]?.invoke(value) ?: value
    }
    // (7) call the target constructor with a map of source attributes and values, where argFor defines the value to use (see (8)
    override fun invoke(data: I): O = with(outConstructor) {
        callBy(parameters.associateWith { argFor(it, data) })
    }
    // (9) register converter classes on attribute level to support transitive conversions or collection types
    val fieldMappers = mutableMapOf<String, Mapper<Any, Any>>()
    inline fun <reified S : Any, reified T : Any> register(parameterName: String, crossinline mapper: Mapper<S, T>): DataClassMapper<I, O> = apply {
        // to be able to store the sub-mappers, we have to wrap them as (Any) -> Any
        this.fieldMappers[parameterName] = object : Mapper<Any, Any> {
            override fun invoke(data: Any): Any = mapper.invoke(data as S)
        }
    }

}