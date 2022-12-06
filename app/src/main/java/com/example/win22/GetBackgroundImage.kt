package com.example.win22

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

object GetBackgroundImage {
    fun setImage(layout: ConstraintLayout, context: Context){
        Glide.with(context)
            .asDrawable()
            .load("http://49.12.202.175/win22/background.png")
            .into(object: CustomTarget<Drawable>(){
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    layout.background = resource
                }
                override fun onLoadCleared(placeholder: Drawable?) {}
            })
    }
}