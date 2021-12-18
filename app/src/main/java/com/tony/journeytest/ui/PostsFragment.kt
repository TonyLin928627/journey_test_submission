package com.tony.journeytest.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.tony.journeytest.R
import com.tony.journeytest.databinding.FragmentPostsBinding
import com.tony.journeytest.entities.Post
import com.tony.journeytest.viewModels.PostsViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PostsFragment : Fragment() {

    private val viewModel: PostsViewModel by navGraphViewModels(R.id.navigation_main) {
        defaultViewModelProviderFactory
    }

    private lateinit var binding: FragmentPostsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_posts,
            container, false
        )
        this.binding.lifecycleOwner = this.viewLifecycleOwner
        this.binding.viewModel = this.viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.viewModel.postsToDisplay.observe(viewLifecycleOwner){ posts ->
            setupListView(posts)
        }
    }

    private fun setupListView(posts: List<Post>){
        PostListAdapter().also { adapter ->
            adapter.onItemClicked = this::onPostSelected
            this.binding.postList.adapter = adapter
            adapter.submitList(posts)
        }
    }

    private fun onPostSelected(selectedPost: Post) {
        val action = PostsFragmentDirections.actionPostsFragmentToCommentsOfPostFragment(selectedPost)
        findNavController().navigate(action)
    }
}