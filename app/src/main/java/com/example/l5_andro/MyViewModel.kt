package com.example.l5_andro

import android.content.Context
import android.text.Spannable.Factory
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras

class MyViewModel(context: Context) : ViewModel() {
    private lateinit var myRepository: MyRepository
    private lateinit var dataList: MutableList<DBItem>
    init {
        myRepository = MyRepository(context)
        dataList = myRepository.getData()!!
    }

    fun getData(): MutableList<DBItem>? {
        return myRepository.getData()
    }
    fun getData2(): LiveData<List<DBItem>>?{
        return myRepository.getData2()
    }
    fun addItem(item: DBItem) : Boolean {
        return myRepository.addItem(item)
    }
    fun deleteItem(item: DBItem) : Boolean {
        return myRepository.deleteItem(item)
    }

    //modifying item
    fun modifyItem(item: DBItem): Boolean {
        return myRepository.modifyItem(item)
    }


    companion object{
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val application = checkNotNull(extras[APPLICATION_KEY])
                return MyViewModel(application.applicationContext) as T
            }
        }
    }


}