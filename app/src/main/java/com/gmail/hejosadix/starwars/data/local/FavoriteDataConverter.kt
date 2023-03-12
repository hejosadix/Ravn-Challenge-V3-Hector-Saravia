package com.gmail.hejosadix.starwars.data.local

import androidx.room.TypeConverter
import com.gmail.hejosadix.starwars.domain.models.Species
import com.gmail.hejosadix.starwars.domain.models.VehicleConnection
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class FavoriteDataConverter {
    companion object {
        @JvmStatic
        private val gson = Gson()

        @JvmStatic
        @TypeConverter
        fun stringToSpecies(data: String?): Species {
            if (data == null) {
                return Species()
            }

            val type = object : TypeToken<Species>() {

            }.type

            return gson.fromJson(data, type)
        }

        @JvmStatic
        @TypeConverter
        fun speciesToString(species: Species): String {
            return gson.toJson(species)
        }

        @JvmStatic
        @TypeConverter
        fun stringToVehicleConnection(data: String?): VehicleConnection {
            if (data == null) {
                return VehicleConnection()
            }

            val type = object : TypeToken<VehicleConnection>() {

            }.type

            return gson.fromJson(data, type)
        }

        @JvmStatic
        @TypeConverter
        fun vehicleConnectionToString(vehicleConnection: VehicleConnection): String {
            return gson.toJson(vehicleConnection)
        }
    }
}