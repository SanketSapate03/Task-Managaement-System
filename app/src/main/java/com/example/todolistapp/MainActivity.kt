package com.example.todolistapp

import android.content.Intent
import android.os.Binder
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.todolistapp.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

//to create room data bse
//1. entity--> Table
//2. dao (queries) --> fetch add delete (data access object)
//3. database

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    //creating obj of myDatabase
    private lateinit var database: myDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        //Initialising the database object --> it costly so we make singleton class
        database= Room.databaseBuilder(
            applicationContext, myDatabase::class.java, "To_Do"
        ).build()




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //on add button
        binding.add.setOnClickListener(){
            val intent= Intent(this, CreateCard::class.java)
            startActivity(intent)
            setRecycler()
        }

        //delete all btn
        binding.deleteAll.setOnClickListener(){
            DataObject.deleteAll()

            //global scope to delete
            GlobalScope.launch {
                database.dao().deleteAll()
            }

            setRecycler()
        }
        setRecycler()
 }

    private fun setRecycler(){
        //setting up data
        binding.recyclerView.adapter=Adapter(DataObject.getAllData())

        //set layout on recycler view
        binding.recyclerView.layoutManager=LinearLayoutManager(this)
    }
}