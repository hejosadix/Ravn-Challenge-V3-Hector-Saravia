package com.gmail.hejosadix.starwars.utils.extencions

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

fun Toolbar.setSupportActionBar(activity: Activity) {
    val appCompatActivity = (activity as AppCompatActivity)
    appCompatActivity.setSupportActionBar(this)
    appCompatActivity.supportActionBar?.let {
        it.setDisplayHomeAsUpEnabled(true)
        it.setDisplayShowHomeEnabled(true)
    }
}
