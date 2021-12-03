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

        // viewBinding = Inflating XML Layout using viewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Load the image from a random link from google using the Glide Library.
        Glide.with(this)

            // Image Source: To check the error you can change the link by adding some char
            .load("https://cdn.pixabay.com/photo/2018/05/03/21/49/android-3372580_960_720.png")

            // Add the listener: Once the image will be downloaded then take following action:
            .listener(object : RequestListener<Drawable> {

                // Failure: Log the Error message if image will be failed to download.
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

                // Success: Load time thumbnail in our ImageView
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

                                // Store the RGB value instead the intColor variable
                                val intColor = it.vibrantSwatch?.rgb ?: 0

                                // Change background color based on the fetched RGB value
                                binding.targetTheme.setBackgroundColor(intColor)
                            }

                        }

                    return false
                }
            })

            // Load image into the ImageView
            .into(binding.imgReference)

    }
}