package br.com.fiap.aeroparts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import br.com.fiap.aeroparts.databinding.FragmentUpdateBinding
import com.google.firebase.database.*

class UpdateFragments : Fragment() {

    private lateinit var binding: FragmentUpdateBinding
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpdateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("users")

        binding.updateButton.setOnClickListener {
            val userId = binding.userIdEditText.text.toString()
            val newUsername = binding.usernameEditText.text.toString()
            val newPassword = binding.passwordEditText.text.toString()

            if (userId.isNotEmpty() && newUsername.isNotEmpty() && newPassword.isNotEmpty()) {
                if (isEmailValid(newUsername) && isPasswordValid(newPassword)) {
                    updateUser(userId, newUsername, newPassword)
                } else {
                    Toast.makeText(requireContext(), "E-mail ou senha inválida", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Por favor preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isEmailValid(email: String): Boolean {
        val emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
        return email.matches(emailRegex.toRegex())
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length >= 8
    }

    private fun updateUser(userId: String, newUsername: String, newPassword: String) {
        val userData = UserData(userId, newUsername, newPassword)
        databaseReference.child(userId).setValue(userData)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Perfil atualizado com sucesso", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(requireContext(), "Falha ao atualizar os dados do usuário: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
