package yemelichevaleksandr.ot1.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import yemelichevaleksandr.ot1.App
import yemelichevaleksandr.ot1.databinding.ActivityMainBinding
import yemelichevaleksandr.ot1.viewmodel.MainActivityViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainActivityViewModel by lazy {
        ViewModelProvider(this).get(MainActivityViewModel::class.java).apply {
            App.instance.component.inject(this)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fab.setOnClickListener {
            startActivity(Intent(this, TestActivity::class.java))
        }

        viewModel.checkUpdate()
    }
}