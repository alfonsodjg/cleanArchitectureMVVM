package com.example.ramdomuser.ui.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ramdomuser.databinding.ActivityUserShowDatasBinding
import com.example.ramdomuser.ui.adapter.UserAdapter
import com.example.ramdomuser.ui.viewmodels.EmailShowDataViewModel

class UserShowDatasActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserShowDatasBinding
    private val viewModel: EmailShowDataViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserShowDatasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.vieState.observe(this){
            val adapter = UserAdapter(it.listDatasUser)
            binding.rvDatas.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
            binding.rvDatas.adapter = adapter
        }
        viewModel.onShowDatasUser()
    }
}