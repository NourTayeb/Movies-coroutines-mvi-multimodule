package com.nourtayeb.ads.common.base

interface BaseMapper<Domain, ROOM> {

    fun RoomToDomain(data: ROOM):Domain

    fun DomainToRoom(domain: Domain): ROOM

}
