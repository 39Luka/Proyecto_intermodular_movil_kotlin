package net.iesochoa.silvia.projecto_intermodular.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import net.iesochoa.silvia.projecto_intermodular.model.HomeUiState
import net.iesochoa.silvia.projecto_intermodular.model.LoginUiState

class HomeViewModel : ViewModel(){

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    fun onSearchBarValueChange(newValue: String){
        _uiState.update { it.copy(searchBarValue = newValue) }

    }

}