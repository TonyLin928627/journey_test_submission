package com.tony.journeytest.viewModels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tony.journeytest.R
import com.tony.journeytest.repositories.IPostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DownloadViewModel @Inject constructor(
    private val postsRepository: IPostRepository
) : ViewModel() {

    val downloadInfo = MutableLiveData<String>("")
    val isDownloading = MutableLiveData<Boolean>(true)
    val isDownloadSuccess = MutableLiveData<Boolean>(false)


    fun shouldStartDownload(): Boolean{
        return postsRepository.isDownloadingNeeded()
    }

    fun startDownloading(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            postsRepository.deletePostsAndComments()
            downloadInfo.postValue(context.resources.getString(R.string.downloading))
            try {
                isDownloading.postValue(true)
                val result = postsRepository.downloadPostsAndComments()

                downloadInfo.postValue(context.resources.getString(R.string.downloaded_successfully, result.first, result.second))
                isDownloadSuccess.postValue(true)

            }catch (exception: Throwable) {

                isDownloadSuccess.postValue(false)
                downloadInfo.postValue(context.resources.getString(R.string.downloaded_failed, exception.localizedMessage))

            } finally {
                isDownloading.postValue(false)
            }
        }

    }
}