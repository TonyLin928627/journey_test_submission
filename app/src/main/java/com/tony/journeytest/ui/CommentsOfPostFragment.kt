package com.tony.journeytest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.tony.journeytest.R
import com.tony.journeytest.databinding.FragmentCommentsOfPostBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommentsOfPostFragment : Fragment() {

    private lateinit var binding: FragmentCommentsOfPostBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_comments_of_post,
            container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.commentsOfPostLabel.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}