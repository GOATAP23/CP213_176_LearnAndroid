package org.ananchanon.project.models

import kotlinx.serialization.Serializable

@Serializable
data class PokedexResponse(
    val pokemon_entries: List<PokemonEntry>
)

@Serializable
data class PokemonEntry(
    val entry_number: Int,
    val pokemon_species: PokemonSpecies
)

@Serializable
data class PokemonSpecies(
    val name: String,
    val url: String
)
