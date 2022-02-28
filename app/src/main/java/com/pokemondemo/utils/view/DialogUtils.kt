package com.pokemondemo.utils.view

import android.content.Context
import com.pokemondemo.R

class DialogUtils {
    companion object {
        fun loadingDialog(context: Context, timeOut: Long): CustomDialog {
            return CustomDialog.Builder(context).setLoadingContent(true)
                .setCancelable(false).setTitle(context.getString(R.string.loading))
                .setMessage(context.getString(R.string.please_wait)).setTimeOut(timeOut).build()
        }

        fun messageDialog(context: Context, title: String, message: String): CustomDialog {
            return CustomDialog.Builder(context).setCancelable(true).setTitle(title)
                .setMessage(message).setNeuralButton(
                    context.getString(R.string.close),
                    object : CustomDialog.OnNeuralListener {
                    }).build()
        }
    }
}