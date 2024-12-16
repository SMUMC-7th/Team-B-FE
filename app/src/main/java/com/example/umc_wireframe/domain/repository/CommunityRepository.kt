package com.example.umc_wireframe.domain.repository

import com.example.umc_wireframe.domain.model.entity.PostListEntity
import com.example.umc_wireframe.domain.model.entity.ServerEntity
import retrofit2.http.Path

interface CommunityRepository {
    //community

    //post
    suspend fun getPost(
        postId: String
    )

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
        postId: String
    )

    suspend fun getCommentList(
        postId: String
    )

    suspend fun patchComment(
        commentId: String
    )

    suspend fun patchReport(
        @Path("commentId") commentId: String
    )

    suspend fun deleteComment(
        @Path("commentId") commentId: String
    )
}