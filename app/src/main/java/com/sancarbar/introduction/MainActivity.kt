package com.sancarbar.introduction

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


const val EXTRA_MESSAGE = "com.sancarbar.introduction.MESSAGE"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        shareButton.setOnClickListener {
            shareText()
        }
    }

    val REQUEST_IMAGE_CAPTURE = 1


    //https://developer.android.com/training/camera/photobasics
    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data!!.extras.get("data") as Bitmap
            imageView.setImageBitmap(imageBitmap)
        }
    }

    fun onImageClicked(view: View) {
        dispatchTakePictureIntent()
    }

    //https://developer.android.com/training/sharing/send
    private fun shareText() {
        val message = messageToShare.text.toString()
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, message)
            type = "text/plain"
        }
        startActivity(sendIntent)
    }

    //https://developer.android.com/training/basics/firstapp/starting-activity#kotlin
    fun onSecondActivityClicked(view: View) {

        val message = messageToShare.text.toString()
        val intent = Intent(this, SecondActivity::class.java).apply {
            putExtra(EXTRA_MESSAGE, message)
        }
        startActivity(intent)
    }


}
