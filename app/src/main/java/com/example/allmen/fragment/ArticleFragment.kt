package com.example.allmen.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.example.allmen.databinding.FragmentArticleBinding

class ArticleFragment : Fragment() {

    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticleBinding.inflate(inflater, container, false)
        val view = binding.root

        configWebView()

        return view
    }

    private fun configWebView() {

        binding.webView.loadUrl("https://www.diabetes.org/")

        val webSettings = binding.webView.settings
        webSettings.javaScriptEnabled = true

        binding.webView.webViewClient = WebViewClient()
    }

}