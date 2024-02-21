package com.raven.home.domain.usecases

import com.raven.core.bases.BaseUseCase
import com.raven.home.data.remote.models.Resource
import com.raven.home.domain.HomeDataSource
import com.raven.home.domain.mapper.GetNewsMapper
import com.raven.home.domain.models.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(
    private val dataSource: HomeDataSource,
    private val mapper: GetNewsMapper
) : BaseUseCase<Unit, Resource<List<Article>>>() {

    override fun execute(params: Unit): Flow<Resource<List<Article>>> = flow {
        dataSource.getNews()
            .collect {
                if (it.data != null) {
                    emit( Resource.success(mapper.transformDomainToUI(it.data)))

                }
                else emit( Resource.error("Something is wrong, try again later."))
            }
    }
}

