package edu.oregonstate.cs492.finalproject.ui

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import edu.oregonstate.cs492.finalproject.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TMDBDetailFragment : Fragment(R.layout.movie_detail) {
    private val args: TMDBDetailFragmentArgs by navArgs()

    private lateinit var backdropIV: ImageView
    private lateinit var titleTV: TextView
    private lateinit var summaryTitleTV: TextView
    private lateinit var summaryTV: TextView
    private lateinit var savePosterButton: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {



        super.onViewCreated(view, savedInstanceState)

        backdropIV = view.findViewById(R.id.backdrop)
        titleTV = view.findViewById(R.id.title)
        summaryTV = view.findViewById(R.id.summary)
        summaryTitleTV = view.findViewById(R.id.tv_summary_title)
        savePosterButton = view.findViewById(R.id.bt_save_poster)

        val ctx = view.context

        Glide.with(ctx)
            .load(args.movie.backdrop)
            .into(backdropIV)
        titleTV.text = args.movie.title
        summaryTitleTV.text = getString(R.string.label_summary)
        summaryTV.text = args.movie.overview


        //SAVING AN IMAGE, USES MEDIASTORE
        //  https://developer.android.com/reference/android/provider/MediaStore
        savePosterButton.setOnClickListener {
            //Load (w/Glide) the poster into a Bitmap object and call saveImage
            Glide.with(ctx)
                .asBitmap()
                .load(args.movie.poster_path)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(bitmap: Bitmap, transition: Transition<in Bitmap>?) {
                        saveImage(bitmap, ctx)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        //Not sure what to do here?
                    }


                })
        }
    }

    private fun saveImage(bitmap: Bitmap, ctx: Context) {
        //First, name the poster based on the time + movie
        val simpleDateFormat = SimpleDateFormat("yyyymmsshhmmss", Locale.US)
        val date = simpleDateFormat.format(Date())
        val fileName = date + "_${args.movie.title}.jpg"

        //Specifies the context resolver + values (name, file type, location) for MediaStore
        val resolver = ctx.contentResolver
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
        }

        //Inserts image metadata to the external content uri (photo gallery, I think?)
        //  Basically preps for the actual saving
        //  https://developer.android.com/reference/android/content/ContentResolver#insert(android.net.Uri,%20android.content.ContentValues)
        val imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        //Attempts to save the bitmap image to the specific URI specified above
        //  https://developer.android.com/reference/android/content/ContentResolver#openOutputStream(android.net.Uri)
        //  "use" allows the output stream to close after it's used to wire the bitmap to it
        //  Toasts are used to signify to the user the status of the process
        imageUri?.let { uri ->
            resolver.openOutputStream(uri)?.use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                Toast.makeText(ctx, "Image saved to gallery", Toast.LENGTH_SHORT).show()
            } ?: Toast.makeText(ctx, "Failed to save image", Toast.LENGTH_SHORT).show()
        }

    }

}