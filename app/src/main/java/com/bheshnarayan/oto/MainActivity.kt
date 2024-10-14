package com.bheshnarayan.oto

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Environment
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bheshnarayan.oto.utils.checkAudioPermissions
import java.io.File

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        checkAudioPermissions()
        setContentView(R.layout.activity_main)

        val musicDir = File(Environment.getExternalStorageDirectory(),"Music")
        print(musicDir.listFiles())
        val files: Array<File> = musicDir.listFiles()?.filter { it.isFile }?.toTypedArray() ?: arrayOf()
        val listBox: RecyclerView = findViewById(R.id.musicList)

        val adapter = SongsAdapter(files,this)

        listBox.layoutManager = LinearLayoutManager(this)
        listBox.adapter = adapter
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}