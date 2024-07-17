package motiapps.melodify.core.domain.base

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

abstract class UseCase<Input, Output> {

    operator fun invoke(params: Input? = null): Flow<Resource<Output>> = flow {
        try {
            val result = execute(params)
            emit(Resource.Success(result))
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }

    protected abstract suspend fun execute(params: Input?): Output
}