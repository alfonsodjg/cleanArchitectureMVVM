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
import com.example.ramdomuser.databinding.FragmentBirthdayBinding
import com.example.ramdomuser.ui.viewmodels.BirthdayViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BirthdayFragment : Fragment() {

    private var _binding: FragmentBirthdayBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val birthdayViewModel =
            ViewModelProvider(this)[BirthdayViewModel::class.java]

        _binding = FragmentBirthdayBinding.inflate(inflater, container, false)
        val root: View = binding.root

        birthdayViewModel.viewState.observe(viewLifecycleOwner, Observer {
            binding.pbLoading.isVisible = it.isLoading

            if(it.isLoading){
                binding.cardView.visibility = View.INVISIBLE
            }else{
                binding.cardView.visibility = View.VISIBLE
                if (it.userRandom.isNotEmpty()) {
                    val userRandomList = it.userRandom
                    val birthdayText = userRandomList[0].date
                    val cityInfo = userRandomList[0].location.city
                    val stateInfo = userRandomList[0].location.state
                    val countryInfo = userRandomList[0].location.country
                    val postCodeInfo = userRandomList[0].location.postcode
                    val addressText = "$cityInfo, $stateInfo, $countryInfo, $postCodeInfo"
                    binding.tvBirthday.text = "Mi fecha de cumpleaños es: $birthdayText"
                    binding.tvAddress.text = "Mi direccion es: $addressText"
                    Glide.with(binding.root).load(userRandomList[0].picture.thumbnail).into(binding.ivPicture)
                }
            }
        })

        binding.btnActualizar.setOnClickListener { birthdayViewModel.onNewUser() }
        birthdayViewModel.userProvider()

        birthdayViewModel.showData()
        binding.btnSaveUser.setOnClickListener {
            birthdayViewModel.onSaveData()
            alert()
        }

        binding.tvShowUsers.setOnClickListener {
            val intent = Intent(context, BirthdayShowDatasActivity::class.java)
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