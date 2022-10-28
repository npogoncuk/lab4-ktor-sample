package com.example.dao

import com.example.dao.DatabaseFactory.dbQuery
import com.example.models.WeatherDatabase
import org.jetbrains.exposed.sql.*

interface DAOFacade {
    suspend fun allWeatherResponses(): List<String>
    suspend fun weatherResponse(id: Int): String?
    suspend fun addNewWeatherResponse(string: String): String
    suspend fun deleteWeatherResponse(id: Int): Boolean
}
class DAOFacadeImpl : DAOFacade {

    private fun resultRowToString(row: ResultRow) = row.toString()

    override suspend fun allWeatherResponses(): List<String> = dbQuery {
        WeatherDatabase.selectAll().map(::resultRowToString)
    }

    override suspend fun weatherResponse(id: Int): String? = dbQuery {
        WeatherDatabase
            .select { WeatherDatabase.id eq id }
            .map(::resultRowToString)
            .singleOrNull()
    }

    override suspend fun addNewWeatherResponse(string: String): String = dbQuery {
        val insertStatement = WeatherDatabase.insert {
            it[body] = string
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToString)!!
    }

    override suspend fun deleteWeatherResponse(id: Int): Boolean = dbQuery {
        WeatherDatabase.deleteWhere { WeatherDatabase.id eq id } > 0
    }
}