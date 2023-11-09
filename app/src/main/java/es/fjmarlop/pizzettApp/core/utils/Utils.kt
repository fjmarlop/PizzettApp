package es.fjmarlop.pizzettApp.core.utils

import android.content.Context
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


class Utils @Inject constructor(
    private val context: Context
){

    /**
     * @author Fco Javier Marmolejo López
     * @return Token en formato String
     *
     * Este método consigue el tokenId del usuario de Firebase
     * para autentificar el token con la API.
     *
     * */
    suspend fun getToken(): String {
        return suspendCoroutine { continuation ->
            val user = FirebaseAuth.getInstance().currentUser
            user?.getIdToken(true)?.addOnSuccessListener { result ->
                val idToken = result.token
                continuation.resume(idToken.toString())
            }?.addOnFailureListener { exception ->
                continuation.resumeWithException(exception)
            }
        }
    }

    fun mensajeToastCorto(msg: String) {
        Toast.makeText(
            context,
            msg,
            Toast.LENGTH_SHORT
        ).show()
    }
    fun mensajeToastLargo(msg: String) {
        Toast.makeText(
            context,
            msg,
            Toast.LENGTH_LONG
        ).show()
    }

  fun mensajeToast(msg: String) {
        Toast.makeText(
            context,
            msg,
            Toast.LENGTH_LONG
        ).show()
    }

    fun isEmailValid(email: String): Boolean {
        val emailPattern = Patterns.EMAIL_ADDRESS.matcher(email)
        return emailPattern.matches()
    }

}