package br.com.uvets.uvetsandroid

import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.include_loading_container.*
import kotlinx.android.synthetic.main.login_fragment.*
import java.io.File


inline fun <reified T> Gson.fromJson(json: String) = this.fromJson<T>(json, object : TypeToken<T>() {}.type)

fun ImageView.loadFromFile(imageFile: File) {
//    Glide.with(this.context)
//        .load(imageFile)
//        .into(this)
}

fun ImageView.enableImagePickerOnClick(fragment: Fragment) {
    setOnClickListener {
        //        Matisse.from(fragment)
//            .choose(MimeType.ofImage())
//            .maxSelectable(1)
//            //.addFilter(GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
//            //.gridExpectedSize(resources.getDimensionPixelSize(R.dimen.grid_expected_size))
//            //.restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
//            .thumbnailScale(0.85f)
//            .imageEngine(PicassoEngine())
//            .forResult(123)
    }
}

fun Fragment.loading(isLoading: Boolean) {
    if (loading_container != null) {
        loading_container.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}

fun Fragment.showError(errorMessage: String) {
    if (coordinator != null) {
        Snackbar.make(coordinator, errorMessage, Snackbar.LENGTH_SHORT).show()
    } else {
        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
    }
}