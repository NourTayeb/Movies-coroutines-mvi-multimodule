package com.nourtayeb.movies_mvi.common.base

interface BaseMapper<Domain, ROOM, RETROFIT> {

    fun RoomToDomain(data: ROOM):Domain
    fun RetrofitToDomain(data: RETROFIT):Domain

    fun DomainToRoom(domain: Domain): ROOM
    fun DomainToRetrofit(domain: Domain): RETROFIT

    fun RetrofitToRoom(data:RETROFIT):ROOM
}
