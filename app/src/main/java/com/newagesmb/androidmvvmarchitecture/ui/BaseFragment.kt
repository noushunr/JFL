package com.newagesmb.androidmvvmarchitecture.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.newagesmb.androidmvvmarchitecture.R
import com.newagesmb.androidmvvmarchitecture.common.AutoCleanedValue
import com.newagesmb.androidmvvmarchitecture.utils.ConnectvityIObserver
import com.newagesmb.androidmvvmarchitecture.utils.ExtensionFunctions.showSnackBar
import com.newagesmb.androidmvvmarchitecture.utils.NetworkConnectivityObserver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


abstract class BaseFragment<B : ViewBinding> : Fragment(), CoroutineScope by CoroutineScope(
    Dispatchers.Main
) {

    protected var binding: B by autoCleaned()
        private set
     var isNetworkawailable=false
    private lateinit var connectvityIObserver: ConnectvityIObserver
    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> B

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = bindingInflater.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        connectvityIObserver= NetworkConnectivityObserver(requireContext())
        viewLifecycleOwnerLiveData.observe(viewLifecycleOwner) {

            connectvityIObserver.observe().onEach {
                when(it){
                    ConnectvityIObserver.Status.Avialable->{
                        isNetworkawailable =   true


                    }
                    ConnectvityIObserver.Status.Lost->{
                        isNetworkawailable =  false

                        //showSnackBar("It look like your in offline ", Snackbar.ANIMATION_MODE_SLIDE)
                    }
                    ConnectvityIObserver.Status.Unavailable->{
                        isNetworkawailable =  false

                      //  showSnackBar("It look like your in offline ", Snackbar.ANIMATION_MODE_SLIDE)

                    }
                    ConnectvityIObserver.Status.Losing->{
                       // isFirst=false

                    }

                }


            }.launchIn(viewLifecycleOwner.lifecycleScope)

        }
    }
    fun checkNetwork(call:()->Unit){

        if (isNetworkawailable){
            call.invoke()
        }else{
            showSnackBar(getString(R.string.please_check_your_internet_connection),Snackbar.LENGTH_SHORT)
        }
    }
    override fun onDestroyView() {
        coroutineContext[Job]?.cancel()
        super.onDestroyView()
    }
    fun <T : Any> Fragment.autoCleaned(initializer: (() -> T)? = null): AutoCleanedValue<T> {
        return AutoCleanedValue(this, initializer)
    }

    fun  openAppSettings(){
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts("package", requireActivity().packageName, null)
        intent.data = uri
        startActivity(intent)
    }
}