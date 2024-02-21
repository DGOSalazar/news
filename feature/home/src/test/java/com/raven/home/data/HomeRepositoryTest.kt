package com.raven.home.data

import com.raven.home.data.database.dao.Dao
import com.raven.home.data.database.entities.ArticleEntity
import com.raven.home.data.remote.models.ApiResponseModel
import com.raven.home.utils.Status
import com.raven.home.data.remote.service.HomeService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class HomeRepositoryTest {
    @RelaxedMockK
    private lateinit var apiService: HomeService
    @RelaxedMockK
    private lateinit var dao : Dao
    private lateinit var homeRepository: HomeRepository

    @Before
    fun init() {
        MockKAnnotations.init(this)
        homeRepository = HomeRepository(apiService, dao)
    }

    @Test
    fun getNewsSuccessTest() = runBlocking {
        //GIVEN
        val resApi = ApiResponseModel()
        coEvery { apiService.getNews() } returns Response.success(resApi)
        //WHEN
        val res = homeRepository.getNews()
        res.collect { result ->
            //THEN
            Assert.assertEquals(resApi, result.data)
            Assert.assertEquals(Status.SUCCESS, result.status)
        }
    }

    @Test
    fun getNewsErrorTest() = runBlocking {
        //GIVEN
        val errorResponse = "{}"
        val errorBody: ResponseBody = errorResponse.toResponseBody()

        coEvery { apiService.getNews() } returns Response.error(400, errorBody)
        //WHEN
        val res = homeRepository.getNews()
        res.collect { result ->
            //THEN
            Assert.assertEquals(400, result.code)
            Assert.assertEquals(Status.ERROR, result.status)
        }
    }

    @Test
    fun getNewsDaoTest() = runBlocking {
        //GIVEN
        val resApi = listOf<ArticleEntity>()
        coEvery { dao.getArticles() } returns (resApi)
        //WHEN
        val res = homeRepository.getNewsDao()
        res.collect { result ->
            //THEN
            Assert.assertEquals(resApi, result)
        }
    }

    @Test
    fun insertNewsDaoSuccessTest() = runBlocking {
        //GIVEN
        val resApi = listOf<ArticleEntity>()
        coEvery { dao.insertArticles(resApi) } returns (Unit)
        //WHEN
        val res = homeRepository.insertArticlesDao(resApi)
        res.collect { result ->
            //THEN
            Assert.assertEquals(result, Unit)
        }
    }

    @Test
    fun deleteNewsDaoSuccessTest() = runBlocking {
        //GIVEN
        coEvery { dao.deleteArticles() } returns (Unit)
        //WHEN
        val res = homeRepository.deleteNewsDao()
        res.collect{ result ->
            //THEN
            Assert.assertEquals(result, Unit)
        }
    }
}