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
import com.example.ramdomuser.databinding.FragmentEmailBinding
import com.example.ramdomuser.ui.viewmodels.EmailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EmailFragment : Fragment() {

    private var _binding: FragmentEmailBinding? = null
   // private var fragmentDatas: EmailDatasFragment?=null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @SuppressLint("SetTextI18n", "CommitTransaction")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val emailViewModel =
            ViewModelProvider(this)[EmailViewModel::class.java]

        _binding = FragmentEmailBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //ragmentDatas = EmailDatasFragment()
        emailViewModel.viewState.observe(viewLifecycleOwner, Observer {
            binding.pbLoading.isVisible = it.isLoading
            if(it.isLoading){
                binding.cvEmail.visibility = View.INVISIBLE
            } else {
                binding.cvEmail.visibility = View.VISIBLE
                val userRandomList = it.userRandom
                if (userRandomList.isNotEmpty()) {
                    binding.tvEmail.text = "Email: ${userRandomList[0].email}"
                    binding.tvPass.text = "Password: ${userRandomList[0].login.password}"
                    Glide.with(binding.root).load(userRandomList[0].picture.thumbnail).into(binding.ivPicture)
                }
            }
        })

        emailViewModel.onUserProvider()
        emailViewModel.showDatas()
        binding.bntLoadNewUser.setOnClickListener { emailViewModel.onNewUser() }
        binding.bntSaveDatas.setOnClickListener {
            emailViewModel.onSaveDatas()
            alert()
        }

        binding.tvVerDatas.setOnClickListener {
                /*parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container,fragmentDatas!!)
                    .commit()*/

            val intent = Intent(context,EnailShowDatasActivity::class.java)
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