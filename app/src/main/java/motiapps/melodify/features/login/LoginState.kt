package motiapps.melodify.features.login

import kotlinx.parcelize.Parcelize
import motiapps.melodify.core.presentation.base.IViewState

@Parcelize
data class LoginState (
    val isLoading: Boolean,
    val error: String? = null,
    val route: String? = null,
)
: IViewState