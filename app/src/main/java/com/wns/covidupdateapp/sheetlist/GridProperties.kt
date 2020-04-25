package com.wns.covidupdateapp.sheetlist

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GridProperties {
    @SerializedName("columnCount")
    @Expose
    var columnCount: Int? = null
    @SerializedName("rowCount")
    @Expose
    var rowCount: Int? = null

}