@file:Suppress("EXPERIMENTAL_API_USAGE")

package com.spyrdonapps.common.model

import kotlinx.datetime.Instant
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object ApiEndpoints {
    const val login = "/api/auth/login"
    const val register = "/api/auth/register"
    const val refreshToken = "/api/auth/refreshtoken"
    const val weighings = "/api/weighings"
    const val weighingsAdd = "/api/weighings/add"
}

@Serializer(forClass = Instant::class)
internal object InstantSerializer : KSerializer<Instant> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("kotlinx.datetime.Instant", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Instant) {
        encoder.encodeString(value.toString())
    }

    override fun deserialize(decoder: Decoder): Instant =
        Instant.parse(decoder.decodeString())
}

@Serializable
data class Weighing(
    val weight: Float,
    @Serializable(with = InstantSerializer::class) val date: Instant
)

@Serializable
data class Product(val protein: Float, val carbs: Float, val fat: Float, val company: String?)

@Serializable
data class Meal(
    val productsWithWeights: List<ProductWithWeight>,
    @Serializable(with = InstantSerializer::class) val date: Instant
)

// join
@Serializable
data class ProductWithWeight(val product: Product, val meal: Meal, val weight: Float) {
    val kcal: Float
        get() = CalorieUtils.calculateKcal(this)

    val protein: Float
        get() = weightMultiplier * product.protein

    val carbs: Float
        get() = weightMultiplier * product.carbs

    val fat: Float
        get() = weightMultiplier * product.fat

    val weightMultiplier: Float
        get() = weight / 100
}

@Serializable
data class UserCredentials(val username: String, val password: String)

@Serializable
data class TokenData(val accessToken: String, val refreshToken: String)

@Serializable
data class RefreshTokenRequest(val refreshToken: String)

object CalorieUtils {

    private const val kcalPerProteinGram = 4
    private const val kcalPerCarbsGram = 4
    private const val kcalPerFatGram = 9

    fun calculateKcal(productWithWeight: ProductWithWeight): Float =
        with(productWithWeight) {
            val kcalFromProtein = product.protein * kcalPerProteinGram
            val kcalFromCarbs = product.carbs * kcalPerCarbsGram
            val kcalFromFat = product.fat * kcalPerFatGram
            weightMultiplier * (kcalFromProtein + kcalFromCarbs + kcalFromFat)
        }
}
