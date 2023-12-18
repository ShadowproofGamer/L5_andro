package com.example.l6_andro

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ImagePagerAdapter2(fa: Fragment, images: MutableList<ImageItem>): FragmentStateAdapter(fa) {

    private var position = 0
    private var images: MutableList<ImageItem> = images

    override fun createFragment(position: Int): Fragment {
        this.position = position
        Log.w("try", position.toString())
        return ImageFragment2.newInstance(images[position])
    }

    override fun getItemCount(): Int {
        return images.size
    }

    fun getCurrentItem(): Int {
        return this.position
    }

    fun getImage(position: Int): ImageItem {
        return images[position]
    }
}




