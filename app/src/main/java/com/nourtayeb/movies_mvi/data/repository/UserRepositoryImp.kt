package com.nourtayeb.movies_mvi.data.repository

import com.nourtayeb.movies_mvi.data.local.db.UserDao
import com.nourtayeb.movies_mvi.data.mapper.UserMapper
import com.nourtayeb.movies_mvi.domain.entity.User
import com.nourtayeb.movies_mvi.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImp
    @Inject constructor(val userDao: UserDao,val userMapper: UserMapper):UserRepository {
    override suspend fun getLoggedInUser(): User {
        return userMapper.RoomToDomain(userDao.getLoggedInUser())
    }
    override suspend fun addLoggedInUser(user: User) :Boolean{
        val user = userMapper.DomainToRoom(user)
        user.loggedIn = true
        userDao.logOutAll()
        val v =  userDao.addUser(user)
        return v > 0
    }


}