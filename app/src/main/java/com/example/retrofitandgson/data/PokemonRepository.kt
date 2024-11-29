package com.example.retrofitandgson.data

import com.example.retrofitandgson.model.Pockemon
import com.example.retrofitandgson.network.ApiService
import com.example.retrofitandgson.network.PockApi

interface PokemonRepository {
    suspend fun  getAllPokemon():List<Pockemon>
}
class NetworkPokemonRepository(private val pokemonApiService: ApiService
):PokemonRepository{
    override suspend fun getAllPokemon(): List<Pockemon> {
        return pokemonApiService.getAllPokemon()
    }

}