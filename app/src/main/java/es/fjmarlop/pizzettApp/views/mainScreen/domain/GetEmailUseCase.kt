package es.fjmarlop.pizzettApp.views.mainScreen.domain

import es.fjmarlop.pizzettApp.views.mainScreen.data.MainRepositoty
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEmailUseCase @Inject constructor(private val mainRepositoty: MainRepositoty){
    operator fun invoke():Flow<String> = mainRepositoty.email

}


