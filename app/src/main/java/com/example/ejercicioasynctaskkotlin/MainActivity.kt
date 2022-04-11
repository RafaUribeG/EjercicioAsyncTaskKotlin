package com.example.ejercicioasynctaskkotlin

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.net.URL


class MainActivity : AppCompatActivity() {

    lateinit var txtHilo1: TextView
    lateinit var progressBar2: ProgressBar
    lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT > 9) {
            val policy = ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }

        var btnDescargarImagen:Button = findViewById(R.id.btnDescargarImagen)
        txtHilo1 = findViewById(R.id.txtHilo1)
        imageView = findViewById(R.id.imageView)
        progressBar2 = findViewById(R.id.progressBar2)


        btnDescargarImagen.setOnClickListener {
            DescargarImagen().execute(
                "http://cdn.pixabay.com/photo/2021/08/25/20/42/field-6574455__480.jpg")
        }
    }

    inner class DescargarImagen:AsyncTask<String, Int, Bitmap>(){
        override fun onPreExecute() {
            super.onPreExecute()
        }


        override fun doInBackground(vararg p0: String): Bitmap {
            //txtHilo1.text = "Descarga de la imagen Inicia..."
            publishProgress()
            val url = URL(p0[0])
            val input = url.openStream()
            val imagen: Bitmap = BitmapFactory.decodeStream(input)

            return imagen
        }

        override fun onProgressUpdate(vararg values: Int?) {
            progressBar2.visibility = View.VISIBLE
            super.onProgressUpdate(*values)
        }

        override fun onPostExecute(result: Bitmap) {
            progressBar2.visibility = View.GONE
            imageView.setImageBitmap(result)
            Toast.makeText(applicationContext, "Imagen Descargada ", Toast.LENGTH_SHORT).show()
            super.onPostExecute(result)
        }

    }
}