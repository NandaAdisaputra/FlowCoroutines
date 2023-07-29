package com.nandaadisaputra.retrofit.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.nandaadisaputra.retrofit.R
import com.nandaadisaputra.retrofit.adapter.PostAdapter
import com.nandaadisaputra.retrofit.databinding.ActivityMainBinding
import com.nandaadisaputra.retrofit.utils.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var adapter: PostAdapter
    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_main)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        initData()
        setupObserver()
    }
    private fun initData() {
        adapter = PostAdapter()
        adapter.setOnClickItem { name ->
            Toast.makeText(this, "The Title is ${name.title}", Toast.LENGTH_SHORT).show()
        }
        binding.adapter = adapter
    }
    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                val viewModel = ViewModelProvider(this@MainActivity)[MainViewModel::class.java]
                viewModel.getBody()
                viewModel.uiState.collect {
                    when (it) {
                        is UiState.Success -> {
                            binding.progressBar = false
                            adapter.submitList(it.data)
                            binding.recyclerView = true
                        }
                        is UiState.Loading -> {
                            binding.progressBar = true
                            binding.recyclerView = false
                        }
                        is UiState.Error -> {
                            //Handle Error
                            binding.progressBar = false
                            Toast.makeText(
                                this@MainActivity,
                                it.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }
}