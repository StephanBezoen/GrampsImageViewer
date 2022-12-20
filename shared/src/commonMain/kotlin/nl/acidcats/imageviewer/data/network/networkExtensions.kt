package nl.acidcats.imageviewer.data.network

import io.ktor.client.request.*

fun HttpRequestBuilder.urlFromDef(
    serviceDef: ServiceDef,
    pathOverride:String? = null,
) =
    url(
        scheme = serviceDef.apiScheme,
        host = serviceDef.apiHost,
        path = pathOverride ?: serviceDef.apiPath
    )

