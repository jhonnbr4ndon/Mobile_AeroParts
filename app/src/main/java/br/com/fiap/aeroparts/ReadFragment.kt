package br.com.fiap.aeroparts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import br.com.fiap.aeroparts.databinding.FragmentReadBinding
import com.google.firebase.database.*

class ReadFragment : Fragment() {

    private lateinit var binding: FragmentReadBinding
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReadBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("users")

        binding.readButton.setOnClickListener {
            val userId = binding.userIdEditText.text.toString()

            if (userId.isNotEmpty()) {
                readUser(userId)
            } else {
                Toast.makeText(requireContext(), "Por favor insira o ID do usuário", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun readUser(userId: String) {
        databaseReference.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val userData = dataSnapshot.getValue(UserData::class.java)
                    userData?.let {

                        binding.idTextView.text = "ID: ${userData.id}"
                        binding.usernameTextView.text = "Username: ${userData.username}"
                        binding.passwordTextView.text = "Password: ${userData.password}"

                        binding.idTextView.visibility = View.VISIBLE
                        binding.usernameTextView.visibility = View.VISIBLE
                        binding.passwordTextView.visibility = View.VISIBLE
                    }
                } else {
                    Toast.makeText(requireContext(), "Usuário não encontrado", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(requireContext(), "Erro de banco de dados: ${databaseError.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

}
