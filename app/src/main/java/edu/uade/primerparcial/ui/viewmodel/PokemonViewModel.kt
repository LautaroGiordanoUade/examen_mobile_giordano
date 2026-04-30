package edu.uade.primerparcial.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.uade.primerparcial.domain.model.Pokemon
import edu.uade.primerparcial.domain.usecase.GetPokemonsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Single source of truth for the Pokemon List UI state.
 */
sealed interface PokemonUiState {
    data object Loading : PokemonUiState
    data class Success(val pokemons: List<Pokemon>) : PokemonUiState
    data class Error(val message: String) : PokemonUiState
}

/**
 * User actions modeled as events to maintain Unidirectional Data Flow.
 */
sealed interface PokemonEvent {
    data object LoadPokemons : PokemonEvent
}

class PokemonViewModel(
    private val getPokemonsUseCase: GetPokemonsUseCase = GetPokemonsUseCase()
) : ViewModel() {

    // Internal mutable state, exposed as read-only StateFlow
    private val _uiState = MutableStateFlow<PokemonUiState>(PokemonUiState.Loading)
    val uiState: StateFlow<PokemonUiState> = _uiState.asStateFlow()

    init {
        onEvent(PokemonEvent.LoadPokemons)
    }

    /**
     * Entry point for all UI events.
     */
    fun onEvent(event: PokemonEvent) {
        when (event) {
            PokemonEvent.LoadPokemons -> loadPokemons()
        }
    }

    private fun loadPokemons() {
        viewModelScope.launch {
            _uiState.value = PokemonUiState.Loading
            try {
                // The ViewModel doesn't care WHERE the data comes from (Use Case abstraction)
                val pokemons = getPokemonsUseCase()
                _uiState.value = PokemonUiState.Success(pokemons)
            } catch (e: Exception) {
                _uiState.value = PokemonUiState.Error(
                    e.message ?: "An unexpected error occurred while loading Pokédex"
                )
            }
        }
    }
}
