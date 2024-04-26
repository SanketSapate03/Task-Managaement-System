package com.example.todolistapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.room.Room
import com.example.todolistapp.databinding.ActivityCreateCardBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CreateCard : AppCompatActivity() {
    lateinit var binding: ActivityCreateCardBinding

    //creating obj of myDatabase
    private lateinit var database: myDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCreateCardBinding.inflate(layoutInflater)
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



        //taking data from user and set to the list
      binding.saveButton.setOnClickListener(){
            if (binding.createTitle.text.toString().trim { it <= ' ' }.isNotEmpty()
                && binding.createPriority.text.toString().trim { it <= ' ' }.isNotEmpty()
            ) {
                var title = binding.createTitle.getText().toString()
                var priority = binding.createPriority.getText().toString()

                DataObject.setData(title, priority)

                //here we are add data in our entity also --> need to run on coroutines
                GlobalScope.launch {
                    database.dao().insertTask(Entity(0, title,priority))
                }

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

            } else {
                // Provide feedback to the user that fields are empty
                Toast.makeText(this, "Title and priority cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }


    }

}