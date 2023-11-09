package es.fjmarlop.pizzettApp.vistas.login.domain

import es.fjmarlop.pizzettApp.vistas.login.data.LoginRepository
import javax.inject.Inject

class ComprobarEmpleadoUseCase @Inject constructor(private val loginRepository: LoginRepository) {

    suspend operator fun invoke(email: String): Int {
            return loginRepository.comprobarEmpleado(email)
    }
}