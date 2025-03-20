package com.newagesmb.androidmvvmarchitecture.utils

import android.animation.TimeInterpolator
import android.content.Context
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.core.text.HtmlCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.doOnLayout
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import androidx.transition.Slide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.transition.MaterialFadeThrough
import com.google.android.material.transition.MaterialSharedAxis
import com.newagesmb.androidmvvmarchitecture.R
import java.util.*
import kotlin.math.exp
import kotlin.math.sin

object ExtensionFunctions {
    fun View.show() {
        visibility = View.VISIBLE
    }

    fun View.hide() {
        visibility = View.GONE
    }

    fun View.invisible() {
        visibility = View.INVISIBLE
    }

    fun View.disable() {
        this.animate().apply {
            duration = 200
            alpha(0.5f)
            start()
        }
        isEnabled = false
    }

    fun View.enable() {
        this.animate().apply {
            duration = 200
            alpha(1f)
            start()
        }
        isEnabled = true
    }

    fun View.addRipple() =
        with(TypedValue()) {
            context.theme.resolveAttribute(android.R.attr.selectableItemBackground, this, true)
            setBackgroundResource(resourceId)
        }

    fun View.shake() {
        val freq = 3f
        val decay = 2f

        // interpolator that goes 1 -> -1 -> 1 -> -1 in a sine wave pattern.
        val decayingSineWave = TimeInterpolator { input ->
            val raw = sin(freq * input * 2 * Math.PI)
            (raw * exp((-input * decay).toDouble())).toFloat()
        }

        this.animate().xBy(-100f).setInterpolator(decayingSineWave).setDuration(300).start()
    }

    fun Context.showToast(message: String?) {
        if (!message.isNullOrBlank())
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun Fragment.showToast(message: String?) {
        requireContext().showToast(message)
    }
    @JvmStatic
    @BindingAdapter("app:errorText")
    fun bindErrorText(textInputLayout: TextInputLayout, errorText: String) {
        if (errorText.isEmpty()) {
            Log.d("errortextEmpty",errorText)
            textInputLayout.error = null
        }
        else {
            Log.d("errortext",errorText)
            textInputLayout.error = errorText
        }
    }

    fun Fragment.ifNetworkConnected(connected: () -> Unit) {
        if (NetworkUtils.isNetworkConnected) {
            connected.invoke()
        } else {
            showToast(getString(R.string.please_check_your_internet_connection))
        }
    }

    fun Fragment.showSnackBar(message: String?, lengthShort: Int) {
        requireView().showSnackBar(message)
    }

    fun View.showSnackBar(message: String?) {
        if (!message.isNullOrBlank())
            Snackbar.make(this, message, Snackbar.LENGTH_LONG).show()
    }
    fun View.showSnackBarWithAction(message: String?,action:String,calback:()->Unit) {
        if (!message.isNullOrBlank())
            Snackbar.make(this, message, Snackbar.LENGTH_LONG).setAction(action){
                 calback.invoke()
            }
                .show()
    }

    fun Fragment.showLoader() {
        CustomLoader.showLoader(requireContext())
    }

    fun Fragment.hideLoader() {
        CustomLoader.hideLoader()
    }

    fun Fragment.materialFadeThrough() {
        enterTransition = MaterialFadeThrough()
        returnTransition = MaterialFadeThrough()
        exitTransition = MaterialFadeThrough()
        reenterTransition = MaterialFadeThrough()
    }

    fun Fragment.materialSharedAxisX() {
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
    }

    fun Fragment.materialSharedAxisY() {
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Y, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Y, false)
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.Y, true)
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Y, false)
    }

    fun Fragment.materialSharedAxisZ() {
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false)
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false)
    }

    fun Fragment.slideTransition() {
        enterTransition =
            Slide(Gravity.END).apply {
                duration = 200
                interpolator = FastOutSlowInInterpolator()
            }
        returnTransition =
            Slide(Gravity.END).apply {
                duration = 200
                interpolator = FastOutSlowInInterpolator()
            }
        exitTransition =
            Slide(Gravity.START).apply {
                duration = 200
                interpolator = FastOutSlowInInterpolator()
            }
        reenterTransition =
            Slide(Gravity.START).apply {
                duration = 200
                interpolator = FastOutSlowInInterpolator()
            }
    }

    fun <T> MutableLiveData<T>.asLiveData() = this as LiveData<T>

    fun Fragment.safeNavigate(
        directions: NavDirections,
        extras: Navigator.Extras? = null,
        @IdRes fromDest: Int? = null
    ) {
        try {
            val currDest = findNavController().currentDestination as? FragmentNavigator.Destination
            if (javaClass.name == currDest?.className ||
                (fromDest != null && currDest?.id == fromDest)
            ) {
                if (extras != null) {
                    findNavController().navigate(directions, extras)
                } else {
                    findNavController().navigate(directions)
                }
            }
        } catch (e: Exception) {
        }
    }

    fun Fragment.safeNavigate(
        directions: NavDirections,
        navOptions: NavOptions,
        @IdRes fromDest: Int? = null
    ) {
        try {
            val currDest = findNavController().currentDestination as? FragmentNavigator.Destination
            if (javaClass.name == currDest?.className ||
                (fromDest != null && currDest?.id == fromDest)
            ) {
                findNavController().navigate(directions, navOptions)
            }
        } catch (e: Exception) {
        }
    }

    fun View.hideKeyboard() {
        ViewCompat.getWindowInsetsController(this)?.hide(WindowInsetsCompat.Type.ime())

    }

    fun View.showKeyboard() {
        ViewCompat.getWindowInsetsController(this)?.show(WindowInsetsCompat.Type.ime())
    }

    fun View.addKeyboardInsetListener(keyboardCallback: (visible: Boolean) -> Unit) {
        doOnLayout {
            // get init state of keyboard
            var keyboardVisible =
                ViewCompat.getRootWindowInsets(this)?.isVisible(WindowInsetsCompat.Type.ime()) ==
                    true

            // callback as soon as the layout is set with whether the keyboard is open or not
            keyboardCallback(keyboardVisible)

            // whenever there is an inset change on the App, check if the keyboard is visible.
            setOnApplyWindowInsetsListener { _, windowInsets ->
                val keyboardUpdateCheck =
                    ViewCompat.getRootWindowInsets(this)
                        ?.isVisible(WindowInsetsCompat.Type.ime()) == true
                // since the observer is hit quite often, only callback when there is a change.
                if (keyboardUpdateCheck != keyboardVisible) {
                    keyboardCallback(keyboardUpdateCheck)
                    keyboardVisible = keyboardUpdateCheck
                }

                windowInsets
            }
        }
    }

    fun View.isKeyboardVisible() =
        ViewCompat.getRootWindowInsets(this)?.isVisible(WindowInsetsCompat.Type.ime()) == true

    fun Fragment.setStatusBarItemColorBlack() {
        WindowInsetsControllerCompat(requireActivity().window, requireActivity().window.decorView)
            .isAppearanceLightStatusBars = true
    }

    fun Fragment.setStatusBarItemColorWhite() {
        WindowInsetsControllerCompat(requireActivity().window, requireActivity().window.decorView)
            .isAppearanceLightStatusBars = false
    }

    fun Fragment.setNavigationBarItemColorWhite() {
        WindowInsetsControllerCompat(requireActivity().window, requireActivity().window.decorView)
            .isAppearanceLightNavigationBars = false
    }

    fun Fragment.setNavigationBarItemColorBlack() {
        WindowInsetsControllerCompat(requireActivity().window, requireActivity().window.decorView)
            .isAppearanceLightNavigationBars = true
    }

    fun Fragment.setLightNavigationBar(isLight: Boolean) {
        WindowInsetsControllerCompat(requireActivity().window, requireActivity().window.decorView)
            .isAppearanceLightNavigationBars = isLight
    }

    fun TextView.makeLinks(vararg links: Pair<String, View.OnClickListener>) {
        val spannableString = SpannableString(this.text)
        var startIndexOfLink = -1
        for (link in links) {
            val clickableSpan =
                object : ClickableSpan() {
                    override fun updateDrawState(textPaint: TextPaint) {
                        // use this to change the link color
                        // textPaint.color = textPaint.linkColor
                        // toggle below value to enable/disable
                        // the underline shown below the clickable text
                        // textPaint.isUnderlineText = true
                    }

                    override fun onClick(view: View) {
                        Selection.setSelection((view as TextView).text as Spannable, 0)
                        view.invalidate()
                        link.second.onClick(view)
                    }
                }
            startIndexOfLink = this.text.toString().indexOf(link.first, startIndexOfLink + 1)
            //      if(startIndexOfLink == -1) continue // todo if you want to verify your texts
            // contains links text
            spannableString.setSpan(
                clickableSpan,
                startIndexOfLink,
                startIndexOfLink + link.first.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        this.movementMethod =
            LinkMovementMethod.getInstance() // without LinkMovementMethod, link can not click
        this.setText(spannableString, TextView.BufferType.SPANNABLE)
    }

    fun BottomNavigationView.uncheckAllItems() {
        menu.setGroupCheckable(0, true, false)
        for (i in 0 until menu.size()) {
            menu.getItem(i).isChecked = false
        }
        menu.setGroupCheckable(0, true, true)
    }

    fun <T> MutableLiveData<T>.forceRefresh() {
        this.value = this.value
    }

    fun String.toHtmlText() =
        HtmlCompat.fromHtml(this, HtmlCompat.FROM_HTML_MODE_LEGACY)

}
