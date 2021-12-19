package com.example.onepiece

import android.app.Application
import android.graphics.Bitmap
import android.widget.Toast
import androidx.collection.LruCache
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject
import java.net.URLEncoder

class OnepieceViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        const val QUEUE_TAG = "OnepieceVolleyRequest"
        const val SERVER_URL = "https://express-onepiecedb-zjchh.run.goorm.io/"
    }

    data class Person(var id: Int, var name: String, var pirate: String, var image: String, var explanation: String)
    val list = MutableLiveData<ArrayList<Person>>()
    private val person = ArrayList<Person>()

    private var queue: RequestQueue
    val imageLoader: ImageLoader

    init {
        list.value = person
        queue = Volley.newRequestQueue(application)
        imageLoader = ImageLoader(queue,
            object : ImageLoader.ImageCache {
                private val cache = LruCache<String, Bitmap>(100)
                override fun getBitmap(url: String): Bitmap? {
                    return cache.get(url)
                }
                override fun putBitmap(url: String, bitmap: Bitmap) {
                    cache.put(url, bitmap)
                }
            })
    }

    fun getImageUrl(i: Int): String = "$SERVER_URL/image/" +
            URLEncoder.encode(person[i].image,"utf-8")
    fun getPerson(i : Int) = person[i]
    fun getSize() = person.size

    override fun onCleared() {
        super.onCleared()
        queue.cancelAll(QUEUE_TAG)
    }

    fun requestPerson() {
        val url = "https://express-onepiecedb-zjchh.run.goorm.io/"

        val request = JsonArrayRequest(
            Request.Method.GET, url, null,
            {
                person.clear()
                parseJson(it)
                list.value = person

            },
            {
                Toast.makeText(getApplication(), it.toString(),
                Toast.LENGTH_LONG).show()
            }
        )

        request.tag = QUEUE_TAG
        queue.add(request)
    }

    private fun parseJson(items: JSONArray) {
        for (i in 0 until items.length()) {
            val item: JSONObject = items[i] as JSONObject
            val id = item.getInt("id")
            val name = item.getString("name")
            val pirate = item.getString("pirate")
            val image = item.getString("image")
            val explanation = item.getString("explanation")

            person.add(Person(id, name, pirate, image, explanation))
        }
    }

}