package dk.nordfalk.robotopgave.ui.s1_home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import dk.nordfalk.robotopgave.R
import dk.nordfalk.robotopgave.model.Model
import kotlinx.android.synthetic.main.s1_home_nyt_program_dialog_frag.view.*

/**
 * Created by j on 30-09-14.
 */
class NytProgramDialogFragment : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.s1_home_nyt_program_dialog_frag, container, false)
        root.buttonF.setOnClickListener { root.editTextProgram.text.append("F"); root.buttonTilføj.isEnabled = true; }
        root.buttonR.setOnClickListener { root.editTextProgram.text.append("R"); root.buttonTilføj.isEnabled = true;  }
        root.buttonL.setOnClickListener { root.editTextProgram.text.append("L"); root.buttonTilføj.isEnabled = true;  }

        root.buttonTilføj.isEnabled = false;
        root.buttonTilføj.setOnClickListener {
            val program = root.editTextProgram.text.toString();
            Model.get().programmer.add(0, program)
            Model.get().programmerObserver.value = "Tilf prg $root.editTextProgram.text"
            Model.get().valgtProgram.value = program
            dismiss()
        }

        return root
    }
}