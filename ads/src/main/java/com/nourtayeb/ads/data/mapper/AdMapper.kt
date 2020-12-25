package com.nourtayeb.ads.data.mapper

import com.nourtayeb.ads.common.base.BaseMapper
import com.nourtayeb.ads.data.local.db.entities.AdLocal
import com.nourtayeb.ads.domain.entity.ImageAd

class AdMapper:BaseMapper<ImageAd,AdLocal> {
    override fun RoomToDomain(data: AdLocal): ImageAd {
        return ImageAd(data.id,data.name,data.url)
    }

    override fun DomainToRoom(domain: ImageAd): AdLocal {
        return AdLocal(domain.id,domain.name,domain.image)
    }
}