package com.nourtayeb.ads.data.repository

import com.nourtayeb.ads.data.local.db.AdDao
import com.nourtayeb.ads.data.mapper.AdMapper
import io.mockk.MockK
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import javax.inject.Inject

class AdRepositoryImpTest {

    @RelaxedMockK
    lateinit var adDao: AdDao

    val adMapper = AdMapper()

    lateinit var adRepositoryImp: AdRepositoryImp

    @Before
    fun init() {
        MockKAnnotations.init(this)
        adRepositoryImp = AdRepositoryImp(adDao, adMapper)
    }

//    @Test
//    fun `ds`() {
//        coEvery { adDao.getRandomAd(any()) } returns adMapper.DomainToRoom(dummyAd)
//        val adFlow = adRepositoryImp.getAds()
//
//        val job = GlobalScope.launch {
//            runBlockingTest {
//                val v = adFlow.collect {
//                    Assert.assertEquals(it, dummyAd)
//                    println(it)
//                    cancel()
//                }
//            }
//        }
//
//    }
}