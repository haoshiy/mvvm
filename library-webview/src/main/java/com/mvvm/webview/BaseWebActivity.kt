package com.mvvm.webview

import android.view.WindowManager
import android.widget.ProgressBar
import androidx.annotation.CallSuper
import androidx.viewbinding.ViewBinding
import com.mvvm.R
import com.mvvm.annotation.Base
import com.mvvm.ui.BaseActivity
import com.mvvm.viewmodel.BaseViewModel
import com.mvvm.webview.view.ProgressAnimHelper
import com.mvvm.webview.view.ProgressWebView
import com.mvvm.webview.view.WebViewLoadListener

@Base
abstract class BaseWebActivity<VB : ViewBinding, VM : BaseViewModel> : BaseActivity<VB, VM>(),
    WebViewLoadListener {

    private var webView: ProgressWebView? = null

    private var progressAnimHelper: ProgressAnimHelper? = null

    @CallSuper
    override fun initView() {
        window.addFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED)
        webView = f(R.id.baseWebView)
        webView?.setWebViewLoadListener(this)
        val progressBar: ProgressBar? = f(R.id.baseProgressBar)
        if (progressBar != null) {
            progressAnimHelper = ProgressAnimHelper(progressBar!!)
        }
    }

    override fun initData() {

    }

    override fun onDestroy() {
        progressAnimHelper?.destroy()
        webView?.destroy()
        super.onDestroy()
    }

    override fun onBackPressed() {
        if (webView != null && webView!!.canGoBack()) {
            webView!!.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun pageFinished() {
    }

    override fun pageLoadError() {

    }

    override fun progressChanged(newProgress: Int) {
        progressAnimHelper?.progressChanged(newProgress)
    }

    fun load(url: String) {
        webView?.doLoadUrl(url)
    }

    companion object {
        internal const val TITLE = "TITLE"
        internal const val URL = "URL"
    }
}

