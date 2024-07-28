package motiapps.melodify.features.register.domain

import com.google.firebase.auth.FirebaseUser
import motiapps.melodify.core.domain.base.Resource
import motiapps.melodify.core.domain.base.SuspendUseCase
import motiapps.melodify.core.domain.repository.RegisterRepository
import motiapps.melodify.features.register.data.model.RegisterMailInput
import javax.inject.Inject

class RegisterMailUseCase @Inject constructor(
    private val registerRepository: RegisterRepository
) : SuspendUseCase<RegisterMailInput, Resource<FirebaseUser>>() {

    override suspend fun execute(params: RegisterMailInput?): Resource<FirebaseUser> {
        return registerRepository.registerWithEmail(params?.email!!, params.password!!)
    }
}