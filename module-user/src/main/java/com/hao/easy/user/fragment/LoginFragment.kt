package com.hao.easy.user.fragment

import android.text.TextUtils
import androidx.navigation.Navigation
import com.hao.easy.user.R
import com.hao.easy.user.databinding.UserFragmentLoginBinding
import com.hao.easy.user.viewmodel.LoginViewModel
import com.mvvm.annotation.AndroidEntryPoint
import com.mvvm.extensions.addTextChangedListener
import com.mvvm.extensions.hideSoftInput
import com.mvvm.extensions.showError
import com.mvvm.ui.BaseFragment

/**
 * @author Yang Shihao
 */
@AndroidEntryPoint
class LoginFragment : BaseFragment<UserFragmentLoginBinding, LoginViewModel>() {

    override fun initView() {
        activity?.setTitle(R.string.user_login)
        viewBinding {
            editTextUsername.addTextChangedListener(textInputUsername)
            editTextPassword.addTextChangedListener(textInputPassword)
            buttonLogin.setOnClickListener {
                login()
            }
            textRegister.setOnClickListener {
                Navigation.findNavController(root).navigate(R.id.toRegisterFragment)
            }
        }
    }

    private fun login() {
        viewBinding {
            val username = editTextUsername.text.toString().trim()
            val password = editTextPassword.text.toString().trim()
            if (TextUtils.isEmpty(username)) {
                textInputUsername.showError(R.string.user_username_is_empty)
                return@viewBinding
            }
            if (TextUtils.isEmpty(password)) {
                textInputPassword.showError(R.string.user_password_is_empty)
                return@viewBinding
            }
            viewModel {
                login(username, password)
            }
            hideSoftInput()
        }
    }

    override fun initData() {
        viewModel {
            loginLiveData.observe(this@LoginFragment) {
                activity?.finish()
            }
        }
    }
}
