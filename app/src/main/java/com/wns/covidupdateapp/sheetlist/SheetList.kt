package com.wns.covidupdateapp.sheetlist

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SheetList {
    @SerializedName("properties")
    @Expose
    var properties: Properties? = null

}