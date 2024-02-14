package com.example.ramdomuser.ui.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ramdomuser.R
import com.example.ramdomuser.data.local.datamodel.BirthdayDataModelRealm
import com.example.ramdomuser.databinding.ActivityBirthdayShowDatasBinding
import com.example.ramdomuser.ui.adapter.BirthdayAdapter
import com.example.ramdomuser.ui.viewmodels.EmailShowDataViewModel
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmResults

class BirthdayShowDatasActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBirthdayShowDatasBinding
    private val viewModel: EmailShowDataViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBirthdayShowDatasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.vieState.observe(this){
            val adapter = BirthdayAdapter(it.listDataBirthday)
            binding.rvInfo.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
            binding.rvInfo.adapter = adapter
        }
        viewModel.onShowDataBirthday()
    }
}