package com.example.myapplicationf.data.dao

import com.example.myapplicationf.data.model.EscuelaModel

object EscuelaDao {

    fun listar(dato: String): List<EscuelaModel>{
        val lista= mutableListOf<EscuelaModel>()

        val ps=JtdsConexion.getConexion().prepareStatement(
            "SELECT id, idpersona, municipio FROM tablaAndroid WHERE municipio LIKE '%' + ? + '%'"
        )

        ps.setString(1, dato)
        val rs=ps.executeQuery()

        while (rs.next()){
            lista.add(
                EscuelaModel().apply {
                    id = rs.getInt("id")
                    idpersona = rs.getInt("idpersona")
                    municipio = rs.getString("municipio")
                }
            )
        }

        rs.close()
        ps.close()

        return lista
    }

    private fun registrar(model: EscuelaModel){
        val ps=JtdsConexion.getConexion().prepareStatement(
            "INSERT INTO tablaAndroid (idpersona, municipio) VALUES(?, ?)"
        )

        ps.setInt(1, model.idpersona)
        ps.setString(2, model.municipio)

        val result = ps.executeUpdate()

        ps.close()

        if(result < 1)
            throw Exception("Error desconocido, no se pudo realizar la operacion")
    }

    private fun actualizar(model: EscuelaModel){
        val ps=JtdsConexion.getConexion().prepareStatement(
            "UPDATE tablaAndroid SET idpersona=?, municipio=? WHERE id=?"
        )

        ps.setInt(1, model.idpersona)
        ps.setString(2, model.municipio)
        ps.setInt(3, model.id)

        val result = ps.executeUpdate()

        ps.close()

        if(result < 1)
            throw Exception("Error desconocido, no se pudo realizar la operacion")
    }

    fun eliminar(model: EscuelaModel){
        val ps=JtdsConexion.getConexion().prepareStatement(
            "DELETE FROM tablaAndroid WHERE id=?"
        )

        ps.setInt(1, model.id)


        val result = ps.executeUpdate()

        ps.close()

        if(result < 1)
            throw Exception("Error desconocido, no se pudo realizar la operacion")
    }

    fun grabar(model: EscuelaModel){
        if(model.id ==0)
            registrar(model)
        else
            actualizar(model)

    }
}