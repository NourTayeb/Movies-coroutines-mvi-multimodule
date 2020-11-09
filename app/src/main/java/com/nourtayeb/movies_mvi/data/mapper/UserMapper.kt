package com.nourtayeb.movies_mvi.data.mapper

import com.nourtayeb.movies_mvi.common.base.BaseMapper
import com.nourtayeb.movies_mvi.data.local.db.entities.UserLocal
import com.nourtayeb.movies_mvi.domain.entity.User

class UserMapper :BaseMapper<User,UserLocal,UserLocal>{
    override fun RoomToDomain(data: UserLocal) =
        User(data.id)

    override fun RetrofitToDomain(data: UserLocal) =
         User(data.id)

    override fun DomainToRoom(domain: User)=
        UserLocal(domain.id,true)

    override fun RetrofitToRoom(data: UserLocal) =
        data

    override fun DomainToRetrofit(domain: User)=
        UserLocal(domain.id,false)


}