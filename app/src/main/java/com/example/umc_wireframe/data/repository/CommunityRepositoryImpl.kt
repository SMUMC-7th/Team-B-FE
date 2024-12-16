package com.example.umc_wireframe.data.repository

import com.example.umc_wireframe.data.remote.PostSet
import com.example.umc_wireframe.data.remote.ServerDatasource
import com.example.umc_wireframe.domain.model.entity.PostListEntity
import com.example.umc_wireframe.domain.model.entity.ServerEntity
import com.example.umc_wireframe.domain.model.mapper.toPostListEntity
import com.example.umc_wireframe.domain.model.mapper.toTempEntity
import com.example.umc_wireframe.domain.repository.CommunityRepository

class CommunityRepositoryImpl(
    private val datasource: ServerDatasource
) : CommunityRepository {
    // Post
    override suspend fun getPost(postId: String) {
        datasource.getPost(postId)
    }

    override suspend fun getPostList(
        page: Int
    ): ServerEntity<PostListEntity> = datasource.getPostList(page).toPostListEntity()


    override suspend fun postPost(
        content: String,
        title: String
    ): ServerEntity<String> = datasource.postPost(
        PostSet(
            title = title,
            content = content
        )
    ).toTempEntity()

    override suspend fun patchPost(postId: String) {
        datasource.patchPost(postId)
    }

    override suspend fun deletePost(postId: String) {
        datasource.deletePost(postId)
    }

    // Comment
    override suspend fun postComment(postId: String) {
        datasource.postComment(postId)
    }

    override suspend fun getCommentList(postId: String) {
        datasource.getCommentList(postId)
    }

    override suspend fun patchComment(commentId: String) {
        datasource.patchComment(commentId)
    }

    override suspend fun patchReport(commentId: String) {
        datasource.patchReport(commentId)
    }

    override suspend fun deleteComment(commentId: String) {
        datasource.deleteComment(commentId)
    }
}