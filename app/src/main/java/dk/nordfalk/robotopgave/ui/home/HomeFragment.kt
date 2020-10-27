package dk.nordfalk.robotopgave.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dk.nordfalk.robotopgave.R
import dk.nordfalk.robotopgave.model.Model
import kotlinx.android.synthetic.main.home_frag.view.*
import kotlinx.android.synthetic.main.home_starttilstand_item.view.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.home_frag, container, false)


        root.recyclerViewSituation.layoutManager = LinearLayoutManager(activity)
        root.recyclerViewSituation.adapter = starttilstandadapter
        ItemTouchHelper(starttilstandSimpleItemTouchCallback).attachToRecyclerView(root.recyclerViewSituation)

        root.recyclerViewProgram.layoutManager = LinearLayoutManager(activity)
        root.recyclerViewProgram.adapter = programadapter
        Model.get().programmer_livedata.observe(viewLifecycleOwner, { programadapter.notifyDataSetChanged() })

        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            root.buttonKør.text = it
        })

        root.buttonKør.setOnClickListener {
            findNavController().navigate(R.id.navigation_notifications)
        }

        root.fabTilfStartsituation.setOnClickListener {
            findNavController().navigate(R.id.navigation_dashboard)
        }

        root.fabTilfProgram.setOnClickListener {
            // DialogFragment har mulighed for at vises som en dialog
            NytProgramDialogFragment().show(parentFragmentManager, "dialog")
        }

        return root
    }

    var programadapter: RecyclerView.Adapter<*> = object :  RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        var elem = Model.get().programmer
        override fun getItemCount(): Int {
            return elem.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            println("onCreateViewHolder ")
            val itemView: View = layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false)
            return object : RecyclerView.ViewHolder(itemView) {
                init {
                    itemView.setOnClickListener {
                        println("HURRA!!")
                        homeViewModel.valgtProgram = adapterPosition;
                        notifyDataSetChanged()
                    }
                }
            }
        }

        override fun onBindViewHolder(vh: RecyclerView.ViewHolder, position: Int) {
            println("onBindViewHolder $position")
            val overskrift = vh.itemView.findViewById<TextView>(android.R.id.text1)
            overskrift.setText(elem.get(position))
            vh.itemView.background =
                if (homeViewModel.valgtProgram == vh.adapterPosition) resources.getDrawable(R.color.teal_200) else null
        }
    }




    var starttilstande = Model.get().starttilstande
    var starttilstandadapter: RecyclerView.Adapter<*> = object : RecyclerView.Adapter<StarttilstandViewholder>() {

        override fun getItemCount(): Int {
            return starttilstande.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StarttilstandViewholder {
            val view: View = layoutInflater.inflate(R.layout.home_starttilstand_item, parent, false)
            val vh = StarttilstandViewholder(view)
            println("HURRA!22!")
            view.setOnClickListener {
                println("HURRA!!")
                homeViewModel.valgtRum = vh.adapterPosition;
                notifyDataSetChanged()
            }
            return vh
        }

        override fun onBindViewHolder(vh: StarttilstandViewholder, position: Int) {
            vh.overskrift.setText(starttilstande.get(position).position.toString())
            vh.beskrivelse.text = starttilstande.get(position).rum.toString()
            if (starttilstande.get(position).toString().hashCode() % 3 == 2) {
                vh.billede.setImageResource(android.R.drawable.sym_action_call)
            } else {
                vh.billede.setImageResource(android.R.drawable.sym_action_email)
            }
            //vh.itemView.isSelected = (homeViewModel.valgtRum == vh.adapterPosition);
            vh.itemView.background =
                if (homeViewModel.valgtRum == vh.adapterPosition) resources.getDrawable(R.color.teal_200) else null

        }
    }


    internal class StarttilstandViewholder(view: View) : RecyclerView.ViewHolder(view) {
        var overskrift: TextView
        var beskrivelse: TextView
        var billede: ImageView

        init {
            overskrift = view.textView1
            beskrivelse = view.textView2
            billede = view.billede
        }
    }


    // Læs mere på https://medium.com/@ipaulpro/drag-and-swipe-with-recyclerview-b9456d2b1aaf#.fjo359jbr
    var starttilstandSimpleItemTouchCallback: ItemTouchHelper.SimpleCallback =
        object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,  // dragDirs
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            // swipeDirs
            override fun onSelectedChanged(vh: RecyclerView.ViewHolder?, actionState: Int) {
                super.onSelectedChanged(vh, actionState)
                Log.d("Lande", "onSelectedChanged $vh $actionState")
                if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
                    vh!!.itemView.animate().scaleX(0.8f).scaleY(0.8f).rotation(-10f).alpha(0.6f)
                }
            }

            override fun onMove(
                rv: RecyclerView,
                vh: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val position = vh.adapterPosition
                val tilPos = target.adapterPosition
                val land = starttilstande.removeAt(position)
                starttilstande.add(tilPos, land)
                Log.d("Lande", "Flyttet: $starttilstande")
                starttilstandadapter.notifyItemMoved(position, tilPos)
                return true // false hvis rykket ikke skal foretages
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                val position = viewHolder.adapterPosition
                starttilstande.removeAt(position)
                Log.d("Lande", "Slettet: $starttilstande")
                starttilstandadapter.notifyItemRemoved(position)
            }

            override fun clearView(recyclerView: RecyclerView, vh: RecyclerView.ViewHolder) {
                super.clearView(recyclerView, vh)
                Log.d("Lande", "clearView $vh")
                vh.itemView.animate().scaleX(1f).scaleY(1f).rotation(0f).alpha(1f).interpolator =
                    OvershootInterpolator()
            }
        }

}