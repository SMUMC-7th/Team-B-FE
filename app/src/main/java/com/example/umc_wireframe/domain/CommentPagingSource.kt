package com.example.umc_wireframe.domain

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.umc_wireframe.domain.model.entity.CommentResultEntity
import com.example.umc_wireframe.domain.repository.CommunityRepository

class CommentPagingSource(
    private val repository: CommunityRepository,
    private val postId: String
) : PagingSource<Long, CommentResultEntity.CommentEntity>() {

    override suspend fun load(params: LoadParams<Long>): LoadResult<Long, CommentResultEntity.CommentEntity> {
        return try {
            // 현재 커서 (초기값은 0)
            val cursor: Long = params.key ?: 0.toLong()

            // 데이터 요청
            val entity = repository.getCommentList(
                postId = postId,
                cursor = cursor,
                size = params.loadSize
            )

            // 다음 커서 계산
                val nextKey = if (entity.result?.list?.isEmpty() != false) null else entity.result.lastId.toLong()

                // 결과 반환
                LoadResult.Page(
                    data = entity.result!!.list,
                    prevKey = null, // 이전 키가 필요 없을 경우 null
                    nextKey = nextKey
                )
        } catch (e: Exception) {
            // 에러 처리
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }
    override fun getRefreshKey(state: PagingState<Long, CommentResultEntity.CommentEntity>): Long? {
        return state.anchorPosition?.let { anchorPosition ->
            // 가까운 페이지를 기준으로 prevKey와 nextKey를 계산
            state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
                ?: state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
        }
    }
}

