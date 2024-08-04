package motiapps.melodify.features.loading.presentation

import kotlinx.parcelize.Parcelize
import motiapps.melodify.core.presentation.base.IViewState

@Parcelize
data class LoadingState (
    val isLoading: Boolean = true,
    val error: String? = null,
    val navigateTo: String? = null
)
: IViewState