package com.nourtayeb.ads.domain.usecase

import com.nourtayeb.ads.comon.fiveImageAds
import com.nourtayeb.ads.comon.stringAds
import com.nourtayeb.ads.data.repository.AdRepository
import com.nourtayeb.ads.domain.entity.ImageAd
import com.nourtayeb.ads.domain.entity.StringAd
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ShowImageAdsUseCaseTest {
    lateinit var showImageAdsUseCase: ShowImageAdsUseCase

    @RelaxedMockK
    lateinit var adRepository: AdRepository

    @Before
    fun init(){
        MockKAnnotations.init(this)
        showImageAdsUseCase = ShowImageAdsUseCase(adRepository)
    }
    @Test
    fun `buildusecase for 2 elements returns first 2 elements of repo getImageAds`()= runBlockingTest{
        every { adRepository.getImageAds() } returns fiveImageAds.asFlow()
        val buildCaseResult = showImageAdsUseCase.buildUseCase(2)
        val resultList= mutableListOf<ImageAd>()
        buildCaseResult.collect {
            resultList.add(it)
        }
        Assert.assertEquals(resultList, fiveImageAds.take(2))
    }
}