package com.pokemondemo.utils.view

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.pokemondemo.R
import com.pokemondemo.databinding.LayoutCustomDialogBinding

class CustomDialog() {
    private var alertDialog: AlertDialog? = null
    val MSG_TIME_OUT = 1

    private lateinit var binding: LayoutCustomDialogBinding

    constructor(context: Context, builder: Builder) : this() {
        if (alertDialog == null) {
            binding = DataBindingUtil.inflate(
                LayoutInflater.from(context), R.layout.layout_custom_dialog, null, false
            )
            binding.builder = builder
            if (builder.positiveListener != null) {
                binding.tvPositive.setOnClickListener {
                    dismiss()
                    builder.positiveListener?.onCallback(alertDialog!!)
                }
            }
            if (builder.negativeListener != null) {
                binding.tvNegative.setOnClickListener {
                    dismiss()
                    builder.negativeListener?.onCallback(alertDialog!!)
                }
            }
            if (builder.neuralListener != null) {
                binding.tvNeutral.setOnClickListener {
                    dismiss()
                    builder.neuralListener?.onCallback(alertDialog!!)
                }
            }
            alertDialog =
                AlertDialog.Builder(context).setView(binding.root)
                    .setCancelable(builder.isCancelable)
                    .create()
            alertDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            alertDialog?.setCanceledOnTouchOutside(builder.isCancelable)
            if (builder.timeOut > 0) {
                object : Handler(Looper.getMainLooper()) {
                    override fun handleMessage(msg: Message) {
                        super.handleMessage(msg)
                        if (msg.what == MSG_TIME_OUT) {
                            dismiss()
                        }
                    }
                }.sendEmptyMessageDelayed(MSG_TIME_OUT, builder.timeOut)
            }
        }
    }


    fun show() {
        if (!alertDialog?.isShowing!!) {
            alertDialog?.show()
        }
    }

    fun isShowing(): Boolean {
        return alertDialog?.isShowing!!
    }

    fun dismiss() {
        if (alertDialog?.isShowing!!)
            alertDialog?.dismiss()
    }

    class Builder(var context: Context) {
        var title: String = ""
        var message: String = ""
        var isLoading: Boolean = false
        var positiveListener: OnPositiveListener? = null
        var negativeListener: OnNegativeListener? = null
        var neuralListener: OnNeuralListener? = null
        var isCancelable: Boolean = true
        var contentPositive = context.getString(R.string.ok)
        var contentNegative = context.getString(R.string.cancel)
        var contentNeural = context.getString(R.string.close)
        var timeOut: Long = -1

        fun setTitle(title: String): Builder {
            this.title = title
            return this
        }

        fun setMessage(message: String): Builder {
            this.message = message
            return this
        }

        fun setLoadingContent(isLoading: Boolean): Builder {
            this.isLoading = isLoading
            return this
        }

        fun setPositiveButton(
            content: String? = contentPositive,
            listener: OnPositiveListener
        ): Builder {
            this.positiveListener = listener
            this.contentPositive = content!!
            return this
        }

        fun setNegativeButton(
            content: String? = contentNegative,
            listener: OnNegativeListener
        ): Builder {
            this.negativeListener = listener
            this.contentNegative = content!!
            return this
        }

        fun setNeuralButton(
            content: String? = contentNeural,
            listener: OnNeuralListener
        ): Builder {
            this.neuralListener = listener
            this.contentNeural = content!!
            return this
        }

        fun setCancelable(isCancel: Boolean): Builder {
            this.isCancelable = isCancel
            return this
        }

        fun setTimeOut(timeOut: Long): Builder {
            this.timeOut = timeOut
            return this
        }

        fun build(): CustomDialog {
            return CustomDialog(context, this)
        }
    }

    interface OnPositiveListener {
        fun onCallback(dialog: AlertDialog) {}
    }

    interface OnNegativeListener {
        fun onCallback(dialog: AlertDialog) {}
    }

    interface OnNeuralListener {
        fun onCallback(dialog: AlertDialog) {}
    }

    companion object {
        const val LOADING_DIALOG_TIMEOUT = 15000L
    }
}