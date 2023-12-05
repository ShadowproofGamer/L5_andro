package com.example.l5_andro

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.l5_andro.databinding.FragmentShowBinding

class ShowFragment : Fragment() {
    private lateinit var _binding: FragmentShowBinding
    lateinit var showName: TextView
    lateinit var showSpec: TextView
    lateinit var showStrength: ProgressBar
    lateinit var showType: ImageView
    lateinit var showDanger: CheckBox
    lateinit var returnButton: Button
    lateinit var modifyButton: Button
    var tempType = "Human"
    var changed = false
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
        _binding = FragmentShowBinding.inflate(inflater, container, false)
        showName = _binding.showName
        showSpec = _binding.showSpec
        showStrength = _binding.showStrengthBar
        showType = _binding.showType
        showDanger = _binding.showDangerous
        returnButton = _binding.showReturnButton
        modifyButton = _binding.showModifyButton
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //var returnButton = requireActivity().findViewById<View>(R.id.show_return_button)
        returnButton.setOnClickListener {
            //item modifications
            parentFragmentManager.setFragmentResult(
                "mainmodmsg", bundleOf(
                    "toChange" to changed,
                    "name" to showName.text.toString(),
                    "spec" to showSpec.text.toString(),
                    "strength" to showStrength.progress.toFloat(),
                    "danger" to showDanger.isChecked,
                    "type" to tempType,
                    "id" to itemId,
                    "humanoids" to arrayOf("Human", "NPC", "Orc")
                )
            )
            requireActivity().onBackPressed()
        }
        parentFragmentManager.setFragmentResultListener(
            "msgtochild",
            viewLifecycleOwner
        ) { _, bundle ->
            run {
                /*
                    "name" to currData.text_name,
                    "spec" to currData.text_spec,
                    "strength" to currData.item_strength,
                    "danger" to currData.dangerous,
                    "type" to currData.item_type,
                    "humanoids" to currData.humanoids
                 */
                showName.text = bundle.getString("name")
                showSpec.text = bundle.getString("spec")
                showDanger.isChecked = bundle.getBoolean("danger")
                showStrength.progress = bundle.getFloat("strength").toInt()
                when (bundle.getString("type")) {
                    "Human" -> showType.setImageResource(R.drawable.human)
                    "NPC" -> showType.setImageResource(R.drawable.npc)
                    "Orc" -> showType.setImageResource(R.drawable.enemy)
                    else -> showType.setImageResource(R.drawable.human)
                }
                tempType = bundle.getString("type").toString()
                itemId = bundle.getInt("id")
            }
        }

        parentFragmentManager.setFragmentResultListener(
            "addmodmsg",
            viewLifecycleOwner
        ) { _, bundle ->
            if (bundle.getBoolean("toChange")) {

                changed = true
                //requireActivity().recreate()
            }
            run {
                /*
                    "name" to currData.text_name,
                    "spec" to currData.text_spec,
                    "strength" to currData.item_strength,
                    "danger" to currData.dangerous,
                    "type" to currData.item_type,
                    "humanoids" to currData.humanoids
                 */
                showName.text = bundle.getString("name")
                showSpec.text = bundle.getString("spec")
                showDanger.isChecked = bundle.getBoolean("danger")
                showStrength.progress = (bundle.getFloat("strength")*2.0).toInt()
                when (bundle.getString("type")) {
                    "Human" -> showType.setImageResource(R.drawable.human)
                    "NPC" -> showType.setImageResource(R.drawable.npc)
                    "Orc" -> showType.setImageResource(R.drawable.enemy)
                    else -> showType.setImageResource(R.drawable.human)
                }
                tempType = bundle.getString("type").toString()
                itemId = bundle.getInt("id")
            }
        }




        modifyButton.setOnClickListener { _ ->
            parentFragmentManager.setFragmentResult(
                "modmsg", bundleOf(
                    "name" to showName.text.toString(),
                    "spec" to showSpec.text.toString(),
                    "strength" to showStrength.progress.toFloat(),
                    "danger" to showDanger.isChecked,
                    "type" to tempType,
                    "id" to itemId,
                    "humanoids" to arrayOf("Human", "NPC", "Orc")
                )
            )

            findNavController().navigate(R.id.action_show_frag_to_add_frag)

        }


    }

    override fun onPause() {
        super.onPause()
        //item modifications
        parentFragmentManager.setFragmentResult(
            "mainmodmsg", bundleOf(
                "toChange" to changed,
                "name" to showName.text.toString(),
                "spec" to showSpec.text.toString(),
                "strength" to showStrength.progress.toFloat(),
                "danger" to showDanger.isChecked,
                "type" to tempType,
                "id" to itemId,
                "humanoids" to arrayOf("Human", "NPC", "Orc")
            )
        )
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            ShowFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}