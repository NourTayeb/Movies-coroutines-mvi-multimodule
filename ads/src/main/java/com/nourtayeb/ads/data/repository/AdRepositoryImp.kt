package com.nourtayeb.ads.data.repository

import android.util.Log
import com.nourtayeb.ads.data.local.db.AdDao
import com.nourtayeb.ads.data.mapper.AdMapper
import com.nourtayeb.ads.domain.entity.ImageAd
import com.nourtayeb.ads.domain.entity.StringAd
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.random.Random


class AdRepositoryImp @Inject constructor(val adDao: AdDao, val adMapper: AdMapper) : AdRepository {
    override fun getImageAds(): Flow<ImageAd> = flow {
        try {
            while (true) {
                var adLocal = adDao.getRandomAd(Random.nextInt(9) + 1)
                if (adLocal == null) {
                    adDao.addAll(dummyAds.map { adMapper.DomainToRoom(it) })
                    adLocal = adDao.getRandomAd(Random.nextInt(9) + 1)
                }
                adLocal?.let { emit(adMapper.RoomToDomain(it)) }
                delay(1000)
            }
        } finally {
            Log.d("Flow", "Flow was stopped")
        }
    }

    override fun getStringAds(): Flow<StringAd> = flow {
        emit(StringAd(1, "Get our latest offers"))
        delay(2000)
        emit(StringAd(2, "Invite your friends today"))
    }
}