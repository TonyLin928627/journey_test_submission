package com.tony.journeytest.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tony.journeytest.repositories.IPostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DownloadViewModel @Inject constructor(
    private val postsRepository: IPostRepository
) : ViewModel() {

//    private val allPosts =  flow {
//        viewModelScope.launch {
//            emit(postsRepository.getAllPosts())
//        }}.asLiveData()

    val _downloadInfo = MutableLiveData<String>("Init value")
    val downloadInfo: LiveData<String> = _downloadInfo
    val isDownloading = MutableLiveData<Boolean>(true)
    val isDownloadSuccess = MutableLiveData<Boolean>(false)

    fun shouldStartDownload(): Boolean{
        return postsRepository.isDownloadingNeeded()
    }

    fun startDownloading() {
        viewModelScope.launch(Dispatchers.IO) {
            postsRepository.deletePostsAndComments()
            _downloadInfo.postValue("Start Downloading")
            try {
                isDownloading.postValue(true)
                val result = postsRepository.downloadPostsAndComments()

                _downloadInfo.postValue("Downloaded successfully. ${result.first} post(s) and ${result.second} comment(s) are downloaded, please click the button below to go next.")
                isDownloadSuccess.postValue(true)

            }catch (exception: Throwable) {

                isDownloadSuccess.postValue(false)
                _downloadInfo.postValue("Failed to download: ${exception.localizedMessage}")

            } finally {
                isDownloading.postValue(false)
            }
        }

    }
}