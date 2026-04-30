package edu.uade.primerparcial.data.remote.dto

import com.google.gson.annotations.SerializedName

data class PokemonDto(
    val id: Int,
    val name: String,
    val sprites: SpritesDto,
    val height: Int,
    val weight: Int,
    val types: List<TypeSlotDto>
)

data class SpritesDto(
    @SerializedName("back_default") val backDefault: String?,
    @SerializedName("front_default") val frontDefault: String?
)

data class TypeSlotDto(
    val slot: Int,
    val type: TypeDto
)

data class TypeDto(
    val name: String
)

data class PokemonListResponse(
    val results: List<PokemonNamedDto>
)

data class PokemonNamedDto(
    val name: String,
    val url: String
)
