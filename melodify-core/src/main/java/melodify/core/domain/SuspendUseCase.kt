package melodify.core.domain

abstract class SuspendUseCase<Input, Output> {

    suspend operator fun invoke(params: Input? = null): Output {
        return execute(params)
    }

    protected abstract suspend fun execute(params: Input?): Output
}