package com.example.umc_wireframe.data.repository

import com.example.umc_wireframe.data.model.PostDetailResponse
import com.example.umc_wireframe.data.model.ServerResponse
import com.example.umc_wireframe.data.remote.CommentSet
import com.example.umc_wireframe.data.remote.PostSet
import com.example.umc_wireframe.data.remote.ServerDatasource
import com.example.umc_wireframe.domain.model.entity.CommentResultEntity
import com.example.umc_wireframe.domain.model.entity.PostCommentResultEntity
import com.example.umc_wireframe.domain.model.entity.PostListEntity
import com.example.umc_wireframe.domain.model.entity.ServerEntity
import com.example.umc_wireframe.domain.model.mapper.toCommentResultEntity
import com.example.umc_wireframe.domain.model.mapper.toPostListEntity
import com.example.umc_wireframe.domain.model.mapper.toTempEntity
import com.example.umc_wireframe.domain.repository.CommunityRepository

class CommunityRepositoryImpl(
    private val datasource: ServerDatasource
) : CommunityRepository {
    // Post
    override suspend fun getPost(
        postId: String
    ): ServerResponse<PostDetailResponse> =
        datasource.getPost(postId)


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
    override suspend fun postComment(
        postId: String,
        comment: String,
        parentId: Int
    ): ServerEntity<PostCommentResultEntity> = datasource.postComment(
        postId = postId,
        comment = CommentSet(
            content = comment,
            parentId = parentId
        )
    )


    override suspend fun getCommentList(
        postId: String,
        cursor: Long,
        size: Int
    ): ServerEntity<CommentResultEntity> = datasource.getCommentList(
        postId = postId,
        cursor = cursor,
        size = size
    ).toCommentResultEntity()


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