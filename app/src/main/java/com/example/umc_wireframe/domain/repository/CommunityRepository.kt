package com.example.umc_wireframe.domain.repository

import com.example.umc_wireframe.data.model.PostDetailResponse
import com.example.umc_wireframe.data.model.ServerResponse
import com.example.umc_wireframe.domain.model.entity.CommentResultEntity
import com.example.umc_wireframe.domain.model.entity.PostDetailEntity
import com.example.umc_wireframe.domain.model.entity.PostListEntity
import com.example.umc_wireframe.domain.model.entity.ServerEntity
import retrofit2.http.Path

interface CommunityRepository {
    //community

    //post
    suspend fun getPost(
        postId: String
    ): ServerResponse<PostDetailResponse>

    suspend fun getPostList(
        page: Int
    ): ServerEntity<PostListEntity>

    suspend fun postPost(
        content: String,
        title: String
    ): ServerEntity<String>

    suspend fun patchPost(
        postId: String
    )

    suspend fun deletePost(
        postId: String
    )

    //comment
    suspend fun postComment(
        postId: String,
        comment: String,
        parentId: Int = 0
    ): ServerEntity<String>

    suspend fun getCommentList(
        postId: String,
        cursor: Long = 0,
        size: Int = 10
    ): ServerEntity<CommentResultEntity>

    suspend fun patchComment(
        commentId: String,
    )


    suspend fun patchReport(
        commentId: String
    )

    suspend fun deleteComment(
        commentId: String
    )
}