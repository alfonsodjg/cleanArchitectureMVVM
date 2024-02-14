package com.example.ramdomuser.ui.views

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.ramdomuser.data.local.datamodel.UserDataModelRealm
import com.example.ramdomuser.databinding.FragmentUserBinding
import com.example.ramdomuser.ui.viewmodels.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.realm.kotlin.query.RealmResults

@AndroidEntryPoint
class UserFragment : Fragment() {

    private var _binding: FragmentUserBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val userViewModel =
            ViewModelProvider(this)[UserViewModel::class.java]

        _binding = FragmentUserBinding.inflate(inflater, container, false)
        val root: View = binding.root

        userViewModel.viewState.observe(viewLifecycleOwner, Observer {
            binding.pbLoading.isVisible = it.isLoading
            if(it.isLoading){
                binding.cvUser.visibility = View.INVISIBLE
            } else {
                binding.cvUser.visibility = View.VISIBLE
                val userRandomList = it.userRandom
                if (userRandomList.isNotEmpty()) {
                val firstName = userRandomList[0].name.first
                val lastName = userRandomList[0].name.last
                val fullName = "$firstName $lastName"
                binding.tvName.text = "Mi Nombre es: $fullName"
                binding.tvPhone.text = "Mi teléfono es: ${userRandomList[0].phone}"
                Glide.with(binding.root).load(userRandomList[0].picture.thumbnail).into(binding.ivPicture)
            }
            }
        })

        userViewModel.onCreate()
        userViewModel.verDatas()
        binding.bntLoadNewUser.setOnClickListener { userViewModel.onNewUser() }
        binding.bntSaveDatas.setOnClickListener {
            userViewModel.onSaveDatas()
            alert()
        }
        binding.tvVerDatas.setOnClickListener {
            /*parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container,fragmentDatas!!)
                .commit()*/

            val intent = Intent(context,UserShowDatasActivity::class.java)
            startActivity(intent)
        }
        return root
    }
    private fun alert(){
        val builder = AlertDialog.Builder(context)
        builder.setMessage("Datas saved successful")
            .setPositiveButton("ok") { dialogInterface: DialogInterface, i: Int ->
                dialogInterface.cancel()
            }

        val alert: AlertDialog = builder.create()
        alert.setCanceledOnTouchOutside(false)
        alert.setTitle("Alert")
        alert.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}