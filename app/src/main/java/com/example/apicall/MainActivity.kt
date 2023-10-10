package com.example.apicall

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.apicall.databinding.ActivityMainBinding
import java.net.HttpURLConnection
import java.net.URL
import java.util.Scanner


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun getQuote(view: View) {
        val thread = Thread {

            val url = URL(API_URL)
            try {
                val connection = url.openConnection() as HttpURLConnection
                connection.connectTimeout = 10000
                connection.readTimeout = 10000
                connection.requestMethod = "GET"
                connection.connect()


                val responseCode = connection.responseCode

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val scanner = Scanner(connection.inputStream)
                    scanner.useDelimiter("\\A")
                    val jsonData = if (scanner.hasNext()) scanner.next() else ""

                    var quote = parseJson(jsonData)

                    updateTextBoxFromThread(quote)
                } else {
                    updateTextBoxFromThread

                    ("Sorry, there's a problem with the server")
                }
            } else {

            Log.wtf(
                "MAIN_ACTIVITY",

                "Someone changed the API protocol"
            )

        }
        }
        catch(e: IOException) {

            updateTextBoxFromThread

            ("Sorry, there was an error processing the data")

        }
    }
}