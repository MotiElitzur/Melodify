package motiapps.melodify.features.login.presentaion

import kotlinx.parcelize.Parcelize
import motiapps.melodify.core.presentation.base.IViewState
import motiapps.melodify.features.login.domain.model.LoginCredentials

@Parcelize
data class LoginState (
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val emailTouched: Boolean = false,
    val passwordTouched: Boolean = false,
    val confirmPasswordTouched: Boolean = false,
    val isLoginEnabled: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
    val navigationRoute: String? = null
) : IViewState