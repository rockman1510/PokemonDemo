package com.pokemondemo.base.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.pokemondemo.utils.view.CustomDialog
import com.pokemondemo.utils.view.DialogUtils

open class BaseFragment : Fragment() {

    private lateinit var loadingDialog: CustomDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loadingDialog =
            DialogUtils.loadingDialog(requireContext(), CustomDialog.LOADING_DIALOG_TIMEOUT)
        super.onViewCreated(view, savedInstanceState)
    }

    fun hideLoadingDialog() {
        if (loadingDialog.isShowing())
            loadingDialog.dismiss()
    }

    fun showLoadingDialog() {
        if (!loadingDialog.isShowing())
            loadingDialog.show()
    }
}