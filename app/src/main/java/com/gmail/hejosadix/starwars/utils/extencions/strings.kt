package com.gmail.hejosadix.starwars.utils.extencions

import android.content.res.Resources
import com.gmail.hejosadix.starwars.R

fun String?.ifNullOrBlankUnknown(
    resources: Resources,
): String {
    return if (this.isNullOrBlank()) {
        resources.getString(
            R.string.unknown,
        )
    } else {
        this
    }

}