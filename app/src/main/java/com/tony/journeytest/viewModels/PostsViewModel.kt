package com.tony.journeytest.viewModels

import android.content.Context
import androidx.lifecycle.*
import com.tony.journeytest.R
import com.tony.journeytest.entities.Comment
import com.tony.journeytest.entities.Post
import com.tony.journeytest.repositories.IPostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val postsRepository: IPostRepository,
    context: Context
) : ViewModel()  {

    private val searchText = context.resources.getText(R.string.search)
    private val resultFoundText = context.getText(R.string.result_found)

    val searchKey = MutableLiveData<String>("")

    val postsToDisplay = searchKey.switchMap { searchKey ->
            when (searchKey.isNotBlank()) {
                true -> postsRepository.getPostsWithSearchKey(searchKey = searchKey).asLiveData()
                false -> postsRepository.getAllPosts().asLiveData()
            }

    }

    val searchResultCount = postsToDisplay.switchMap {
        MutableLiveData<String?>().apply {
            when (searchKey.value?.isNotBlank()) {
                true -> "${it.size} $resultFoundText"
                else -> searchText.toString()
            }.let {
                postValue(it)
            }
        }
    }

    private val _selectedPost = MutableLiveData<Post?>(null)
    val commentsOfSelectedPost: LiveData<List<Comment>> = _selectedPost.switchMap { selectedPost ->
        MutableLiveData<List<Comment>>().apply {
            selectedPost?.let {
                viewModelScope.launch(Dispatchers.IO){
                    postValue(postsRepository.getCommentsOfPost(it))
                }

            } ?: this.postValue(emptyList())

        }
    }

    var selectedPost: Post? = null
    set(value) { field = value; this._selectedPost.postValue(value)}
}