package com.iatli.y2021w4listmedia

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.MediaPlayer
import android.os.Bundle
import android.provider.MediaStore
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.*


class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener{

    var tts : TextToSpeech? = null
    val CAMERCODE:Int = 7777

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val str = read_file_content()

        val txt_file = findViewById<TextView>(R.id.txt_file)
        txt_file.setText(str)

        tts = TextToSpeech(this, this)
        
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 66666 )
        }


    }


    fun read_file(view: View){
        val str = read_file_content()
        tts!!.speak(str, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    fun radiobuttoncall(view: View){
        if (view.id == R.id.r1) {
            val mp: MediaPlayer = MediaPlayer.create(this, R.raw.button1)
            mp.start()
        }
        else if (view.id == R.id.r2) {
            val mp: MediaPlayer = MediaPlayer.create(this, R.raw.button2)
            mp.start()

        }
        else if (view.id == R.id.r3) {
            val mp: MediaPlayer = MediaPlayer.create(this, R.raw.button3)
            mp.start()
        }

    }

    private fun read_file_content() : String{
        val scanner: Scanner = Scanner(resources.openRawResource(R.raw.file))
        val stringbuilder: StringBuilder = StringBuilder()
        while (scanner.hasNext()){
            stringbuilder.append(scanner.nextLine())
        }
        scanner.close()

        return stringbuilder.toString()
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS){
            tts!!.setLanguage(Locale.US)
            Toast.makeText(this, "TTS is ready!", Toast.LENGTH_SHORT).show()
        }
    }

    fun camera(view: View) {
        val intent: Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERCODE)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == CAMERCODE && resultCode == RESULT_OK){
            val img: Bitmap? = data!!.extras.get("data") as Bitmap
            findViewById< ImageView>(R.id.img_view).setImageBitmap(img)
        }
    }


}