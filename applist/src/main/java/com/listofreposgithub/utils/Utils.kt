package com.listofreposgithub.utils

import android.content.Context
import android.widget.Toast

const val API_BASE_URL = "https://api.github.com/"

const val READ_TIMEOUT = 30L
const val WRITE_TIMEOUT = 15L
const val CONNECT_TIMEOUT = 60L

fun Context.toast(message: CharSequence) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()