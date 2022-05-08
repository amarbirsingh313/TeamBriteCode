package com.app.teambrite.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CasesTimeSeriesDataModel (

    @SerializedName("dailyconfirmed" ) var dailyconfirmed : String? = null,
    @SerializedName("dailydeceased"  ) var dailydeceased  : String? = null,
    @SerializedName("dailyrecovered" ) var dailyrecovered : String? = null,
    @SerializedName("date"           ) var date           : String? = null,
    @SerializedName("dateymd"        ) var dateymd        : String? = null,
    @SerializedName("totalconfirmed" ) var totalconfirmed : String? = null,
    @SerializedName("totaldeceased"  ) var totaldeceased  : String? = null,
    @SerializedName("totalrecovered" ) var totalrecovered : String? = null,
)