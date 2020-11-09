package com.nourtayeb.movies_mvi.coroutines

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class FlowTests {
    @Test
    fun testFlowAndCollect(){
        val flow = flow{
            emit(2)
            emit(1)
        }
        runBlocking {
            val list= mutableListOf<Int>()
            flow.collect {
                list.add(it)
            }
            Assert.assertEquals(list, listOf(2,1))
        }
    }
}