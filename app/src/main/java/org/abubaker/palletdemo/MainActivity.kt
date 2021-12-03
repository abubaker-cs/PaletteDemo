package org.abubaker.palletdemo

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import org.abubaker.palletdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Load the image from a random link from google.
        Glide.with(this)

            // To check the error you can change the link by adding some char
            .load("https://cdn.pixabay.com/photo/2018/05/03/21/49/android-3372580_960_720.png")

            // Add the listener
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    @Nullable e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {

                    // log exception
                    Log.e("TAG", "Error loading image", e)

                    // important to return false so the error placeholder can be placed
                    return false

                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {

                    // Once the resource is ready generate the Palette from the bitmap of image.
                    Palette.from(resource.toBitmap())
                        .generate { palette ->

                            // Get the vibrantSwatch color from the image and set it to the parent layout background.
                            palette?.let {
                                val intColor = it.vibrantSwatch?.rgb ?: 0
                                binding.targetTheme.setBackgroundColor(intColor)
                            }

                        }

                    return false
                }
            })

            .into(binding.imgReference)

    }
}