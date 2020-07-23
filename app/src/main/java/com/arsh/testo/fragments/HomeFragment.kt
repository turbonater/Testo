package com.arsh.testo.fragments

import android.icu.text.CaseMap
import android.os.Bundle
import android.os.Message
import android.text.Layout
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.arsh.testo.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.zip.Inflater

class HomeFragment : Fragment(), View.OnClickListener {

    lateinit var navController: NavController
    lateinit var txtInputTitle: TextInputLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        view.findViewById<Button>(R.id.btnMakeTest).setOnClickListener(this)
        view.findViewById<Button>(R.id.btnTakeTest).setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnMakeTest -> {
                navigateToMakeTestWithDialog(
                    "Title",
                    "Enter a Title Please!",
                    "A suitable title for your test"
                )
            }
            R.id.btnTakeTest -> navigateToTakeTestWithDialog(
                "Unique Id",
                "Enter to take a test!",
                " The Id is used to identify a test"
            )
        }
    }

    fun navigateToMakeTestWithDialog(title: String, hint: String, helpText: String) {
        val builder = MaterialAlertDialogBuilder(requireContext())
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.input_dialog, null)
        txtInputTitle = dialogLayout.findViewById(R.id.txtInputDialog)
        txtInputTitle.hint = hint
        txtInputTitle.helperText = helpText
        val testTitle = txtInputTitle.editText?.text
        builder.setView(dialogLayout)
            .setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }
        builder.setPositiveButton("Ok") { dialog, which ->
            val action =
                HomeFragmentDirections.actionHomeFragmentToCreateTestFragment(testTitle.toString())
            navController.navigate(action)
        }.setCancelable(false)
        builder.show()
    }

    fun navigateToTakeTestWithDialog(title: String, hint: String, helpText: String) {
        val builder = MaterialAlertDialogBuilder(requireContext())
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.input_dialog, null)
        txtInputTitle = dialogLayout.findViewById(R.id.txtInputDialog)
        txtInputTitle.hint = hint
        txtInputTitle.helperText = helpText
        val uId = txtInputTitle.editText?.text
        builder.setView(dialogLayout)
            .setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }
        builder.setPositiveButton("Ok") { dialog, which ->
            val action = HomeFragmentDirections.actionHomeFragmentToTakeTestFragment(uId.toString())
            navController.navigate(action)
        }.setCancelable(false)
        builder.show()
    }
}
