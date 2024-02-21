package com.raven.home.domain.mapper

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.raven.home.data.database.entities.ArticleEntity
import com.raven.home.data.remote.models.ApiResponseModel
import java.lang.reflect.Type
import com.raven.home.domain.models.Article
import com.raven.home.domain.models.Media
import com.raven.home.domain.models.MediaMetaData
import javax.inject.Inject

class GetNewsMapper @Inject constructor() {
    fun transformDomainToUI(params: ApiResponseModel) :  List<Article> {
        val map = mutableListOf<Article>()
        var mediaTempList = mutableListOf<Media>()
        var mediaMetaDataTempList = mutableListOf<MediaMetaData>()

        params.results.forEach { articleRemote ->
            articleRemote.media.forEach { mediaRemote ->
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
                    title = articleRemote.title,
                    publishedDate = articleRemote.publishedDate,
                    abstract = articleRemote.abstract,
                    byLine = articleRemote.byline,
                    section = articleRemote.section,
                    url = articleRemote.url,
                    media = mediaTempList
                )
            )
            mediaTempList = mutableListOf()
            mediaMetaDataTempList = mutableListOf()
        }
        return map
    }

    fun transformDomainToDao(params: ApiResponseModel) :  List<ArticleEntity> {
        var n = 0
        val map = mutableListOf<ArticleEntity>()
        var mediaTempList = mutableListOf<Media>()
        var mediaMetaDataTempList = mutableListOf<MediaMetaData>()

        params.results.forEach { articleRemote ->
            articleRemote.media.forEach { mediaRemote ->
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
            val mediaGson = Gson().toJson(mediaTempList)
            map.add(
                ArticleEntity(
                    identity = n,
                    title = articleRemote.title,
                    publishedDate = articleRemote.publishedDate,
                    abstract = articleRemote.abstract,
                    byline = articleRemote.byline,
                    section = articleRemote.section,
                    url = articleRemote.url,
                    media = mediaGson
                )
            )
            n += 1
            mediaTempList = mutableListOf()
            mediaMetaDataTempList = mutableListOf()
        }
        return map
    }

    fun transformDaoToUi(params: List<ArticleEntity>) :  List<Article> {
        val map = mutableListOf<Article>()
        var mediaTempList = mutableListOf<Media>()
        var mediaMetaDataTempList = mutableListOf<MediaMetaData>()

        params.forEach { articleRemote ->
            val mediaListType: Type = object : TypeToken<List<Media>>() {}.type
            val mediaList: List<Media> = Gson().fromJson(articleRemote.media, mediaListType)
            mediaList.forEach { mediaRemote ->
                mediaRemote.photos.forEach { mediaMetadataRemote ->
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
                    title = articleRemote.title,
                    publishedDate = articleRemote.publishedDate,
                    abstract = articleRemote.abstract,
                    byLine = articleRemote.byline,
                    section = articleRemote.section,
                    url = articleRemote.url,
                    media = mediaTempList
                )
            )
            mediaTempList = mutableListOf()
            mediaMetaDataTempList = mutableListOf()
        }
        return map
    }
}
