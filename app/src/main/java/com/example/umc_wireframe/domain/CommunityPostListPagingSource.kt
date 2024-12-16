package com.example.umc_wireframe.domain

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.umc_wireframe.domain.model.entity.PostEntity
import com.example.umc_wireframe.domain.repository.CommunityRepository
import retrofit2.HttpException
import java.io.IOException

class CommunityPostListPagingSource(
    private val communityRepository: CommunityRepository
) : PagingSource<Int, PostEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PostEntity> {
        val page = params.key ?: 1 // 첫 페이지는 1, 이후 페이지는 params.key 사용
        return try {
            // 서버에서 데이터 가져오기
            val entity = communityRepository.getPostList(page) // page를 이용해 서버에서 데이터를 요청

            // 서버에서 반환된 데이터를 PagingData로 변환하여 반환
            LoadResult.Page(
                data = entity.result!!.postList, // 서버에서 받아온 데이터
                prevKey = if (page == 1) null else page - 1, // 이전 페이지
                nextKey = if (entity.result.isLast) null else page + 1 // 다음 페이지
            )
        } catch (e: IOException) {
            // 네트워크 오류 처리
            LoadResult.Error(e)
        } catch (e: HttpException) {
            // 서버 오류 처리
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PostEntity>): Int? {
        // 이전/다음 페이지로 새로고침을 시도할 때 사용 (보통 null 반환)
        return state.anchorPosition?.let { state.closestPageToPosition(it)?.prevKey }
    }
}
