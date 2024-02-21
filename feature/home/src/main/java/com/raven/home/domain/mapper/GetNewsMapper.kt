package com.raven.home.domain.mapper

import com.raven.home.data.remote.models.ApiResponseModel
import com.raven.home.data.remote.models.MediaRemoteModel
import com.raven.home.domain.models.Article
import com.raven.home.domain.models.Media
import com.raven.home.domain.models.MediaMetaData
import kotlinx.coroutines.delay
import javax.inject.Inject

class GetNewsMapper @Inject constructor() {

    fun transformDomainToUI(params: ApiResponseModel) :  List<Article> {
        val map = mutableListOf<Article>()
        var mediaTempList = mutableListOf<Media>()
        var mediaMetaDataTempList = mutableListOf<MediaMetaData>()

        params.results.forEach {
            it.media.forEach { mediaRemote ->
                mediaRemote.mediaMetadata.forEach { mediaMetadataRemote ->
                    mediaMetaDataTempList.add(
                        MediaMetaData(
                            url = mediaMetadataRemote.url,
                            format = mediaMetadataRemote.format,
                            height = mediaMetadataRemote.height,
                            width = mediaMetadataRemote.width,
                        )
                    )
                }
                mediaTempList.add(
                    Media(
                        caption = mediaRemote.caption,
                        copyright = mediaRemote.copyright,
                        photos = mediaMetaDataTempList,
                    )
                )
            }
            map.add(
                Article(
                    title = it.title,
                    publishedDate = it.publishedDate,
                    abstract = it.abstract,
                    byLine = it.byline,
                    section = it.section,
                    url = it.url,
                    media = mediaTempList
                )
            )
            mediaTempList = mutableListOf()
            mediaMetaDataTempList = mutableListOf()
        }
        return map
    }
}
