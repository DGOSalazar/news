package com.raven.home.domain.usecases

import android.content.Context
import com.raven.home.data.HomeRepository
import com.raven.home.data.database.entities.ArticleEntity
import com.raven.home.data.remote.models.ApiResponseModel
import com.raven.home.utils.Resource
import com.raven.home.utils.Status
import com.raven.home.domain.mapper.GetNewsMapper
import com.raven.home.domain.models.Article
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.times
import org.mockito.Mockito.verify

class GetNewsUseCaseTest {
    @RelaxedMockK
    private lateinit var homeRepository: HomeRepository
    @RelaxedMockK
    private lateinit var mapper : GetNewsMapper
    @RelaxedMockK
    private lateinit var context : Context
    private lateinit var getNewsUseCase: GetNewsUseCase

    @Before
    fun init() {
        MockKAnnotations.init(this)
        getNewsUseCase = GetNewsUseCase(homeRepository, mapper, context)
    }
    @Test
    fun getNewsUseCaseTestForApiSuccess() = runBlocking {
        //GIVEN
        val resApi = ApiResponseModel()
        val expectedRes = listOf<Article>()
        coEvery { homeRepository.getNews() } returns (flowOf(Resource.success(resApi)))
        //WHEN
        val res = getNewsUseCase.execute(Unit)
        res.collect { result ->
            //THEN
            assertEquals(expectedRes, result.data)
            assertEquals(Status.SUCCESS, result.status)
            verify(homeRepository, times(1)).getNews()
        }
    }
    @Test
    fun getNewsUseCaseTestForApiError() = runTest {
        //GIVEN
        coEvery { homeRepository.getNews() } returns (flowOf(Resource.error(null)))
        //WHEN
        val res = getNewsUseCase.execute(Unit)
        res.collect { result ->
            //THEN
            assertEquals(null, result.data)
            assertEquals(Status.ERROR, result.status)
            verify(homeRepository, times(1)).getNews()
        }
    }
    @Test
    fun getNewsUseCaseTestForDaoSuccess() = runTest {
        //GIVEN
        val articleEntity = ArticleEntity(title = "test1")
        coEvery { homeRepository.getNewsDao() } returns (flowOf(listOf((articleEntity))))
        //WHEN
        val res = getNewsUseCase.execute(Unit)
        res.collect { result ->
            //THEN
            assertEquals(Status.SUCCESS, result.status)
        }
    }
}