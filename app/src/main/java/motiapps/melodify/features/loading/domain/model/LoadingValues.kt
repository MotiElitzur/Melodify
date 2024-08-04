package motiapps.melodify.features.loading.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoadingValues(
    val userId: String? = null
): Parcelable
