package com.tony.journeytest.viewModels

import androidx.lifecycle.*
import com.tony.journeytest.entities.Comment
import com.tony.journeytest.entities.Post
import com.tony.journeytest.repositories.IPostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val postsRepository: IPostRepository
) : ViewModel()  {

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
                true -> "${it.size.toString()} results found"
                else -> "Search"
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