package com.tony.journeytest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.tony.journeytest.R
import com.tony.journeytest.databinding.FragmentDownloadBinding
import com.tony.journeytest.viewModels.DownloadViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DownloadFragment : Fragment() {
    private lateinit var binding: FragmentDownloadBinding

    private val viewModel: DownloadViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_download,
            container, false
        )
        this.binding.lifecycleOwner = this.viewLifecycleOwner
        this.binding.viewModel = this.viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // check if or not download.
        when (viewModel.shouldStartDownload()){
            true -> {//start download and update UI when download is completed if true
                this.viewModel.startDownloading(context = context!!)

                this.viewModel.isDownloadSuccess.observe(this.viewLifecycleOwner) { isSuccess ->
                    when (isSuccess){
                        true -> this.binding.nextButton.setOnClickListener (Navigation.createNavigateOnClickListener(R.id.navigation_main))
                    }
                }
            }

            false -> {//navigate to next destination if no need to download
                findNavController().navigate(R.id.navigation_main)
            }
        }
    }
}