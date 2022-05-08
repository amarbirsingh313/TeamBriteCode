package com.app.teambrite.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Covid19DataModel (

    @SerializedName("cases_time_series" ) var casesTimeSeryDataModels : ArrayList<CasesTimeSeriesDataModel> = arrayListOf(),
    @SerializedName("statewise"         ) var statewiseDataModel       : ArrayList<StatewiseDataModel>       = arrayListOf(),
    @SerializedName("tested"            ) var testedDataModel          : ArrayList<TestedDataModel>          = arrayListOf()
)