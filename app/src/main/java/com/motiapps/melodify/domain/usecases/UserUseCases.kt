package com.motiapps.melodify.domain.usecases

import javax.inject.Inject

data class UserUseCases @Inject constructor(
    val getUser: GetUserUseCase,
    val insertUser: InsertUserUseCase,
)

//class UserUseCases @Inject constructor(
//    private val userId: String,
//    private val userRepository: UserRepository
//) {
//
//    suspend operator fun invoke() {
//        userRepository.getUserById(userId)
//    }
//}