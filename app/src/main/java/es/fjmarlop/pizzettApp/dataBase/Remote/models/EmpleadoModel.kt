package es.fjmarlop.pizzettApp.dataBase.Remote.models

/**
 * Clase de modelo que representa a un empleado.
 *
 * @property empleadoEmail Dirección de correo electrónico del empleado.
 * @property empleadoName Nombre del empleado.
 * @property id Identificador único del empleado (puede ser nulo si el empleado es nuevo y aún no ha sido asignado un ID).
 */
data class EmpleadoModel(
    val empleadoEmail: String,
    val empleadoName: String,
    val id: Int?
) {



}