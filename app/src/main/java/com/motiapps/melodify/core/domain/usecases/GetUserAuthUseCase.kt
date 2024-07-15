package com.motiapps.melodify.core.domain.usecases

import com.google.firebase.auth.FirebaseUser
import com.motiapps.melodify.core.domain.repository.AuthRepository
import javax.inject.Inject

class GetUserAuthUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): FirebaseUser? {
        return authRepository.getCurrentUser()
    }
}