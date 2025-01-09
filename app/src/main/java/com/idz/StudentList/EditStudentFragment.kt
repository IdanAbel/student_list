package com.idz.StudentList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.idz.StudentList.databinding.FragmentEditStudentBinding
import com.idz.StudentList.model.Model
import com.idz.StudentList.model.Student

class EditStudentFragment : Fragment() {

    private var binding: FragmentEditStudentBinding? = null
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

        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditStudentBinding.inflate(inflater, container, false)

        binding?.editStudentId?.setText(studentId)
        binding?.editStudentName?.setText(studentName)
        binding?.editStudentPhone?.setText(studentPhone)
        binding?.editStudentAddress?.setText(studentAddress)
        binding?.studentCheckBox?.isChecked = studentIsChecked ?: false
        binding?.cancelButtonEditStudent?.setOnClickListener(::onCancelClicked)
        binding?.saveButtonEditStudent?.setOnClickListener(::onSaveClicked)
        binding?.deleteButtonEditStudent?.setOnClickListener(::onDeleteClicked)
        return binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun onSaveClicked(view: View) {

        val student = Student(
            name = binding?.editStudentName?.text?.toString() ?: "",
            id = binding?.editStudentId?.text?.toString() ?: "",
            address = binding?.editStudentAddress?.text?.toString() ?: "",
            phone = binding?.editStudentPhone?.text?.toString() ?: "",
            avatarUrl = "",
            isChecked = binding?.studentCheckBox?.isChecked ?: false
        )

        binding?.progressBar?.visibility = View.VISIBLE

        Model.shared.add(student) {
            binding?.progressBar?.visibility = View.GONE
            val action =
                EditStudentFragmentDirections.actionStudentEditFragmentToStudentDetailsFragment(
                    student.name,
                    student.id,
                    student.phone,
                    student.address,
                    student.isChecked
                )
            binding?.root?.let {
                Navigation.findNavController(it).navigate(action)
            }
        }
    }

    private fun onDeleteClicked(view: View) {

        binding?.progressBar?.visibility = View.VISIBLE

        Model.shared.deleteStudentById(studentId ?: "") {
            binding?.progressBar?.visibility = View.GONE
            val action =
                EditStudentFragmentDirections.actionStudentDetailsFragmentToStudentListFragment()
            binding?.root?.let {
                Navigation.findNavController(it).navigate(action)
            }
        }
    }

    private fun onCancelClicked(view: View) {
        Navigation.findNavController(view).popBackStack()
    }
}