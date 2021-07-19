package com.precious.networkcaller.ui

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.precious.networkcaller.api.RetrofitProvider
import com.precious.networkcaller.api.RetrofitProvider.placeholderAPI
import com.precious.networkcaller.models.Item
import com.precious.networkcaller.models.Student
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {
    val itemsLiveData = MutableLiveData<List<Item>>()

    fun getItems(){
        CoroutineScope(Dispatchers.IO).launch {
            itemsLiveData.postValue(RetrofitProvider.placeholderAPI.getItems())
        }
    }

    fun addStudent(studentData: Student, onResult: (Student) -> Unit){
        placeholderAPI.addStudent(studentData).enqueue(
            object : Callback<Student> {
                override fun onFailure(call: Call<Student>, t: Throwable) {
                    Log.e(TAG, "Fail to Post Student Data")
                }

                override fun onResponse(call: Call<Student>, response: Response<Student>) {
                    val addedStudent = response.body()!!
                    onResult(addedStudent)
                }


            }
        )
    }
}