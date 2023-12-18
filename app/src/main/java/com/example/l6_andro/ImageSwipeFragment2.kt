package com.example.l6_andro

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.l6_andro.R
import com.example.l6_andro.databinding.FragmentImageSliderBinding
import com.example.l6_andro.databinding.FragmentImageSwipeBinding

class ImageSwipeFragment2 : Fragment() {
    private lateinit var _binding: FragmentImageSwipeBinding
    private lateinit var _adapter: ImagePagerAdapter2
    private lateinit var imageRepo: ImageRepo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        imageRepo = ImageRepo.getInstance(requireContext())
        arguments?.let {
        }
        _adapter = ImagePagerAdapter2(this, imageRepo.getSharedList()!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentImageSwipeBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager = _binding.viewPager2

        val position = arguments?.getInt("position")

        viewPager.adapter = _adapter

        // Retrieve the photo path from the arguments
        val photoPath = arguments?.getString("path")

        // Set the current item of the ViewPager to this position
        viewPager.setCurrentItem(position!!, false)

        _binding.imageSaveButton.setOnClickListener { _ ->
            val item = viewPager.currentItem
            val image = _adapter.getImage(item)

            parentFragmentManager.setFragmentResult("preferences1",
                bundleOf("img" to image.curi)
            )

            findNavController().navigate(R.id.action_global_mainFragment)

            /*
            val data: SharedPreferences = requireActivity().getSharedPreferences("L5_preferences", Context.MODE_PRIVATE)
            val editor = data.edit()
            editor.putString("image", image.curi.toString())
            editor.apply()
            requireActivity().onBackPressed()

             */
        }
    }



}