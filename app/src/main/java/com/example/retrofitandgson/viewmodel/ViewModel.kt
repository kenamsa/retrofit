package com.example.retrofitandgson.viewmodel

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.retrofitandgson.model.Pockemon
import com.example.retrofitandgson.network.PockApi
import java.io.IOException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.retrofitandgson.PokemonApplication
import com.example.retrofitandgson.data.NetworkPokemonRepository
import com.example.retrofitandgson.data.PokemonRepository
import com.example.retrofitandgson.network.PockApi.retrofitService
import kotlinx.coroutines.launch


sealed interface PockUiState {
    data class Success(val pokemons: List<Pockemon>) : PockUiState
    object Error : PockUiState
    object Loading : PockUiState
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
class PokViewModel(private val pokemonRepository: PokemonRepository):ViewModel(){
/** The mutable State that stores the status of the most recent request */
    var pockUiState: PockUiState by mutableStateOf(PockUiState.Loading)
        private set

    /**
     */
    init {
        getAllPock()
    }
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun getAllPock() {
        viewModelScope.launch {

            pockUiState = try {

                val listResult = pokemonRepository.getAllPokemon()
                PockUiState.Success(
                    listResult
                )
            } catch (e: IOException) {
                PockUiState.Error
            } catch (e: HttpException) {
                PockUiState.Error
            }
        }
    }
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as PokemonApplication)
                val pokemonRepository = application.container.pokemonRepository
                PokViewModel(pokemonRepository = pokemonRepository)
            }
        }
    }
}