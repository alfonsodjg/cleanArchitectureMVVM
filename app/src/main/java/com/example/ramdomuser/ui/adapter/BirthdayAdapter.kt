package com.example.ramdomuser.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ramdomuser.data.local.datamodel.BirthdayDataModelRealm
import com.example.ramdomuser.data.local.datamodel.EmailDataModelRealm
import com.example.ramdomuser.databinding.ItemDatasrealmEmailBinding



class BirthdayAdapter (val listRealm : List<BirthdayDataModelRealm>):
    RecyclerView.Adapter<BirthdayAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemDatasrealmEmailBinding): RecyclerView.ViewHolder(binding.root) {

        fun binDatas(item: BirthdayDataModelRealm){
            binding.tvEmail.text = item.date
            binding.tvPass.text = item.city
            Glide.with(binding.root).load(item.picture).into(binding.ivPicture)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BirthdayAdapter.ViewHolder {
        val view = ItemDatasrealmEmailBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: BirthdayAdapter.ViewHolder, position: Int) {
        val datas = listRealm[position]
        holder.binDatas(datas)
    }


    override fun getItemCount():Int {
        return listRealm.size
    }




}