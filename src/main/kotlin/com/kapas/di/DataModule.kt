package com.kapas.di

import com.kapas.data.IKapasRepository
import com.kapas.data.KapasRepository
import com.kapas.data.database.DatabaseFactory
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.locations.*
import org.koin.dsl.module
import java.net.URI

@KtorExperimentalLocationsAPI
val databaseModule = module {
    single {
        DatabaseFactory(get())
    }

    factory {
        val config = HikariConfig()
        config.apply {
            driverClassName = System.getenv("JDBC_DRIVER")
            //jdbcUrl = System.getenv("DATABASE_URL")
            maximumPoolSize = 6
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"

            val uri = URI(System.getenv("DATABASE_URL"))
            val username = uri.userInfo.split(":").toTypedArray()[0]
            val password = uri.userInfo.split(":").toTypedArray()[1]
            jdbcUrl =
                "jdbc:postgresql://" + uri.host + ":" + uri.port + uri.path + "?sslmode=require" + "&user=$username&password=$password"
        }
        HikariDataSource(config)
    }
}

val repositoryModule = module {
    single<IKapasRepository> {
        KapasRepository(get())
    }
}