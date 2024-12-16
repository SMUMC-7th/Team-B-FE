package com.example.umc_wireframe.presentation.community.write

sealed interface PostWriteUiStates {
    data object init: PostWriteUiStates
    data object success: PostWriteUiStates
    data object fail: PostWriteUiStates
}