package com.example.l5_andro

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.l5_andro.databinding.FragmentImageSwipeBinding

class ImageSwipeFragment : Fragment() {
    private lateinit var _binding: FragmentImageSwipeBinding
    private lateinit var _adapter: ImagePagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
        _adapter = ImagePagerAdapter(this)
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
        viewPager.adapter = _adapter

        _binding.imageSaveButton.setOnClickListener { _ ->
            val item = viewPager.currentItem
            var image = -1

            when (item) {
                0 -> image = R.drawable.emotion_neutral
                1 -> image = R.drawable.enemy_title
                2 -> image = R.drawable.human_title
                3 -> image = R.drawable.npc_title
            }

            val data: SharedPreferences = requireActivity().getSharedPreferences("L5_preferences", Context.MODE_PRIVATE)
            val editor = data.edit()
            editor.putInt("image", image)
            editor.apply()
            requireActivity().onBackPressed()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ImageSwipeFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}