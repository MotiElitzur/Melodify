package motiapps.melodify.features.login.presentaion

import kotlinx.parcelize.Parcelize
import motiapps.melodify.core.presentation.base.IViewState

@Parcelize
data class LoginState (
    val isLoading: Boolean,
    val error: String? = null,
    val route: String? = null,
    var email: String? = null,
    val password: String? = null,
)
: IViewState