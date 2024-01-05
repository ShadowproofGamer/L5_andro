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
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.l5_andro.databinding.FragmentListBinding
import com.example.l5_andro.databinding.ListItemBinding

class List5Fragment : Fragment() {

    private lateinit var _binding: FragmentListBinding

    //val dataRepo = DataRepo.getInstance()
    lateinit var dataRepo: MyViewModel
    lateinit var adapter: MyAdapter

    val myVModel : MyViewModel by activityViewModels { MyViewModel.Factory}




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
        dataRepo = myVModel //MyViewModel(requireContext())
        adapter = MyAdapter(onItemAction)

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

        myVModel.getData2()?.observe(viewLifecycleOwner){ datalist ->
            adapter.submitList(datalist)
        }

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

    var onItemAction: (item: DBItem, action: Int) -> Unit = { item, action ->
        when (action) {
            1 -> {
                parentFragmentManager.setFragmentResult(
                    "msgtochild", bundleOf(
                        "name" to item.text_name,
                        "spec" to item.text_spec,
                        "strength" to item.item_strength,
                        "danger" to item.dangerous,
                        "type" to item.item_type,
                        "humanoids" to arrayOf("Human", "NPC", "Orc"),
                        "id" to item.id
                    )
                )
                findNavController().navigate(R.id.show_frag)
            }

            2 -> {
                val builder: AlertDialog.Builder = AlertDialog.Builder(requireActivity())
                builder
                    .setTitle("Delete item")
                    .setMessage("Are you sure you want to delete?")
                    //.setSingleChoiceItems()
                    .setPositiveButton("Yes") { dialog, which ->
                        dataRepo.deleteItem(item)
                        /*
                        if (dataRepo.deleteItem(item)) {
                            adapter.submitList(dataRepo.getData())
                        }

                         */
                    }.setNegativeButton("No") { dialog, which ->
                        dialog.cancel()
                    }.create().show()
                true
            }
        }
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
                findNavController().navigate(R.id.add_frag)
            }
        }
        return super.onOptionsItemSelected(item)
    }


    //(var data: MutableList<DBItem>)
    inner class MyAdapter(private val onItemAction: (item: DBItem, action: Int) -> Unit) :
        ListAdapter<DBItem, MyAdapter.MyViewHolder>(DiffCallback) {
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

        //override fun getItemCount(): Int {
        //    return 10
        //}

        fun addItem(item: DBItem): Boolean {
            dataRepo.addItem(item)
            /*
            if (dataRepo.addItem(item))
                adapter.submitList(dataRepo.getData())

             */
            return true
        }

        fun updateItem(item: DBItem): Boolean {
            dataRepo.modifyItem(item)
            /*
            if (dataRepo.modifyItem(item))
                adapter.submitList(dataRepo.getData())

             */
            return true
        }


        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            var currData = getItem(position)
            holder.txt1.text = currData.text_name
            holder.txt2.text = if (currData.text_spec == "Default specification") {
                (currData.item_type + " " + currData.text_spec + " " + currData.item_strength)
            } else {
                currData.text_spec
            }
            holder.itemView.setOnClickListener { _ ->
                val position = holder.adapterPosition
                onItemAction(getItem(position), 1)
            }
            holder.itemView.setOnLongClickListener {
                val position = holder.adapterPosition
                onItemAction(getItem(position), 2)
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
        private val DiffCallback = object : DiffUtil.ItemCallback<DBItem>() {
            override fun areItemsTheSame(oldItem: DBItem, newItem: DBItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DBItem, newItem: DBItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}