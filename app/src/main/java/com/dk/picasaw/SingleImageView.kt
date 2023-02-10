package com.dk.picasaw

import android.app.WallpaperManager
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dk.picasaw.models.RandomImg
import com.squareup.picasso.Picasso

class SingleImageView : AppCompatActivity() {
    private lateinit var imageUri: String
    lateinit var imageView:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.single_image_view)
        if (supportActionBar != null) {
            supportActionBar?.hide();
        }
        val data=intent.getStringExtra("Imgurl")
        imageView=findViewById(R.id.single_image)
        Picasso.get().load(data).into(imageView)
        val wallpaperManager=WallpaperManager.getInstance(applicationContext)
        findViewById<Button>(R.id.setBG).setOnClickListener(View.OnClickListener {
            try{
                val bitmap=(imageView.drawable as BitmapDrawable).bitmap
                wallpaperManager.setBitmap(bitmap)
                Toast.makeText(applicationContext, "Wallpaper set successfully!", Toast.LENGTH_SHORT).show()
            }
            catch (e:Exception){
                e.printStackTrace()
                Toast.makeText(applicationContext, "Failed to set wallpaper!", Toast.LENGTH_SHORT).show()
            }
        })
    }

}