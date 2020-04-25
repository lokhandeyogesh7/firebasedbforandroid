package com.wns.covidupdateapp.sheetlist

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Properties {
    @SerializedName("gridProperties")
    @Expose
    var gridProperties: GridProperties? = null
    @SerializedName("index")
    @Expose
    var index: Int? = null
    @SerializedName("sheetId")
    @Expose
    var sheetId: Int? = null
    @SerializedName("sheetType")
    @Expose
    var sheetType: String? = null
    @SerializedName("title")
    @Expose
    var title: String? = null

    override fun toString(): String {
        return title!!
    }

}