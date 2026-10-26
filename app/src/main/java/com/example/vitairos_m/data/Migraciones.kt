
package com.example.vitairos_m.data

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

// Migración de versión 1 a versión 2
// Agrega la tabla de perfil
val MIGRATION_1_2 = object : Migration(1, 2) {

    override fun migrate(db: SupportSQLiteDatabase) {

        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS `perfil` (
                `id` INTEGER NOT NULL,
                `nombre` TEXT NOT NULL,
                `correo` TEXT NOT NULL,
                PRIMARY KEY(`id`)
            )
            """.trimIndent()
        )
    }
}

// Migración de versión 2 a versión 3
// Agrega categoría, energía y nota a las emociones
val MIGRATION_2_3 = object : Migration(2, 3) {

    override fun migrate(db: SupportSQLiteDatabase) {

        db.execSQL(
            "ALTER TABLE emociones ADD COLUMN categoria TEXT"
        )

        db.execSQL(
            "ALTER TABLE emociones ADD COLUMN energia INTEGER"
        )

        db.execSQL(
            "ALTER TABLE emociones ADD COLUMN nota TEXT"
        )
    }
}

// Migración de versión 3 a versión 4
// Agrega la tabla de solicitudes de atención profesional
val MIGRATION_3_4 = object : Migration(3, 4) {

    override fun migrate(db: SupportSQLiteDatabase) {

        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS `solicitudes_hora` (
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `profesional` TEXT NOT NULL,
                `fecha` TEXT NOT NULL,
                `hora` TEXT NOT NULL,
                `fechaSolicitud` INTEGER NOT NULL
            )
            """.trimIndent()
        )
    }
}
