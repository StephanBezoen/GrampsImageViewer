package nl.acidcats.imageviewer.data.network

sealed class ApiResult<out T> {
    data class Error(val throwable: Throwable) : ApiResult<Nothing>()
    data class Success<out T>(val data: T) : ApiResult<T>()
}