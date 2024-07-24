package motiapps.melodify.core.domain.base

abstract class SuspendUseCase<Input, Output> {

    suspend operator fun invoke(params: Input? = null): Output {
        return execute(params)

//        try {
//            val result = execute(params)
//
//            if (result is Resource<*>) {
//                @Suppress("UNCHECKED_CAST")
//                return (result as Resource<Output>)
//            } else {
//                return (Resource.Success(result))
//            }
//        } catch (e: Exception) {
//            return (Resource.Error(e))
//        }
    }

    protected abstract suspend fun execute(params: Input?): Output
}