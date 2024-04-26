package com.example.todolistapp

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.Data
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.room.Room
import com.example.todolistapp.databinding.ActivityUpdateCardBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UpdateCard : AppCompatActivity() {
    lateinit var binding: ActivityUpdateCardBinding

    //creating obj of myDatabase
    private lateinit var database: myDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateCardBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        //Initialising the database object --> it costly so we make singleton class
        database = Room.databaseBuilder(
            applicationContext, myDatabase::class.java, "To_Do"
        ).build()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //setting up o
        val position = intent.getIntExtra("id", -1)
        if (position != -1) {
            val title = DataObject.getData(position).title
            val priority = DataObject.getData(position).priority

            binding.createTitle.setText(title)
            binding.createPriority.setText(priority)

            //when click on delete
            binding.deleteBUtton.setOnClickListener()
            {
                DataObject.deleteData(position)

                //global scope to delete data from database also
                GlobalScope.launch {
                    database.dao()
                        .deleteTask(Entity(position + 1, title, priority))
                }
                myIntent() //to return back to main activity
            }

            //when click on update
            binding.updateButton.setOnClickListener() {
                DataObject.updateData(
                    position,
                    binding.createTitle.text.toString(),
                    binding.createPriority.text.toString()
                )

                //global scope to update data from database also
                GlobalScope.launch {
                    database.dao()
                        .updateTask(
                            Entity(
                                position + 1,
                                binding.createTitle.text.toString(),
                                binding.createPriority.text.toString()
                            )
                        )
                }


                myIntent()
            }
        }
    }

    //function for intent passing
    private fun myIntent() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }


}