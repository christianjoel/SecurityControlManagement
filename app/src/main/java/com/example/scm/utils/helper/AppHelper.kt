package com.example.scm.utils.helper

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.text.Html
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.example.scm.R
import com.example.scm.utils.anim.PushDown
import com.example.scm.utils.anim.PushDownAnim
import com.google.android.material.snackbar.Snackbar
import java.util.Calendar
import java.util.Locale


object AppHelper {

    fun isNetworkConnected(context: Context): Boolean {
        var result = false
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cm?.run {
                cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                    result = when {
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                        else -> false
                    }
                }
            }
        } else {
            cm?.run {
                cm.activeNetworkInfo?.run {
                    if (type == ConnectivityManager.TYPE_WIFI) {
                        result = true
                    } else if (type == ConnectivityManager.TYPE_MOBILE) {
                        result = true
                    }
                }
            }
        }
        return result
    }


    fun crossfade_splash(viewIn: View, animateViewOut: Boolean = true) {
        val crossfadeDuration = 700L
        viewIn.alpha = 0f
        viewIn.visibility = View.VISIBLE
        viewIn.bringToFront()
        viewIn.animate()
            .alpha(1f)
            .setDuration(crossfadeDuration)
            .setListener(null)
    }

    fun crossfade_gone(viewIn: View, animateViewOut: Boolean = true) {
        val crossfadeDuration = 700L
        viewIn.animate()
            .setDuration(if (animateViewOut) crossfadeDuration else 0)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    viewIn.visibility = View.GONE
                }
            })

    }

    fun onShakeView(view: View, context: Context) {
        val shake: Animation
        shake = AnimationUtils.loadAnimation(context, R.anim.shake)
        view.startAnimation(shake)
    }

    fun runAnimation(view: View, context: Context) {
        val a: Animation = AnimationUtils.loadAnimation(context, R.anim.scale)
        a.reset()
        view.clearAnimation()
        view.startAnimation(a)
    }

    fun pushDown(pusList: PushDown) {
        pusList.setScale(PushDownAnim.MODE_STATIC_DP, 2F)
            .setDurationPush(PushDownAnim.DEFAULT_PUSH_DURATION)
            .setDurationRelease(PushDownAnim.DEFAULT_RELEASE_DURATION)
            .setInterpolatorPush(PushDownAnim.DEFAULT_INTERPOLATOR)
            .setInterpolatorRelease(PushDownAnim.DEFAULT_INTERPOLATOR)
    }

    fun skippushDown(pusList: PushDown) {
        pusList.setScale(PushDownAnim.MODE_STATIC_DP, 4F)
            .setDurationPush(PushDownAnim.DEFAULT_PUSH_DURATION)
            .setDurationRelease(PushDownAnim.DEFAULT_RELEASE_DURATION)
            .setInterpolatorPush(PushDownAnim.DEFAULT_INTERPOLATOR)
            .setInterpolatorRelease(PushDownAnim.DEFAULT_INTERPOLATOR)
    }

    fun pushDown_List(pusList: PushDown) {
        pusList.setScale(PushDownAnim.MODE_STATIC_DP, 6F)
            .setDurationPush(PushDownAnim.DEFAULT_PUSH_DURATION)
            .setDurationRelease(PushDownAnim.DEFAULT_RELEASE_DURATION)
            .setInterpolatorPush(PushDownAnim.DEFAULT_INTERPOLATOR)
            .setInterpolatorRelease(PushDownAnim.DEFAULT_INTERPOLATOR)
    }

    fun setEnable(vararg views: View, boll: Boolean) {

    }

    fun crossfade(viewIn: View, viewOut: View, animateViewOut: Boolean = true) {
        val crossfadeDuration = 1000L
        // Set the content view to 0% opacity but visible, so that it is visible
        // (but fully transparent) during the animation.
        viewIn.alpha = 0f
        viewIn.visibility = View.VISIBLE
        viewIn.bringToFront()
        viewIn.animate()
            .alpha(1f)
            .setDuration(crossfadeDuration)
            .setListener(null)
        viewOut.animate()
            .alpha(0f)
            .setDuration(if (animateViewOut) crossfadeDuration else 0)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    viewOut.visibility = View.GONE
                }
            })
    }

    fun runLayoutAnimation(recyclerView: RecyclerView) {
        val context = recyclerView.context
        val controller =
            AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down)
        recyclerView.layoutAnimation = controller
        recyclerView.adapter!!.notifyDataSetChanged()
        recyclerView.scheduleLayoutAnimation()
    }

    fun onRotateClick(view: View) {
        view.animate().apply {
            rotationBy(180f)
            duration = 90
        }.start()
    }

    fun onChangeColorClick(context: Context, view1: ImageView, view2: TextView, view3: ImageView) {
        view1.setColorFilter(ContextCompat.getColor(context, R.color.color_500));
        view2.setTextColor(ContextCompat.getColor(context, R.color.color_500))
        view3.setColorFilter(ContextCompat.getColor(context, R.color.color_500))
        android.os.Handler(Looper.getMainLooper()).postDelayed(object : Runnable {
            override fun run() {
                view1.setColorFilter(ContextCompat.getColor(context, R.color.text_color))
                view2.setTextColor(ContextCompat.getColor(context, R.color.text_color))
                view3.setColorFilter(ContextCompat.getColor(context, R.color.text_color))
            }
        }, 500)
    }


    fun showSnack(message: String?, activity: Activity) {
        val snack: Snackbar = Snackbar.make(
            activity.findViewById(android.R.id.content),
            message!!,
            Snackbar.LENGTH_LONG
        )
        snack.setTextColor(Color.WHITE)
        snack.show()
    }

    fun goneAll(vararg views: View?) {
        val crossfadeDuration = 500L
        //val crossfadeDuration = 0L
        for (view in views) {
            view!!.animate()
                .alpha(0f)
                .setDuration(crossfadeDuration)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        view.visibility = View.GONE
                    }
                })
        }
    }

    fun viewAll(vararg views: View?) {
        val crossfadeDuration = 500L
        //val crossfadeDuration = 0L
        for (view in views) {
            view!!.alpha = 0f
            view.visibility = View.VISIBLE
            view.bringToFront()
            view.animate()
                .alpha(1f)
                .setDuration(crossfadeDuration)
                .setListener(null)
        }
    }

    @JvmStatic
    fun navigateto(activity: Activity, int: Int, bundle: Bundle) {
        activity.findNavController(R.id.nav_host_fragment)
            .navigate(int, bundle)
    }

    fun onNextPage(context: Context?, bundle: Bundle?, cls: Class<*>?) {
        val intent = Intent(context, cls!!)
        if (bundle != null) {
            intent.putExtras(bundle!!)
        }

        context!!.startActivity(intent, bundle)
    }

    fun setToolBar(supportActionBar: ActionBar?, title: String) {
        supportActionBar!!.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setDisplayShowHomeEnabled(true);
    }


    fun extractColorProfile(
        context: Activity? = null,
        productImage: ImageView? = null,
        imageUrl: String = "",
        lltop: LinearLayout? = null,
        zoom_top: Toolbar
    ) {
        Glide.with(context!!).asBitmap()
            .load(imageUrl)
            .into(object : CustomTarget<Bitmap?>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: com.bumptech.glide.request.transition.Transition<in Bitmap?>?
                ) {
                    productImage!!.setImageBitmap(resource)
                    // Extract color
                    Palette.from(resource!!).generate { p: Palette? ->
                        val mutedLight =
                            p!!.getLightMutedColor(
                                ContextCompat.getColor(
                                    context,
                                    android.R.color.darker_gray
                                )
                            )
                        val mutedLight2 =
                            p.getMutedColor(
                                ContextCompat.getColor(
                                    context,
                                    android.R.color.darker_gray
                                )
                            )
                        val mutedLight3 =
                            p.getDarkMutedColor(
                                ContextCompat.getColor(
                                    context,
                                    android.R.color.darker_gray
                                )
                            )
                        val mutedLight4 =
                            p.getLightVibrantColor(
                                ContextCompat.getColor(
                                    context,
                                    android.R.color.darker_gray
                                )
                            )
                        val mutedLight5 =
                            p.getVibrantColor(
                                ContextCompat.getColor(
                                    context,
                                    android.R.color.darker_gray
                                )
                            )
                        val mutedLight6 =
                            p.getDarkVibrantColor(
                                ContextCompat.getColor(
                                    context,
                                    android.R.color.darker_gray
                                )
                            )

                    }
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    TODO("Not yet implemented")
                }
            })
    }


    private lateinit var alertDialog: AlertDialog
    fun showLoader(context: Activity) {
        try {
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            val viewGroup: ViewGroup = context.findViewById(android.R.id.content)
            val dialogView: View =
                LayoutInflater.from(context).inflate(R.layout.loader, viewGroup, false)
            builder.setView(dialogView)
            //   alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
            alertDialog = builder.create()
            alertDialog.show()
            alertDialog.setCanceledOnTouchOutside(false)
        } catch (e: Exception) {
        }

    }

    fun hideLoader() {
        try {
            alertDialog.hide()
        } catch (e: Exception) {
        }
    }

    @SuppressLint("ResourceAsColor")
    fun showAlertDefault(context: Activity, msg: String, int: Int = R.color.color_500) {
        /*create(context)
            .setText(msg)
            .setBackgroundColorRes(int)
            .show()*/

        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()

    }

    interface AlertDialog1 {
        fun yes()
        fun no()
    }

    lateinit var alertDialog1: AlertDialog1

    fun areYouSure(string: String, context: Activity?, alertDialog: AlertDialog1) {
        alertDialog1 = alertDialog
        val alertDialog = android.app.AlertDialog.Builder(context).create()
        alertDialog.setMessage(string)
        alertDialog.setButton(
            android.app.AlertDialog.BUTTON_POSITIVE,
            "Yes"
        ) { dialog: DialogInterface?, which: Int ->
            alertDialog1.yes()
        }
        alertDialog.setButton(
            android.app.AlertDialog.BUTTON_NEGATIVE,
            "No"
        ) { dialog: DialogInterface, which: Int -> dialog.dismiss() }
        if (!alertDialog.isShowing && !context!!.isFinishing) alertDialog.show()
    }


    fun digitDobleDot(int: Double): String {
        var aa = String.format("%.2f", int)

        return aa
    }

    fun fmt(d: Double): String? {
        if (d == d)
            return kotlin.String.format("%d", d as kotlin.Long)
        else
            return kotlin.String.format("%s", d)
    }


    fun removeFirstChar(s: String): String? {
        return s.substring(1)
    }

    fun getDate(timestamp: Long): String {
        val cal: Calendar = Calendar.getInstance(Locale.ENGLISH)
        cal.timeInMillis = timestamp * 1000L
        val date: String = DateFormat.format("dd MMM yyyy - hh:mm a", cal).toString()
        return date
    }

//    fun getVersionCodeOfApp(context: Context): String? {
//        var version = ""
//        try {
//            val pInfo = context.packageManager.getPackageInfo(context.packageName, 0)
//            version = pInfo.versionName
//        } catch (e: PackageManager.NameNotFoundException) {
//            e.printStackTrace()
//        }
//        return version
//    }


    fun userID(co: Activity): String {
        return DataStorage(co).getString("id").toString()
    }

    fun currencysymbol(co: Activity): String {
        return DataStorage(co).getString(Keys.currency_symbol).toString()
    }

    fun loginStatus(context: Activity): Boolean {
        return DataStorage(context).getBoolean("login")
    }

    fun htmlTextSpannable(textView: TextView, string: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            textView.text = Html.fromHtml(string, Html.FROM_HTML_MODE_COMPACT);
        } else {
            textView.text = Html.fromHtml(string);
        }

    }

    interface Dialog {
        fun submit()
    }


    fun hideKeyboard(activity: Activity) {
        val imm: InputMethodManager =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun showToast(context: Context, string: String) {
        val toast = Toast.makeText(context, string, Toast.LENGTH_SHORT)
        toast.show()
    }

    fun isAppInstalled(context: Activity, packageName: String): Boolean {
        val pm: PackageManager = context.packageManager
        var installed = false
        installed = try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
        return installed
    }

    fun savegcmdata(context: Activity, string: String) {
        DataStorage(context).saveString(Keys.gcm_data, string)
    }

}


