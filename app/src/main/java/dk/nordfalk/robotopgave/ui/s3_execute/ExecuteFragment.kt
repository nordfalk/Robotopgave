package dk.nordfalk.robotopgave.ui.s3_execute

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dk.nordfalk.robotopgave.R
import dk.nordfalk.robotopgave.model.Rum
import kotlinx.android.synthetic.main.s3_execute_frag.view.*

class ExecuteFragment : Fragment() {

    private lateinit var vm: ExecuteRobotViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        vm = ViewModelProvider(this).get(ExecuteRobotViewModel::class.java)

        if (vm.rum == Rum.ILLEGAL) {
            return inflater.inflate(R.layout.s3_execute_vaelg_foerst_rum_og_program, container, false)
        }

        val root = inflater.inflate(R.layout.s3_execute_frag, container, false)
        vm.robottilstand.observe(viewLifecycleOwner, Observer {
            root.textViewTilstand.text = it
            root.textViewProgram.text = vm.program
            root.imageButton1Skridt.isEnabled = vm.program.length>0
            root.imageButtonKoer.isEnabled = vm.program.length>0
        })

        root.overskriftRum.text = vm.rum.toString()
        root.imageButton1Skridt.setOnClickListener {
            vm.kør1Skridt()
        }

        return root
    }
}