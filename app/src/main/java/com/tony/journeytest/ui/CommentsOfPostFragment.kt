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
import com.tony.journeytest.databinding.FragmentPostsBinding
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

        this.binding.lifecycleOwner = this.viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.viewModel.commentsOfSelectedPost.observe(this.viewLifecycleOwner) { commentsOfSelectedPost ->
            this.setupListView(commentsOfSelectedPost)
        }

        this.commentsOfPostFragmentArgs.selectedPost.let {
            this.binding.selectedPost = it
            this.viewModel.selectedPost = it
        }

        this.binding.postCardView.setOnClickListener{findNavController().popBackStack()}
    }

    private fun setupListView(commentsOfSelectedPost: List<Comment>) {
        CommentListAdapter().also { adapter ->
            this.binding.commentList.adapter = adapter
            adapter.submitList(commentsOfSelectedPost)
        }
    }
}