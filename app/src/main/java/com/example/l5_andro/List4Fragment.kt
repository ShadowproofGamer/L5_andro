package com.example.l5_andro

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.l5_andro.databinding.FragmentListBinding
import com.example.l5_andro.databinding.ListItemBinding

class List4Fragment : Fragment() {

    private lateinit var _binding: FragmentListBinding

    //val dataRepo = DataRepo.getInstance()
    lateinit var dataRepo: MyRepository
    lateinit var adapter: MyAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
        dataRepo = MyRepository.getinstance(requireContext())
        adapter = MyAdapter(dataRepo.getData()!!)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        val recView = _binding.recView
        recView.layoutManager = LinearLayoutManager(requireContext())

        //val adapter = DataRepo.getInstance().getData()?.let { MyAdapter(it) }
        recView.adapter = adapter

        return _binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*
                    "name" to currData.text_name,
                    "spec" to currData.text_spec,
                    "strength" to currData.item_strength,
                    "danger" to currData.dangerous,
                    "type" to currData.item_type,
                    "humanoids" to currData.humanoids
         */
        parentFragmentManager.setFragmentResultListener(
            "addNewItem",
            viewLifecycleOwner
        ) { string, bundle ->
            run {
                if (bundle.getBoolean("toAdd")) {
                    val itemName = bundle.getString("name", "New person")
                    val itemSpec = bundle.getString("spec", "Some spec")
                    val itemStrength = bundle.getFloat("strength", 1.0F)
                    val itemDanger = bundle.getBoolean("danger", false)
                    val itemType = bundle.getString("type", "Human")
                    val newItem = DBItem(itemName, itemSpec, itemStrength, itemType, itemDanger)
                    adapter.addItem(newItem)
                }
            }
        }

        //modification of item
        parentFragmentManager.setFragmentResultListener(
            "mainmodmsg",
            viewLifecycleOwner
        ) { string, bundle ->
            run {
                if (bundle.getBoolean("toChange")) {
                    val itemName = bundle.getString("name", "New person")
                    val itemSpec = bundle.getString("spec", "Some spec")
                    val itemStrength = bundle.getFloat("strength", 1.0F)
                    val itemDanger = bundle.getBoolean("danger", false)
                    val itemType = bundle.getString("type", "Human")
                    val id = bundle.getInt("id", 0)
                    val updateItem =
                        DBItem(id, itemName, itemSpec, itemStrength, itemType, itemDanger)
                    adapter.updateItem(updateItem)
                }
            }
        }

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_item_add -> {
                //Toast.makeText(requireActivity(), "new item clicked!", Toast.LENGTH_SHORT).show()
                //go to adder fragment
                findNavController().navigate(R.id.action_list_frag_to_add_frag)
            }
        }
        return super.onOptionsItemSelected(item)
    }





    inner class MyAdapter(var data: MutableList<DBItem>) :
        RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
        inner class MyViewHolder(viewBinding: ListItemBinding) :
            RecyclerView.ViewHolder(viewBinding.root) {
            val txt1: TextView = viewBinding.itemTitle
            val txt2: TextView = viewBinding.itemSpec1
            val img: ImageView = viewBinding.itemImg
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val viewBinding = ListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
            return MyViewHolder(viewBinding)
        }

        override fun getItemCount(): Int {
            return data.size
        }

        fun addItem(item: DBItem): Boolean {
            if (dataRepo.addItem(item))
                notifyDataSetChanged()
            requireActivity().recreate()
            return true
        }

        fun updateItem(item: DBItem): Boolean {
            if (dataRepo.modifyItem(item))
                notifyDataSetChanged()
            requireActivity().recreate()
            return true
        }


        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            var currData = data[position]
            holder.txt1.text = currData.text_name
            holder.txt2.text = if (currData.text_spec == "Default specification") {
                (currData.item_type + " " + currData.text_spec + " " + currData.item_strength)
            } else {
                currData.text_spec
            }
            holder.itemView.setOnClickListener { _ ->
                parentFragmentManager.setFragmentResult(
                    "msgtochild", bundleOf(
                        "name" to currData.text_name,
                        "spec" to currData.text_spec,
                        "strength" to currData.item_strength,
                        "danger" to currData.dangerous,
                        "type" to currData.item_type,
                        "humanoids" to arrayOf("Human", "NPC", "Orc"),
                        "id" to currData.id
                    )
                )

                //modifying item
                /*
                parentFragmentManager.setFragmentResultListener(
                    "mainmodmsg",
                    viewLifecycleOwner
                ) { _, bundle ->
                    if (bundle.getBoolean("toChange")) {
                        print("Changing!")
                        currData.text_name = bundle.getString("name").toString()
                        currData.text_spec = bundle.getString("spec").toString()
                        currData.dangerous = bundle.getBoolean("danger")
                        currData.item_strength = bundle.getFloat("strength")
                        currData.item_type = bundle.getString("type").toString()
                        notifyDataSetChanged()
                    }
                }

                 */
                findNavController().navigate(R.id.action_list_frag_to_show_frag)

                /*
                //temp for clicking an object
                Toast.makeText(requireContext(),
                    "You clicked: " + (position + 1),

                    Toast.LENGTH_SHORT).show()
                 */
            }
            holder.itemView.setOnLongClickListener {
                val builder: AlertDialog.Builder = AlertDialog.Builder(requireActivity())
                builder
                    .setTitle("Delete item")
                    .setMessage("Are you sure you want to delete?")
                    //.setSingleChoiceItems()
                    .setPositiveButton("Yes") { dialog, which ->
                        if (dataRepo.deleteItem(currData)) {
                            notifyDataSetChanged()
                            requireActivity().recreate()
                        }
                    }.setNegativeButton("No") { dialog, which ->
                        dialog.cancel()
                    }.create().show()
                true
            }

            when (currData.item_type) {
                "Human" -> holder.img.setImageResource(R.drawable.human)
                "NPC" -> holder.img.setImageResource(R.drawable.npc)
                "Orc" -> holder.img.setImageResource(R.drawable.enemy)
            }
        }
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            List4Fragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}