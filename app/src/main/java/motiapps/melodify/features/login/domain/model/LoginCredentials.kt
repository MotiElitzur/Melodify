package motiapps.melodify.features.login.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginCredentials(
    val email: String = "",
    val password: String = ""
): Parcelable
