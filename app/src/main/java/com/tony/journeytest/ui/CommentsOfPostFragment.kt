package com.tony.journeytest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import com.tony.journeytest.R
import com.tony.journeytest.databinding.FragmentCommentsOfPostBinding
import com.tony.journeytest.entities.Comment
import com.tony.journeytest.viewModels.PostsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommentsOfPostFragment : Fragment() {

    private val viewModel: PostsViewModel by navGraphViewModels(R.id.navigation_main) {
        defaultViewModelProviderFactory
    }

    private lateinit var binding: FragmentCommentsOfPostBinding

    private val commentsOfPostFragmentArgs : CommentsOfPostFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_comments_of_post,
            container, false
        )

        this.binding.viewModel = this.viewModel
        this.binding.lifecycleOwner = this.viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //1. load and populate the comments
        this.viewModel.commentsToDisplay.observe(this.viewLifecycleOwner) { commentsOfSelectedPost ->
            this.setupListView(commentsOfSelectedPost)
        }

        //2. load the selected post from the args
        this.commentsOfPostFragmentArgs.selectedPost.let {
            this.binding.selectedPost = it
            this.viewModel.selectedPost = it
        }

        //3. set listener to pop back to previous destination
        this.binding.postCardView.setOnClickListener{findNavController().popBackStack()}
        this.binding.commentsCard.setOnClickListener{findNavController().popBackStack()}
    }

    /**
     * populate the given comments
     */
    private fun setupListView(commentsOfSelectedPost: List<Comment>) {
        CommentListAdapter().also { adapter ->
            this.binding.commentList.adapter = adapter
            adapter.submitList(commentsOfSelectedPost)
        }
    }
}