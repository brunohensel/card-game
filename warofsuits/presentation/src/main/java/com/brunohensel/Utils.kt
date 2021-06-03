package com.brunohensel

import android.content.Context
import androidx.core.content.ContextCompat

fun Context.getDrawableFromRes(it: Int) = ContextCompat.getDrawable(this, it)