package es.fjmarlop.pizzettApp.core.utils

import android.content.Context
import android.widget.Toast
import androidx.navigation.NavHostController
import es.fjmarlop.pizzettApp.core.navegacion.Rutas
import javax.inject.Inject

class Utils @Inject constructor(
    private val context: Context
){

  fun mensajeToast(msg: String) {
        Toast.makeText(
            context,
            msg,
            Toast.LENGTH_SHORT
        ).show()
    }

  fun navigateTo(navController: NavHostController, ruta :Rutas){
      navController.navigate(ruta.ruta)
  }
}