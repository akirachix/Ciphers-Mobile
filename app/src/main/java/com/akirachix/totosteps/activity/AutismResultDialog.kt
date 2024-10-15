package com.akirachix.totosteps.activity

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import com.akirachix.totosteps.databinding.DialogAutismResultBinding


object AutismResultDialog {
    fun show(context: Context, message: String, onDismiss: () -> Unit) {
        AlertDialog.Builder(context)
            .setTitle("Autism Screening Result")
            .setMessage(message)
            .setPositiveButton("Close") { dialog, _ ->
                dialog.dismiss()
                onDismiss()
            }
            .setOnDismissListener {
                onDismiss()
            }
            .create()
            .show()
    }
}