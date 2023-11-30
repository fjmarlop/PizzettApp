package es.fjmarlop.pizzettApp.dataBase.Remote.responses

import es.fjmarlop.pizzettApp.dataBase.Remote.models.EmpleadoModel

/**
 * Clase de modelo de respuesta que representa un empleado recibido desde un servicio web.
 *
 * @property empleadoEmail Dirección de correo electrónico del empleado.
 * @property empleadoName Nombre del empleado.
 * @property id Identificador único del empleado.
 */
data class EmpleadoResponse(
    val empleadoEmail: String,
    val empleadoName: String,
    val id: Int
) {

    /**
     * Convierte la respuesta de empleado a un modelo de empleado [EmpleadoModel].
     *
     * @return Objeto [EmpleadoModel] creado a partir de la respuesta de empleado.
     */
    fun toModel(): EmpleadoModel {
        return EmpleadoModel(id = this.id, empleadoEmail = this.empleadoEmail, empleadoName = this.empleadoName)
    }
}


