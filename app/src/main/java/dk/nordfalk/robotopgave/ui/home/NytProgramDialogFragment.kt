package dk.nordfalk.robotopgave.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import dk.nordfalk.robotopgave.R
import dk.nordfalk.robotopgave.model.Model
import kotlinx.android.synthetic.main.home_nyt_program_dialog_frag.view.*

/**
 * Created by j on 30-09-14.
 */
class NytProgramDialogFragment : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.home_nyt_program_dialog_frag, container, false)
        root.buttonF.setOnClickListener { root.editTextProgram.text.append("F"); root.buttonTilføj.isEnabled = true; }
        root.buttonR.setOnClickListener { root.editTextProgram.text.append("R"); root.buttonTilføj.isEnabled = true;  }
        root.buttonL.setOnClickListener { root.editTextProgram.text.append("L"); root.buttonTilføj.isEnabled = true;  }

        root.buttonTilføj.isEnabled = false;
        root.buttonTilføj.setOnClickListener {
            Model.get().programmer.add(root.editTextProgram.text.toString())
            Model.get().programmer_livedata.value = "Tilf prg $root.editTextProgram.text"
            dismiss()
        }

        return root
    }
}