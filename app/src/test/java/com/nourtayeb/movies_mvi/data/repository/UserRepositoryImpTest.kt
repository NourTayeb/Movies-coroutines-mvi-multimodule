package com.nourtayeb.movies_mvi.data.repository

import com.nourtayeb.movies_mvi.common.dummyUser
import com.nourtayeb.movies_mvi.data.local.db.UserDao
import com.nourtayeb.movies_mvi.data.mapper.UserMapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class UserRepositoryImpTest {

    @RelaxedMockK
    lateinit var userDao: UserDao
    var userMapper = UserMapper()

    lateinit var userRepositoryImp: UserRepositoryImp

    @Before
    fun init() {
        MockKAnnotations.init(this)
        userRepositoryImp = UserRepositoryImp(userDao, userMapper)
    }

    @Test
    fun `getLoggedInUser calls userDao getLoggedInUser`() {
        coEvery { userDao.getLoggedInUser() } returns userMapper.DomainToRoom(dummyUser)
        runBlockingTest { userRepositoryImp.getLoggedInUser() }
        coVerify { userDao.getLoggedInUser() }
    }

    @Test
    fun `a mapped userlocal userDao getLoggedInUser is returned from repo getLoggedInUser`() {
        coEvery { userDao.getLoggedInUser() } returns userMapper.DomainToRoom(dummyUser)
        runBlockingTest {
            val user = userRepositoryImp.getLoggedInUser()
            Assert.assertEquals(dummyUser, user)
        }
    }

    @Test
    fun `addLoggedInUser calls userDao logOutAll`() {
        coEvery { userDao.addUser(userMapper.DomainToRoom(dummyUser)) } returns 1
        runBlockingTest {
            userRepositoryImp.addLoggedInUser(dummyUser)
        }
        coVerify { userDao.logOutAll() }
    }
    @Test
    fun `addLoggedInUser returns true for when dao addUser returns bigger than 0`() {
        coEvery { userDao.addUser(userMapper.DomainToRoom(dummyUser)) } returns 1
        runBlockingTest {
            val bool =userRepositoryImp.addLoggedInUser(dummyUser)
            Assert.assertEquals(true,bool)
        }
    }
    @Test
    fun `addLoggedInUser returns false for when dao addUser returns 0`() {
        coEvery { userDao.addUser(userMapper.DomainToRoom(dummyUser)) } returns 0
        runBlockingTest {
            val bool =userRepositoryImp.addLoggedInUser(dummyUser)
            Assert.assertEquals(false,bool)
        }
    }
}