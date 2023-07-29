package com.nandaadisaputra.retrofit.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nandaadisaputra.retrofit.utils.UiState
import com.nandaadisaputra.retrofit.model.PostModel
import com.nandaadisaputra.retrofit.network.ApiHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val apiHelper: ApiHelper
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<List<PostModel>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<PostModel>>> = _uiState
    fun getBody() {
        viewModelScope.launch(Dispatchers.Main) {
            _uiState.value = UiState.Loading
            apiHelper.getBody()
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    _uiState.value = UiState.Error(e.toString())
                }
                .collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }
}