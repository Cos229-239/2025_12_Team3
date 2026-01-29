import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import kotlin.coroutines.resume

object SteamApi {
    private val client = OkHttpClient()

    suspend fun fetchGameDetails(appId: String): String {
        val url = "https://store.steampowered.com/api/appdetails?appids=$appId"

        val request = Request.Builder()
            .url(url)
            .build()

        return suspendCancellableCoroutine { cont ->
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    cont.resumeWith(Result.failure(e))
                }

                override fun onResponse(call: Call, response: Response) {
                    cont.resume(response.body?.string().orEmpty())
                }
            })
        }
    }
}