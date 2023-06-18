package com.abhijith.socialnetworkapp.core.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import com.abhijith.socialnetworkapp.R

@SuppressLint("StringFormatInvalid")
fun Context.sendSharePostIntent(postId:String) {
   val intent =  Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT,
        getString(R.string.share_intent_text,
        "https://abhijith.com/$postId"
        ))
        type = "text/plain"
    }
    if (intent.resolveActivity(packageManager)!=null){
         startActivity(Intent.createChooser(intent,"Select an app"))
    }
}