package br.com.fiap.aeroparts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import br.com.fiap.aeroparts.databinding.FragmentDeleteBinding
import com.google.firebase.database.*

class DeleteFragment : Fragment() {

    private lateinit var binding: FragmentDeleteBinding
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDeleteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("users")

        binding.deleteButton.setOnClickListener {
            val userId = binding.userIdEditText.text.toString()

            if (userId.isNotEmpty()) {
                deleteUser(userId)
            } else {
                Toast.makeText(requireContext(), "Por favor insira o ID do usuário", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun deleteUser(userId: String) {
        databaseReference.child(userId).removeValue()
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Dados do usuário excluídos com sucesso", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(requireContext(), "Falha ao excluir dados do usuário: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
