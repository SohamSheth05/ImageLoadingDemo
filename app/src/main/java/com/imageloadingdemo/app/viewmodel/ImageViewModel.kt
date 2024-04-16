package com.imageloadingdemo.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.imageloadingdemo.app.model.UnSplashImageDataItem
import com.imageloadingdemo.app.paging.UnSplashImagePagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageViewModel @Inject constructor(private val unSplashImagePagingSource: UnSplashImagePagingSource) :
    ViewModel() {

    private val _unsplashResponse: MutableStateFlow<PagingData<UnSplashImageDataItem>> =
        MutableStateFlow(PagingData.empty())
    var unSplashResponse = _unsplashResponse.asStateFlow()
        private set

    init {
        viewModelScope.launch {
            Pager(
                config = PagingConfig(
                    10, enablePlaceholders = true
                )
            ) {
                unSplashImagePagingSource
            }.flow.cachedIn(viewModelScope).collect {
                _unsplashResponse.value = it
            }
        }
    }
}