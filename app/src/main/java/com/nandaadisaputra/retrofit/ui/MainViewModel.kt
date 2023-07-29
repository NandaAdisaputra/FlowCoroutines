package com.nandaadisaputra.retrofit.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nandaadisaputra.retrofit.utils.UiState
import com.nandaadisaputra.retrofit.model.PostModel
import com.nandaadisaputra.retrofit.network.ApiHelper
import com.nandaadisaputra.retrofit.room.DatabaseHelper
import com.nandaadisaputra.retrofit.room.entity.Body
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dbHelper: DatabaseHelper,
    private val apiHelper: ApiHelper
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<List<Body>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Body>>> = _uiState
    fun getBody() {
        viewModelScope.launch(Dispatchers.Main) {
            _uiState.value = UiState.Loading
            dbHelper.getBody()
                .flatMapConcat { bodyFromDb ->
                    if (bodyFromDb.isEmpty()) {
                        return@flatMapConcat apiHelper.getBody()
                            .map { apiBodyList ->
                                val bodyList = mutableListOf<Body>()
                                for (apiBody in apiBodyList) {
                                    val body = Body(
                                        apiBody.id,
                                        apiBody.title,
                                        apiBody.body
                                    )
                                    bodyList.add(body)
                                }
                                bodyList
                            }
                            .flatMapConcat { bodyToInsertInDB ->
                                dbHelper.insertAll(bodyToInsertInDB)
                                    .flatMapConcat {
                                        flow {
                                            emit(bodyToInsertInDB)
                                        }
                                    }
                            }
                    } else {
                        return@flatMapConcat flow {
                            emit(bodyFromDb)
                        }
                    }
                }
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