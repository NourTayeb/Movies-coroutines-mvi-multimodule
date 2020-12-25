package com.nourtayeb.ads.domain.usecase

import com.nourtayeb.ads.comon.stringAds
import com.nourtayeb.ads.data.repository.AdRepository
import com.nourtayeb.ads.domain.entity.StringAd
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ShowStringAdsUseCaseTest {
    lateinit var showStringAdsUseCase: ShowStringAdsUseCase

    @RelaxedMockK
    lateinit var adRepository: AdRepository

    @Before
    fun init(){
        MockKAnnotations.init(this)
        showStringAdsUseCase = ShowStringAdsUseCase(adRepository)
    }
    @Test
    fun `buildusecase returns same as repo getStringAds`()= runBlockingTest{
        every { adRepository.getStringAds() } returns stringAds.asFlow()
        val buildCaseResult = showStringAdsUseCase.buildUseCase()
        val resultList= mutableListOf<StringAd>()
        buildCaseResult.collect {
            resultList.add(it)
        }
        Assert.assertEquals(resultList, stringAds)
    }
}