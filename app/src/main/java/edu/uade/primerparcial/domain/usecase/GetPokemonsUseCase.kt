package edu.uade.primerparcial.domain.usecase

import edu.uade.primerparcial.data.PokemonRepository
import edu.uade.primerparcial.domain.model.Pokemon

class GetPokemonsUseCase(
    private val repository: PokemonRepository = PokemonRepository()
) {
    operator fun invoke(): List<Pokemon> = repository.getPokemons()
}
