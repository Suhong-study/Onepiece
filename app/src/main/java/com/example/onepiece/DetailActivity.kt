package com.example.onepiece

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.onepiece.databinding.ActivityDetailBinding
import java.net.URLEncoder

class DetailActivity: AppCompatActivity() {
    private lateinit var layout: ActivityDetailBinding
    private lateinit var model: OnepieceViewModel
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        layout = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(layout.root)
        model = ViewModelProvider(this).get(OnepieceViewModel::class.java)
        layout.name.text = intent.getStringExtra("name")
        val url = "${OnepieceViewModel.SERVER_URL}/image/" + URLEncoder.encode(intent.getStringExtra("image"), "utf-8")
        layout.cover.setImageUrl(url, model.imageLoader)
        layout.explanation.text = intent.getStringExtra("explanation")
    }
}