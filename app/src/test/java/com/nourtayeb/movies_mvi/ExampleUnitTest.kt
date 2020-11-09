package com.nourtayeb.movies_mvi

import com.nourtayeb.movies_mvi.coroutines.test
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.runBlocking
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {


    @Test
    fun test_with_testObserver(){
        runBlocking {
            val subject = Channel<Int>()
            val observer = subject.consumeAsFlow().test(this)
            observer.assertNoValues()
            subject.send(1)
            observer.assertValues(1)
            observer.finish()
        }
    }
//    @Test
//    fun normal_flow_testing() {
//        runBlocking {
//            val subject = Channel<Int>()
//            val values = mutableListOf<Int>()
//            val job = launch {
//                subject.consumeAsFlow()
//                    .collect { values.add(it) }
//            }
//            assertEquals(emptyList<Int>(), values)
//            subject.send(1)
//            assertEquals(listOf(1), values)
//            job.cancel()
//
//
//            flowOf(1, 2, 3)
//                .test(this)
//                .assertValues(1, 2, 3)
//                .finish()
//
//
//        }
//
//    }

    @Test
    fun `test fetchData with an empty result`() {
//        every {  } returns listOf()
//
//        val captureData = slot<Int>()
//
//
//        verify(exactly = 1) { view.onResult(capture(captureData)) }
//        captureData.captured.let { res ->
//            assertNotNull(res)
//            assert(res.isEmpty())
//        }
    }
}