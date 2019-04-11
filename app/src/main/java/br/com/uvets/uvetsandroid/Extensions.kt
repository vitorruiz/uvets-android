package br.com.uvets.uvetsandroid

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import br.com.uvets.uvetsandroid.ui.base.BaseFragment
import com.blankj.utilcode.util.KeyboardUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.esafirm.imagepicker.features.ImagePicker
import com.esafirm.imagepicker.features.ReturnMode
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.loading_ui.*
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
        //EasyImage.openChooserWithGallery(fragment, "Selecione", 1)
        ImagePicker.create(fragment)
            .returnMode(ReturnMode.ALL) // set whether pick and / or camera action should return immediate result or not.
            .folderMode(true) // folder mode (false by default)
            //.toolbarFolderTitle("Folder") // folder selection title
            .toolbarImageTitle("Selecione") // image selection title
            //.toolbarArrowColor(Color.BLACK) // Toolbar 'up' arrow color
            //.includeVideo(true) // Show video on image picker
            .single() // single mode
            //.multi() // multi mode (default mode)
            //.limit(10) // max images can be selected (99 by default)
            //.showCamera(true) // show camera or not (true by default)
            .imageDirectory("UVets") // directory name for captured image  ("Camera" folder by default)
            //.origin(images) // original selected images, used in multi mode
            //.exclude(images) // exclude anything that in image.getPath()
            //.excludeFiles(files) // same as exclude but using ArrayList<File>
            //.theme(R.style.CustomImagePickerTheme) // must inherit ef_BaseTheme. please refer to sample
            //.enableLog(false) // disabling log
            //.imageLoader(new GrayscaleImageLoder()) // custom image loader, must be serializeable
            .start() // start image picker activity with request code
    }
}

fun BaseFragment.loading(isLoading: Boolean) {
    activity?.run {
        if (KeyboardUtils.isSoftInputVisible(this)) {
            KeyboardUtils.hideSoftInput(this)
        }
    }
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