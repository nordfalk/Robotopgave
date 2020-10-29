package dk.nordfalk.robotopgave.ui.s2_rum

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dk.nordfalk.robotopgave.R
import dk.nordfalk.robotopgave.model.*
import kotlinx.android.synthetic.main.s2_nyt_rum_frag.*
import kotlinx.android.synthetic.main.s2_nyt_rum_frag.view.*


class DashboardFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.s2_nyt_rum_frag, container, false)

        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.retninger_array, android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        root.spinner.setAdapter(adapter)

        root.buttonOpret.setOnClickListener {

            try {
                val w = getIntOrError(1, root.editTextRumBredde);
                val h = getIntOrError(1, root.editTextRumHoejde);
                val x = getIntOrError(0, root.editTextRobotStartX);
                val y = getIntOrError(0, root.editTextRobotStartY);
                if (x==-1 || y==-1 || w==-1 || h==-1) return@setOnClickListener // hop ud
                val r = Retning.valueOf(spinner.selectedItem.toString())
                val nytRum = Rum(w,h, Position(x,y,r))
                Robot(nytRum) // Laver fejltjek og kaster Exception

                Model.get().apply {
                    rum.add(0, nytRum)
                    valgtRum.value = nytRum
                }
                findNavController().navigateUp()
            } catch (e : Exception) {
                e.printStackTrace()
                Snackbar.make(root.buttonOpret, "Fejl: "+e, Snackbar.LENGTH_LONG).show()
            }
        }

        return root
    }

    private fun getIntOrError(min: Int, editText: EditText?): Int {
        editText!! // intern fejl hvis null
        try {
            editText.setError(null)
            val v = Integer.parseInt(editText.text.toString());
            if (v >= min) return v;
        } catch (e : Exception) { e.printStackTrace() }
        editText.setError("Feltet skal være $min eller større")
        return -1;
    }
}