package com.example.myreactivesearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.myreactivesearch.network.ApiConfig
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

@FlowPreview
@ExperimentalCoroutinesApi
class MainViewModel : ViewModel() {


    private val accesToken = "sk.eyJ1IjoiYmxhY2tkdWNrNyIsImEiOiJjbGV5cnNoNGgwcTR6M3hwNGlxNzk5M2FsIn0.4PCfqo4roz63d9XARlPhHw"

    val queryChannel = MutableStateFlow("")

    val searchResult = queryChannel
        .debounce(300)
        .distinctUntilChanged()
        .filter { it.trim().isNotEmpty() }
        .mapLatest {
            ApiConfig.provideApiService().getCountry(it, accesToken).features
        }
        .asLiveData()

}