package com.wsl.domain.model

import com.google.gson.annotations.SerializedName

data class City(
    val name: String
)

sealed class PlaceOfTheRoute {
    object Departure: PlaceOfTheRoute()

    object Arrival: PlaceOfTheRoute()

    data class InTheMiddle(val position: Int): PlaceOfTheRoute()
}


data class MainCityApiResponseObject (

    @SerializedName("totalResultsCount" ) var totalResultsCount : Int?                = null,
    @SerializedName("geonames"          ) var geonames          : ArrayList<CityJsonObject> = arrayListOf()

)
data class CityJsonObject (

    @SerializedName("adminCode1"  ) var adminCode1  : String?      = null,
    @SerializedName("lng"         ) var lng         : String?      = null,
    @SerializedName("geonameId"   ) var geonameId   : Int?         = null,
    @SerializedName("toponymName" ) var toponymName : String?      = null,
    @SerializedName("countryId"   ) var countryId   : String?      = null,
    @SerializedName("fcl"         ) var fcl         : String?      = null,
    @SerializedName("population"  ) var population  : Int?         = null,
    @SerializedName("countryCode" ) var countryCode : String?      = null,
    @SerializedName("name"        ) var name        : String?      = null,
    @SerializedName("fclName"     ) var fclName     : String?      = null,
    @SerializedName("countryName" ) var countryName : String?      = null,
    @SerializedName("fcodeName"   ) var fcodeName   : String?      = null,
    @SerializedName("adminName1"  ) var adminName1  : String?      = null,
    @SerializedName("lat"         ) var lat         : String?      = null,
    @SerializedName("fcode"       ) var fcode       : String?      = null

)