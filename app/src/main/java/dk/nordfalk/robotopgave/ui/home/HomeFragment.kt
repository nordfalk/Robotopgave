package dk.nordfalk.robotopgave.ui.home

import android.graphics.Color
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
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.recycler_view_item.view.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val textView: TextView = root.findViewById(R.id.buttonKør)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        root.buttonKør.setOnClickListener {
            findNavController().navigate(R.id.navigation_notifications)
        }


        var recyclerView = root.recyclerViewSituation
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = rumadapter
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)


        return root
    }



    // Vi laver en arrayliste så vi kan fjerne/indsætte elementer
    var rum = Model.get().starttilstande


    var rumadapter: RecyclerView.Adapter<*> = object : RecyclerView.Adapter<RumViewholder>() {

        override fun getItemCount(): Int {
            return rum.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RumViewholder {
            val view: View = layoutInflater.inflate(R.layout.recycler_view_item, parent, false)
            val vh = RumViewholder(view)
            println("HURRA!22!")
            view.setOnClickListener {
                println("HURRA!!")
                homeViewModel.valgtRum = vh.adapterPosition;
                notifyDataSetChanged()
            }
            return vh
        }

        override fun onBindViewHolder(vh: RumViewholder, position: Int) {
            vh.overskrift.setText(rum.get(position).report)
            vh.beskrivelse!!.text = rum.get(position).toString()
            if (position % 3 == 2) {
                vh.billede!!.setImageResource(android.R.drawable.sym_action_call)
            } else {
                vh.billede!!.setImageResource(android.R.drawable.sym_action_email)
            }
            //vh.itemView.isSelected = (homeViewModel.valgtRum == vh.adapterPosition);
            vh.itemView.background = if (homeViewModel.valgtRum == vh.adapterPosition) resources.getDrawable(R.color.teal_200) else null

        }
    }


    internal class RumViewholder(view: View) : RecyclerView.ViewHolder(view) {
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
    var simpleItemTouchCallback: ItemTouchHelper.SimpleCallback =
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
                val land = rum.removeAt(position)
                rum.add(tilPos, land)
                Log.d("Lande", "Flyttet: $rum")
                rumadapter.notifyItemMoved(position, tilPos)
                return true // false hvis rykket ikke skal foretages
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                val position = viewHolder.adapterPosition
                rum.removeAt(position)
                Log.d("Lande", "Slettet: $rum")
                rumadapter.notifyItemRemoved(position)
            }

            override fun clearView(recyclerView: RecyclerView, vh: RecyclerView.ViewHolder) {
                super.clearView(recyclerView, vh)
                Log.d("Lande", "clearView $vh")
                vh.itemView.animate().scaleX(1f).scaleY(1f).rotation(0f).alpha(1f).interpolator =
                    OvershootInterpolator()
            }
        }

}