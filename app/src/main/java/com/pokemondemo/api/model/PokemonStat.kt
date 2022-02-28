package com.pokemondemo.api.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PokemonStat(
    @Expose
    @SerializedName("base_stat")
    val base_stat: Int?,
    @Expose
    @SerializedName("effort")
    val effort: Int?,
    @Expose
    @SerializedName("stat")
    val stat: Stat?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readParcelable(Stat::class.java.classLoader)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        base_stat?.let { parcel.writeInt(it) }
        effort?.let { parcel.writeInt(it) }
        parcel.writeParcelable(stat, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PokemonStat> {
        override fun createFromParcel(parcel: Parcel): PokemonStat {
            return PokemonStat(parcel)
        }

        override fun newArray(size: Int): Array<PokemonStat?> {
            return arrayOfNulls(size)
        }
    }
}

data class Stat(
    @Expose
    @SerializedName("name")
    val name: String?,
    @Expose
    @SerializedName("url")
    val url: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Stat> {
        override fun createFromParcel(parcel: Parcel): Stat {
            return Stat(parcel)
        }

        override fun newArray(size: Int): Array<Stat?> {
            return arrayOfNulls(size)
        }
    }
}
