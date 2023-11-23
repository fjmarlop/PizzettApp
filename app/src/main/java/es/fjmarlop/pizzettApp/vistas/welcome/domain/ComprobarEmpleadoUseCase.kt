package es.fjmarlop.pizzettApp.vistas.welcome.domain

import es.fjmarlop.pizzettApp.vistas.welcome.data.WelcomeRepository
import javax.inject.Inject

class ComprobarEmpleadoUseCase @Inject constructor(
    private val repository: WelcomeRepository
){
    suspend operator fun invoke(email: String): Int {
        return repository.comprobarEmpleado(email)
    }
}