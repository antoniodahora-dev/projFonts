package co.a3tecnology.fontes

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.widget.TextView
import android.widget.Toast
import androidx.core.provider.FontRequest
import androidx.core.provider.FontsContractCompat


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // aplicando a fonte de forma programatica
        val txtView = findViewById<TextView>(R.id.txtView)
//
//        val typeface = ResourcesCompat.getFont(this, R.font.opensans_italic)
//
//        txtView.typeface = typeface

        val queryBuilder =  QueryBuilder("Lobster",
            width = 100f,
            weight = 400,
            italic = 0f,
            besteffort = true)

        val query = queryBuilder.build()
        val request = FontRequest(
            "com.google.android.gms.fonts" ,
        "com.google.android.gms",
            query,
            R.array.com_google_android_gms_fonts_certs)

        val callback = object : FontsContractCompat.FontRequestCallback() {
            override fun onTypefaceRequestFailed(reason: Int) {
                super.onTypefaceRequestFailed(reason)
                Toast.makeText(
                    this@MainActivity, "Falhou + $reason", Toast.LENGTH_LONG).show()
            }

            override fun onTypefaceRetrieved(typeface: Typeface?) {
                txtView.typeface = typeface
            }
        }

        //em qual thread ele ira fazer a requisão
        val handlerThread = HandlerThread("fonts")
        handlerThread.start()

        val handler = Handler(handlerThread.looper)


        FontsContractCompat.requestFont(this, request, callback, handler)
    }
}