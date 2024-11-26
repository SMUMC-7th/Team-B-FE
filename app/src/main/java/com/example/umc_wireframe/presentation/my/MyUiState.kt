package com.example.umc_wireframe.presentation.my

data class MyUiState(
    val name: String,
    val nickName:String
){
    companion object{
        fun init() = MyUiState(
            name = "",
            nickName = ""
        )
    }
}