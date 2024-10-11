package com.cevdetkilickeser.neyicez.di

import com.cevdetkilickeser.neyicez.domain.AuthService
import com.cevdetkilickeser.neyicez.domain.AuthServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthModule {

    @Binds
    abstract fun bindAuthService(authServiceImpl: AuthServiceImpl): AuthService

}

//auth.signInWithEmailAndPassword(email,password)
//.addOnCompleteListener {
//    if (it.isSuccessful){
//        if (auth.currentUser!!.isEmailVerified){
//            val intent = Intent(requireContext(), MainActivity::class.java)
//            startActivity(intent)
//            requireActivity().finish()
//        }else{
//            Navigation.findNavController(view).navigate(R.id.logInToUnconfirmed)
//        }
//    }
//}.addOnFailureListener{
//    Snackbar.make(view,getText(R.string.login_failed), Snackbar.LENGTH_SHORT).show()
//}