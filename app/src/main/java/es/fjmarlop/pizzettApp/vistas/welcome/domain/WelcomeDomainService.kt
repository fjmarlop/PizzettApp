package es.fjmarlop.pizzettApp.vistas.welcome.domain

import es.fjmarlop.pizzettApp.vistas.welcome.data.WelcomeRepository
import javax.inject.Inject

class WelcomeDomainService @Inject constructor(
    private val repository: WelcomeRepository
) {
    suspend fun comprobarEmpleado(email: String): Int {
        return repository.comprobarEmpleado(email)
    }
}