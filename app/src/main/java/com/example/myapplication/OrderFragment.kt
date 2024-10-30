package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myapplication.databinding.FragmentOrderBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OrderFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OrderFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentOrderBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            var selectedJenis = "Pilih tipe tiket"

            // Navigasi ke JenisFragment untuk memilih jenis tiket
            etTiket.setOnClickListener {
                val action = OrderFragmentDirections.actionOrderFragmentToJenisFragment()
                findNavController().navigate(action)
            }

            // Mendapatkan data dari JenisFragment
            findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>("jenis")
                ?.observe(viewLifecycleOwner) { res ->
                    selectedJenis = res
                    etTiket.setText(res)
                }

            btnBuy.setOnClickListener {
                // Cek apakah jenis tiket sudah dipilih
                if (selectedJenis == "Pilih tipe tiket") {
                    Toast.makeText(requireContext(), "Pilih tipe tiket", Toast.LENGTH_SHORT).show()
                } else {
                    // Format waktu saat ini
                    val currentTime = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault()).format(Date())

                    // Tampilkan toast dengan pesan sukses
                    Toast.makeText(
                        requireContext(),
                        "Tiket dengan tipe $selectedJenis berhasil dipesan pada $currentTime",
                        Toast.LENGTH_LONG
                    ).show()
                }

                // Kembali ke fragment sebelumnya
                findNavController().navigateUp()
            }
        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BeliFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OrderFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}