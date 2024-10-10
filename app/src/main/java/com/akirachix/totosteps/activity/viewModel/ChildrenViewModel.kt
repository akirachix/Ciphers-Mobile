package com.akirachix.totosteps.activity.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.akirachix.totosteps.models.Child
import com.akirachix.totosteps.ChildrenRepository
import kotlinx.coroutines.launch

class ChildrenViewModel(
    private val repository: ChildrenRepository
) : ViewModel() {
    private val _children = MutableLiveData<List<Child>>()
    val children: LiveData<List<Child>> = _children

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun loadChildren() {
        viewModelScope.launch {
            try {
                val result = repository.getChildren()
                result.fold(
                    onSuccess = { childrenList ->
                        _children.value = childrenList
                    },
                    onFailure = { exception ->
                        _error.value = exception.message ?: "An error occurred"
                    }
                )
            } catch (e: Exception) {
                _error.value = e.message ?: "An error occurred"
            }
        }
    }

    fun refreshChildren() {
        loadChildren()
    }
}

class ChildrenViewModelFactory(private val repository: ChildrenRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChildrenViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ChildrenViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}