package com.example.l6_andro

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.l6_andro.databinding.FragmentImageBinding
import java.io.FileNotFoundException
import java.io.InputStream

private const val ARG_PARAM1: Uri = Uri.EMPTY
class ImageFragment2 : Fragment() {
    private var imgId: Int? = null
    private lateinit var _binding: FragmentImageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            imgId = it.getInt(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentImageBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (this.imgId != null) {
            _binding.imageImg.setImageBitmap(getBitmapFromUri(requireContext(), imgId))
        }
    }
    fun getBitmapFromUri(mContext: Context, uri: Uri?): Bitmap? {
        var bitmap: Bitmap? = null
        try {
            val image_stream: InputStream
            try {
                image_stream = uri?.let {
                    mContext.getContentResolver().openInputStream(it)
                }!!
                bitmap = BitmapFactory.decodeStream(image_stream)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return bitmap
    }

    companion object {

        @JvmStatic
        fun newInstance(imgId: ImageItem) =
            ImageFragment2().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, imgId)
                }
            }
    }
}