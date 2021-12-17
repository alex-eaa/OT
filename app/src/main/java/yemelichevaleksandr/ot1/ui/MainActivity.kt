package yemelichevaleksandr.ot1.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import yemelichevaleksandr.ot1.databinding.ActivityMainBinding
import yemelichevaleksandr.ot1.model.update.UpdateTests

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fab.setOnClickListener {
            startActivity(Intent(this, TestActivity::class.java))
        }

        UpdateTests()
    }
}