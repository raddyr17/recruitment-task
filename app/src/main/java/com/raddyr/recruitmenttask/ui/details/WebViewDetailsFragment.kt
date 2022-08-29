package com.raddyr.recruitmenttask.ui.details

import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.transition.MaterialContainerTransform
import com.raddyr.recruitmenttask.R
import com.raddyr.recruitmenttask.databinding.FragmentWebviewDetailsLayoutBinding
import com.raddyr.recruitmenttask.util.extensions.hide
import com.raddyr.recruitmenttask.util.extensions.show
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class WebViewDetailsFragment : Fragment(R.layout.fragment_webview_details_layout) {

    private var _binding: FragmentWebviewDetailsLayoutBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<WebViewDetailsFragmentArgs>()
    private val timerTask = Runnable() { showLoadingProblem() }
    private val timeoutHandler = Handler(Looper.getMainLooper())
    private var webViewPreviousState: WebViewState? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWebviewDetailsLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding.webview.settings) {
            loadsImagesAutomatically = true
            javaScriptEnabled = true
        }
        binding.webview.webViewClient = object : WebViewClient() {
            var loadingFinished = false

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                webViewPreviousState = WebViewState.PageError
                super.onReceivedError(view, request, error)
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                loadingFinished = false
                webViewPreviousState = WebViewState.PageStartLoading
                showLoading()
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                loadingFinished = true
                if (webViewPreviousState == WebViewState.PageStartLoading) {
                    hideLoading()
                } else {
                    showLoadingProblem()
                }
            }
        }
        binding.ivCloseBtn.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnReload.setOnClickListener {
            handleUrl(args.url)
        }

        handleUrl(args.url)
    }

    private fun handleUrl(url: String?) {
        url?.let {
            binding.webview.loadUrl(url)
            setupTimer()
        }
    }

    private fun setupTimer() {
        timeoutHandler.postDelayed(timerTask, LOADING_TIMEOUT_MILLISECONDS)
    }

    private fun showLoading() {
        with(binding) {
            pbLoader.show()
            llLoadingProblems.hide()
        }
    }

    private fun hideLoading() {
        timeoutHandler.removeCallbacks(timerTask)
        with(binding) {
            webview.show()
            llLoadingProblems.hide()
            pbLoader.hide()
        }
    }

    private fun showLoadingProblem() {
        timeoutHandler.removeCallbacks(timerTask)
        with(binding) {
            webview.hide()
            pbLoader.hide()
            llLoadingProblems.show()
        }
    }

    override fun onStop() {
        super.onStop()
        timeoutHandler.removeCallbacks(timerTask)
    }

    override fun onDestroy() {
        binding.webview.destroy()
        _binding = null
        super.onDestroy()
    }

    enum class WebViewState {
        PageStartLoading, PageError
    }

    companion object {
        private const val LOADING_TIMEOUT_MILLISECONDS = 15000L
    }
}