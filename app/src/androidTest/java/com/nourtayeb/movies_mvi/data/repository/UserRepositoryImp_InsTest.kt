package com.nourtayeb.movies_mvi.data.repository

import com.nourtayeb.movies_mvi.common.dumUser
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class UserRepositoryImp_InsTest {
   @Inject lateinit var userRepositoryImp: UserRepositoryImp



    @get:Rule
    var hiltRule = HiltAndroidRule(this)


    @Before
    fun init(){
        hiltRule.inject()
    }
    @Test
    fun `addLoggedInUser_adds_user_to_db_successfully_when_retrieved_by_getLoggedInUser`(){
        runBlocking {
            userRepositoryImp.addLoggedInUser(dumUser)
            val loggedInUser = userRepositoryImp.getLoggedInUser()
            Assert.assertEquals(loggedInUser, dumUser)
        }

    }
}