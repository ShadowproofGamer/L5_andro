package com.example.l5_andro

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.RatingBar
import androidx.core.os.bundleOf
import com.example.l5_andro.databinding.FragmentAddBinding

class AddFragment : Fragment() {
    private lateinit var _binding: FragmentAddBinding
    lateinit var addName: EditText
    lateinit var addSpec: EditText
    lateinit var addStrength: RatingBar
    lateinit var addType: RadioGroup
    lateinit var addDanger: CheckBox

    lateinit var saveButton: Button
    lateinit var cancelButton: Button

    //says if modification or addition
    var mod = false
    var itemId = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        addName = _binding.addName
        addSpec = _binding.addSpec
        addStrength = _binding.addStrengthBar
        addType = _binding.addRadio
        addDanger = _binding.addDanger
        saveButton = _binding.addSaveButton
        cancelButton = _binding.addCancelButton


        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var race: String = "Human"

        //allowing for modifications
        parentFragmentManager.setFragmentResultListener("modmsg", viewLifecycleOwner) { _, bundle ->
            run {
                mod = true
                addName.setText(bundle.getString("name"))
                addSpec.setText(bundle.getString("spec"))
                addDanger.isChecked = bundle.getBoolean("danger")
                addStrength.progress = bundle.getFloat("strength").toInt()
                when (bundle.getString("type")) {
                    "Human" -> _binding.addTypeHuman.isChecked = true
                    "NPC" -> _binding.addTypeNPC.isChecked = true
                    "Orc" -> _binding.addTypeOrc.isChecked = true
                    else -> _binding.addTypeHuman.isChecked = true
                }
                itemId = bundle.getInt("id")
                race = bundle.getString("type").toString()
            }
        }


        //changing race depending on item checked

        addType.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                _binding.addTypeHuman.id -> race = "Human"
                _binding.addTypeNPC.id -> race = "NPC"
                _binding.addTypeOrc.id -> race = "Orc"
            }
        }

        //going back without changes
        cancelButton.setOnClickListener {
            parentFragmentManager.setFragmentResult(
                "addNewItem", bundleOf(
                    "toAdd" to false
                )
            )

            //for mods
            parentFragmentManager.setFragmentResult(
                "addmodmsg", bundleOf(
                    "name" to addName.text.toString(),
                    "spec" to addSpec.text.toString(),
                    "strength" to addStrength.rating*2,
                    "danger" to addDanger.isChecked,
                    "type" to race,
                    "toAdd" to false,
                    "id" to itemId,
                    "toChange" to false
                )
            )

            requireActivity().onBackPressed()
        }

        //going back with changes
        saveButton.setOnClickListener {
            val name: String = if (addName.text.toString() == "") {
                "Default name"
            } else {
                addName.text.toString()
            }
            val spec: String = if (addSpec.text.toString() == "") {
                "Default spec"
            } else {
                addSpec.text.toString()
            }
            if (mod) {
                parentFragmentManager.setFragmentResult(
                    "addmodmsg", bundleOf(
                        "name" to name,
                        "spec" to spec,
                        "strength" to addStrength.rating,
                        "danger" to addDanger.isChecked,
                        "type" to race,
                        "toAdd" to false,
                        "id" to itemId,
                        "toChange" to true
                    )
                )
            } else {
                parentFragmentManager.setFragmentResult(
                    "addNewItem", bundleOf(
                        "name" to name,
                        "spec" to spec,
                        "strength" to addStrength.rating*2,
                        "danger" to addDanger.isChecked,
                        "type" to race,
                        "toAdd" to true,
                        "toChange" to false
                    )
                )
            }
            requireActivity().onBackPressed()
        }


    }



    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}