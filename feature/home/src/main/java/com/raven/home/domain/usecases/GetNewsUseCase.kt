package com.raven.home.domain.usecases

import android.content.Context
import com.raven.core.bases.BaseUseCase
import com.raven.home.R
import com.raven.home.utils.Resource
import com.raven.home.domain.HomeDataSource
import com.raven.home.domain.mapper.GetNewsMapper
import com.raven.home.domain.models.Article
import com.raven.home.utils.isOnline
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(
    private val dataSource: HomeDataSource,
    private val mapper: GetNewsMapper,
    private val context: Context
) : BaseUseCase<Unit, Resource<List<Article>>>() {
    override fun execute(params: Unit): Flow<Resource<List<Article>>> = flow {
        dataSource.getNewsDao().collect { articleEntities ->
            if (articleEntities.isNotEmpty()) {
                emit(Resource.success(mapper.transformDaoToUi(articleEntities)))
                return@collect
            } else if (isOnline(context)) dataSource.getNews().collect {
                when {
                    isOnline(context) && it.data != null -> {
                            val mapResponse = mapper.transformDomainToUI(it.data)
                            val mapDao = mapper.transformDomainToDao(it.data)
                            dataSource.deleteNewsDao().collect {
                                dataSource.insertArticlesDao(mapDao).collect()
                            }
                            emit(Resource.success(mapResponse))
                        }
                    it.data == null -> {
                            emit(Resource.error(code = it.code, msg = it.message))
                        }
                    else -> {
                        emit(Resource.error(33,
                            context.getString(R.string.something_is_wrong_try_later))
                        )
                    }
                }
            } else emit(Resource.error(12,
                context.getString(R.string.without_internet))
            )
        }
    }
}

