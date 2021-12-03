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

                    // Once the resource (Drawable Image) is ready generate the Palette from the bitmap of image.
                    Palette.from(resource.toBitmap())

                        // We are GENERATING colors from the bitmap
                        .generate { palette ->

                            // Get the vibrantSwatch color from the image and set it to the parent layout background.
                            palette?.let {

                                // Store the RGB value instead the intColor variable
                                /**
                                 * Type of Swatches:
                                 *
                                 *
                                 * If it is a value then use it, otherwise use 0
                                 *
                                 * vibrantSwatch = Returns the most vibrant swatch in the palette.
                                 * Might be null, that's why we need to check if it is NULL using:
                                 *          it.vibrantSwatch?.rgb ?: 0
                                 */
                                val intColor = it.vibrantSwatch?.rgb ?: 0

                                // Change background color based on the fetched RGB value
                                // setBackgroundColor requires an Int value, and our rgb() is already returning result as an integer value
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