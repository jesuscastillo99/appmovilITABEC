package com.example.myapplicationf.data.dao


import java.sql.Connection
import java.sql.DriverManager

object JtdsConexion {

    fun getConexion(): Connection {
        Class.forName("net.sourceforge.jtds.jdbc.Driver")

        //Retorna la cadena de conexion
        return DriverManager.getConnection(
            "jdbc:jtds:sqlserver://172.31.29.96:1433/itabec",
            "us_itabec",
            "12345"
        )
    }
}