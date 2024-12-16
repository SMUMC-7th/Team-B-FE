package com.example.umc_wireframe.presentation.community.detail

data class PostDetailCommentItem(
    val writer: String,
    val content:String,
    val replyList: List<ReplyItem>
)

data class ReplyItem(
    val writer: String,
    val content:String,
    val report: Boolean
)