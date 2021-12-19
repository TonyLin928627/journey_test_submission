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

    //region posts
    val postSearchKey = MutableLiveData<String>("")
    val postsToDisplay = postSearchKey.switchMap { searchKey ->
            when (searchKey.isNotBlank()) {
                true -> postsRepository.getPostsWithSearchKey(searchKey = searchKey).asLiveData()
                false -> postsRepository.getAllPosts().asLiveData()
            }

    }
    val postSearchResultCount = postsToDisplay.switchMap {
        MutableLiveData<String?>().apply {
            when (postSearchKey.value?.isNotBlank()) {
                true -> "${it.size} $resultFoundText"
                else -> searchText.toString()
            }.let {
                postValue(it)
            }
        }
    }
    //endregion

    //region comments of selected post
    private val _selectedPost = MutableLiveData<Post?>(null)
    var selectedPost: Post? = null
    set(value) { field = value; this._selectedPost.postValue(value)}

    private val commentsOfSelectedPost: LiveData<List<Comment>> = _selectedPost.switchMap { selectedPost ->
        MutableLiveData<List<Comment>>().apply {
            selectedPost?.let {
                viewModelScope.launch(Dispatchers.IO){
                    postValue(postsRepository.getCommentsOfPost(it))
                }

            } ?: this.postValue(emptyList())

        }
    }

    val commentSearchKey = MutableLiveData<String>("")

    val commentsToDisplay = commentSearchKey.switchMap { searchKey ->
        when (searchKey.isNotBlank()){
            false -> commentsOfSelectedPost
            true -> {
                MutableLiveData<List<Comment>>().apply {
                    selectedPost?.let {
                        viewModelScope.launch(Dispatchers.IO){
                            postValue(postsRepository.getCommentsOfPostWithSearchKey(post = it, searchKey = searchKey))
                        }

                    } ?: this.postValue(emptyList())

                }
            }
        }
    }
    val searchCommentsResultCount = commentsToDisplay.switchMap {
        MutableLiveData<String?>().apply {
            when (commentSearchKey.value?.isNotBlank()) {
                true -> "${it.size} $resultFoundText"
                else -> searchText.toString()
            }.let {
                postValue(it)
            }
        }
    }
    //endregion
}
