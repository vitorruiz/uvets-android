package br.com.uvets.uvetsandroid

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.include_loading_container.*
import pl.aprilapps.easyphotopicker.EasyImage
import java.io.File


inline fun <reified T> Gson.fromJson(json: String) = this.fromJson<T>(json, object : TypeToken<T>() {}.type)

fun ImageView.loadFromFile(imageFile: File) {
    Glide.with(this.context)
        .load(imageFile)
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .into(this)
}

fun ImageView.loadFromUrl(imageUrl: String) {
    Glide.with(this.context)
        .load(imageUrl)
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .into(this)
}

fun ImageView.enableImagePickerOnClick(fragment: Fragment) {
    setOnClickListener {
        EasyImage.openChooserWithGallery(fragment, "Selecione", 1)
    }
}

fun Fragment.loading(isLoading: Boolean) {
    if (loading_container != null) {
        loading_container.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}

fun Fragment.showSuccessToast(message: String?) {
    val toast = Toast(context)
    toast.duration = Toast.LENGTH_LONG

    //inflate view
    val view = layoutInflater.inflate(R.layout.toast_icon_text, null)
    (view.findViewById(R.id.message) as TextView).text = message
    (view.findViewById(R.id.icon) as ImageView).setImageResource(R.drawable.ic_done)
    (view.findViewById(R.id.parent_view) as CardView).setCardBackgroundColor(resources.getColor(R.color.green_500))

    toast.view = view
    toast.show()
}

fun Fragment.showErrorToast(message: String?) {
    val toast = Toast(context)
    toast.duration = Toast.LENGTH_LONG

    //inflate view
    val view = layoutInflater.inflate(R.layout.toast_icon_text, null)
    (view.findViewById(R.id.message) as TextView).text = message
    (view.findViewById(R.id.icon) as ImageView).setImageResource(R.drawable.ic_close)
    (view.findViewById(R.id.parent_view) as CardView).setCardBackgroundColor(resources.getColor(R.color.red_600))

    toast.view = view
    toast.show()
}