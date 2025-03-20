package com.newagesmb.androidmvvmarchitecture.utils

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import com.newagesmb.androidmvvmarchitecture.databinding.LayoutLoaderBinding

class CustomLoader(context: Context) : Dialog(context) {

    companion object {
        private var customLoader: CustomLoader? = null

        fun showLoader(context: Context) {
            hideLoader()
            if (customLoader == null) {
                customLoader = CustomLoader(context)
                customLoader?.apply {
                    setCancelable(false)
                    show()
                }
            }
        }

        fun hideLoader() {
            if (customLoader != null && customLoader?.isShowing == true) {
                customLoader = try {
                    customLoader?.dismiss()
                    null
                } catch (e: Exception) {
                    null
                }
            }
        }
    }

    private lateinit var binding: LayoutLoaderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutLoaderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window?.apply {
            setLayout(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
            )
            setBackgroundDrawableResource(android.R.color.transparent)
        }
    }
}
