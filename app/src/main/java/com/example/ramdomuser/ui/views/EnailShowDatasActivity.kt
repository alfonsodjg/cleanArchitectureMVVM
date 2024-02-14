package com.example.ramdomuser.ui.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ramdomuser.R
import com.example.ramdomuser.data.local.datamodel.EmailDataModelRealm
import com.example.ramdomuser.databinding.ActivityEnailShowDatasBinding
import com.example.ramdomuser.ui.adapter.EmailAdapter
import com.example.ramdomuser.ui.viewmodels.EmailShowDataViewModel
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmResults

class EnailShowDatasActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEnailShowDatasBinding
    private val viewModel: EmailShowDataViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEnailShowDatasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.vieState.observe(this){
            val adapter = EmailAdapter(it.listDatasEmail)
            binding.rvDatas.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
            binding.rvDatas.adapter = adapter
        }
        viewModel.onShowDatasEmail()
    }
}