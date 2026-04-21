package org.ananchanon.project.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.ananchanon.project.models.PokemonEntry
import org.ananchanon.project.network.PokemonNetwork

class PokemonViewModel : ViewModel() {

    private val _pokemonList = MutableStateFlow<List<PokemonEntry>>(emptyList())
    val pokemonList = _pokemonList.asStateFlow()

    init {
        fetchPokemon()
    }

    fun fetchPokemon() {
        viewModelScope.launch {
            try {
                val response = PokemonNetwork.getKantoPokedex()
                _pokemonList.value = response.pokemon_entries
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
