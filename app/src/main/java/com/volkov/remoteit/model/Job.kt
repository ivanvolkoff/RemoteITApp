package com.volkov.remoteit.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Job(
    @SerializedName("candidate_required_location")
    val candidateRequiredLocation: String?,
    @SerializedName("category")
    val category: String?,
    @SerializedName("company_logo_url")
    val companyLogoUrl: String?,
    @SerializedName("company_name")
    val companyName: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("job_type")
    val jobType: String?,
    @SerializedName("publication_date")
    val publicationDate: String?,
    @SerializedName("salary")
    val salary: String?,
    @SerializedName("tags")
    val tags: List<String>?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("url")
    val url: String?
):Parcelable