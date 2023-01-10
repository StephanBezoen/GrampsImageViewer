-keepattributes *Annotation*

-dontnote kotlinx.serialization.SerializationKt
-dontnote kotlinx.serialization.AnnotationsKt

-keepnames class kotlinx.** { *; }

-keepclassmembernames class kotlinx.** {
    *;
}

## Ktor
-keep class io.ktor.** { *; }
-keep class kotlinx.coroutines.** { *; }
-dontwarn kotlinx.atomicfu.**
-dontwarn io.netty.**
-dontwarn com.typesafe.**
-dontwarn org.slf4j.**

-keepclassmembers class nl.acidcats.imageviewer.android.MyApplication { <methods>; }

-keep class nl.acidcats.imageviewer.data.network.assets.** { <fields>; }
-keep,includedescriptorclasses class nl.acidcats.imageviewer.data.network.assets.**$$serializer { *; }
-keepclassmembers class nl.acidcats.imageviewer.data.network.assets.** {
    *** Companion;
}
-keepclasseswithmembers class nl.acidcats.imageviewer.data.network.assets.** {
    kotlinx.serialization.KSerializer serializer(...);
}

-keep class nl.acidcats.imageviewer.data.model.** { <fields>; }

-keepclassmembers class kotlinx.serialization.json.** {
    *** Companion;
}
-keepclasseswithmembers class kotlinx.serialization.json.** {
    kotlinx.serialization.KSerializer serializer(...);
}

-keepclassmembers class * extends java.lang.Enum {
    <fields>;
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keepclassmembers enum * {
    public *;
}

-dontwarn java.lang.management.ManagementFactory
-dontwarn java.lang.management.RuntimeMXBean
-dontwarn org.bouncycastle.jsse.BCSSLParameters
-dontwarn org.bouncycastle.jsse.BCSSLSocket
-dontwarn org.bouncycastle.jsse.provider.BouncyCastleJsseProvider
-dontwarn org.conscrypt.Conscrypt$Version
-dontwarn org.conscrypt.Conscrypt
-dontwarn org.conscrypt.ConscryptHostnameVerifier
-dontwarn org.openjsse.javax.net.ssl.SSLParameters
-dontwarn org.openjsse.javax.net.ssl.SSLSocket
-dontwarn org.openjsse.net.ssl.OpenJSSE