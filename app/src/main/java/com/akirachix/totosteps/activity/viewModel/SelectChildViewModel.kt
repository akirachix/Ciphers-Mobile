package com.akirachix.totosteps.activity.viewModel

import androidx.lifecycle.*
import com.akirachix.totosteps.models.Child
import com.akirachix.totosteps.ChildrenRepository
import kotlinx.coroutines.launch

class SelectChildViewModel(
    private val repository: ChildrenRepository
) : ViewModel() {
    private val _children = MutableLiveData<List<Child>>()
    val children: LiveData<List<Child>> = _children

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _selectedChild = MutableLiveData<Child>()
    val selectedChild: LiveData<Child> = _selectedChild

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

    fun selectChild(child: Child) {
        _selectedChild.value = child
    }
}

class SelectChildViewModelFactory(private val repository: ChildrenRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SelectChildViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SelectChildViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
