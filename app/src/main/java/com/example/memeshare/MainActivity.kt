package com.example.memeshare

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.airbnb.lottie.LottieAnimationView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.JsonRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

var imgUrl : String? = null
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadmeme()
    }
    private fun loadmeme()
    {
        val pb: ProgressBar = findViewById(R.id.progressBar)
        val an: LottieAnimationView = findViewById(R.id.anim)
        pb.visibility = View.VISIBLE
        val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.herokuapp.com/gimme"
        val jsonObjectRequest =JsonObjectRequest(
            Request.Method.GET, url,null,
            Response.Listener{ response ->
                imgUrl = response.getString("url")
                val img: ImageView = findViewById(R.id.img1)
                Glide.with(this).load(imgUrl).listener(object:RequestListener<Drawable>{
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                       pb.visibility= View.GONE
                        an.visibility=View.GONE
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        pb.visibility= View.GONE
                        an.visibility=View.GONE
                        return false
                    }

                }).into(img)
            }, Response.ErrorListener { } )

        queue.add(jsonObjectRequest)

    }
    fun sharememe(view: View) {
            val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT ,"Checkout this meme $imgUrl" )
        var chooser = Intent.createChooser(intent, "share this meme using ..")
        startActivity(chooser)
    }
    fun nextmeme(view: View) {
        val an: LottieAnimationView = findViewById(R.id.anim)
        an.visibility=View.VISIBLE
    loadmeme()
    }
}