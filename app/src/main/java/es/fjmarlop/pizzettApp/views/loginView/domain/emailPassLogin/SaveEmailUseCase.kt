package es.fjmarlop.pizzettApp.views.loginView.domain.emailPassLogin

import es.fjmarlop.pizzettApp.models.UserModel
import es.fjmarlop.pizzettApp.views.loginView.data.LoginRepository
import javax.inject.Inject

class SaveEmailUseCase @Inject constructor(private val loginRepository: LoginRepository) {

    suspend operator fun invoke(userModel: UserModel){
    loginRepository.addUser(userModel)
    }

}