package com.example.meetexpress

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import android.graphics.BitmapFactory
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_user_details.*
import android.content.Intent


class UserDetails : AppCompatActivity() {

    val PICK_IMAGE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)

        val profile_photo = findViewById<ImageView>(R.id.profile_photo)

        val batmapBitmap = BitmapFactory.decodeResource(resources, R.drawable.logo)
        val circularBitmapDrawable = RoundedBitmapDrawableFactory.create(resources, batmapBitmap)

        circularBitmapDrawable.isCircular = true
        profile_photo.setImageDrawable(circularBitmapDrawable)

        change_photo_btn.setOnClickListener {
            changePhoto()
        }
    }

    private fun changePhoto() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PICK_IMAGE) {
            //TODO: action
        }
    }
}
