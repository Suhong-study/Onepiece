package com.example.onepiece

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onepiece.databinding.ActivityMainBinding
import android.content.Intent

class MainActivity : AppCompatActivity() {
    private lateinit var view : ActivityMainBinding
    private lateinit var model : OnepieceViewModel
    private lateinit var adapter: OnepieceAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = ActivityMainBinding.inflate(layoutInflater)
        setContentView(view.root)

        model = ViewModelProvider(this).get(OnepieceViewModel::class.java)
        adapter = OnepieceAdapter(model) { person -> adapterOnClick(person) }
        view.list.apply{
            layoutManager = LinearLayoutManager(applicationContext)
            itemAnimator = DefaultItemAnimator()
            setHasFixedSize(true)
            adapter = this@MainActivity.adapter
        }

        model.list.observe(this, {
                adapter.notifyDataSetChanged()
            })
        model.requestPerson()
    }

    private fun adapterOnClick(person: OnepieceViewModel.Person) : Unit{
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("name", person.name)
        intent.putExtra("image", person.image)
        intent.putExtra("explanation", person.explanation)
        startActivity(intent)
    }
}