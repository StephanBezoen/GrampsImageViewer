package nl.acidcats.imageviewer.data.usecase

interface UseCase<I : Any?, O : Any?> {
    operator fun invoke(param: I): O
}

interface SuspendingUseCase<I : Any?, O : Any?> {
    suspend operator fun invoke(param: I): O
}
