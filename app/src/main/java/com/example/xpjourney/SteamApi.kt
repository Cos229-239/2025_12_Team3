import android.util.Log
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

object SteamApi {
    private val client = OkHttpClient()

    fun fetchGameDetails(appId: String) {
        val url = "https://store.steampowered.com/api/appdetails?appids=$appId"

        val request = Request.Builder().url(url).build()

        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                Log.e("SteamAPI", "Request failed", e)
            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                response.body?.string()?.let {
                    Log.d("SteamAPI", it)
                }
            }
        })
    }
}