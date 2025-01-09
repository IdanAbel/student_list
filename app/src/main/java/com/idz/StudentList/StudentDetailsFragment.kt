package com.idz.StudentList;

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.idz.StudentList.databinding.FragmentStudentDetailsBinding

class StudentDetailsFragment : Fragment() {
    private var binding: FragmentStudentDetailsBinding? = null
    private var studentName: String? = null
    private var studentId: String? = null
    private var studentPhone: String? = null
    private var studentAddress: String? = null
    private var studentIsChecked: Boolean? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            val args = StudentDetailsFragmentArgs.fromBundle(it)
            studentName = args.studentName
            studentId = args.studentId
            studentPhone = args.studentPhone
            studentAddress = args.studentAddress
            studentIsChecked = args.studentIsChecked
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStudentDetailsBinding.inflate(inflater, container, false)

        binding?.studentId?.text = studentId
        binding?.studentName?.text = studentName
        binding?.studentPhone?.text = studentPhone
        binding?.studentAddress?.text = studentAddress
        binding?.studentCheckBox?.isChecked = studentIsChecked ?: false
        binding?.editButtonStudentDetails?.setOnClickListener(::onEditClick)

        return binding?.root
    }

    private fun onEditClick(view: View) {
        binding?.progressBar?.visibility = View.VISIBLE

        val action =
            StudentDetailsFragmentDirections.actionStudentDetailsFragmentToStudentEditFragment(
                studentName ?: "",
                studentId ?: "",
                studentPhone ?:"",
                studentAddress ?: "",
                studentIsChecked ?: false
            )
        binding?.root?.let {
            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
