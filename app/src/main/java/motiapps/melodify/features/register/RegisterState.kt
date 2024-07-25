package motiapps.melodify.features.register

import kotlinx.parcelize.Parcelize
import motiapps.melodify.core.presentation.base.IViewState

@Parcelize
data class RegisterState (
    val isLoading: Boolean,
    val error: String? = null,
    val route: String? = null,
)
: IViewState