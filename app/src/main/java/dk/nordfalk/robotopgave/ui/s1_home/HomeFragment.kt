package dk.nordfalk.robotopgave.ui.s1_home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dk.nordfalk.robotopgave.R
import dk.nordfalk.robotopgave.model.Model
import kotlinx.android.synthetic.main.s1_home_frag.view.*
import kotlinx.android.synthetic.main.s1_home_starttilstand_item.view.*

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.s1_home_frag, container, false)


        root.recyclerViewRum.layoutManager = LinearLayoutManager(activity)
        root.recyclerViewRum.adapter = rumadapter
        ItemTouchHelper(starttilstandSimpleItemTouchCallback).attachToRecyclerView(root.recyclerViewRum)
        Model.get().valgtRum.observe(viewLifecycleOwner, { rumadapter.notifyDataSetChanged() })

        root.recyclerViewProgram.layoutManager = LinearLayoutManager(activity)
        root.recyclerViewProgram.adapter = programadapter
        fun buttonKørEnable() {
            root.buttonKør.isEnabled = Model.get().valgtProgram.value!!.length>0 && Model.get().valgtRum.value != null;
        }
        Model.get().programmerObserver.observe(viewLifecycleOwner, { programadapter.notifyDataSetChanged(); buttonKørEnable() })
        Model.get().valgtProgram.observe(viewLifecycleOwner, { programadapter.notifyDataSetChanged(); buttonKørEnable() })
        Model.get().valgtRum.observe(viewLifecycleOwner, { buttonKørEnable() })

        root.buttonKør.setOnClickListener {
            findNavController().navigate(R.id.navigation_s3_execute)
        }

        root.fabTilfRum.setOnClickListener {
            findNavController().navigate(R.id.navigation_s2_nyt_rum)
        }

        root.fabTilfProgram.setOnClickListener {
            // DialogFragment har mulighed for at vises som en dialog
            NytProgramDialogFragment().show(parentFragmentManager, "dialog")
        }

        //root.recyclerViewProgram.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY -> run{ println("XXXX11"); if (isVisible) { root.fabTilfProgram.shrink(); v.setOnScrollChangeListener(null)}} }
        //root.recyclerViewRum.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY -> run{ println("XXXX22");root.fabTilfRum.shrink(); v.setOnScrollChangeListener(null)} }

        Handler(Looper.getMainLooper()).postDelayed({
            root.fabTilfProgram.shrink();
            root.fabTilfRum.shrink();
        } , 5000)

        return root
    }



    var programadapter: RecyclerView.Adapter<*> = object :  RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        override fun getItemCount(): Int {
            return Model.get().programmer.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            println("onCreateViewHolder ")
            val itemView: View = layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false)
            return object : RecyclerView.ViewHolder(itemView) {
                init {
                    itemView.setOnClickListener {
                        println("HURRA!!")
                        Model.get().valgtProgram.value = Model.get().programmer[adapterPosition];
                    }
                }
            }
        }

        override fun onBindViewHolder(vh: RecyclerView.ViewHolder, position: Int) {
            println("onBindViewHolder $position")
            val overskrift = vh.itemView.findViewById<TextView>(android.R.id.text1)
            overskrift.setText(Model.get().programmer.get(position))
            vh.itemView.background = if (Model.get().valgtProgram.value.equals(Model.get().programmer[vh.adapterPosition]))
                    resources.getDrawable(R.color.teal_200) else null
        }
    }



    var rumadapter: RecyclerView.Adapter<*> = object : RecyclerView.Adapter<RumViewholder>() {

        override fun getItemCount(): Int {
            return Model.get().rum.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RumViewholder {
            val view: View = layoutInflater.inflate(R.layout.s1_home_starttilstand_item, parent, false)
            val vh = RumViewholder(view)
            view.setOnClickListener {
                println("vh.adapterPosition = ${vh.adapterPosition}")
                Model.get().valgtRum.value = Model.get().rum[vh.adapterPosition];
            }
            return vh
        }

        override fun onBindViewHolder(vh: RumViewholder, position: Int) {
            vh.overskrift.text = Model.get().rum.get(position).toString()
            vh.beskrivelse.text = Model.get().rum.get(position).startposition.toString()
            if (Model.get().rum.get(position).toString().hashCode() % 3 == 0) {
                vh.billede.setImageResource(android.R.drawable.sym_action_call)
            } else {
                vh.billede.setImageResource(android.R.drawable.sym_action_email)
            }
            //vh.itemView.isSelected = (homeViewModel.valgtRum == vh.adapterPosition);
            vh.itemView.background =
                if (Model.get().valgtRum.value == Model.get().rum[vh.adapterPosition]) resources.getDrawable(R.color.teal_200) else null

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


    // Se https://medium.com/@ipaulpro/drag-and-swipe-with-recyclerview-b9456d2b1aaf#.fjo359jbr
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
                val land = Model.get().rum.removeAt(position)
                Model.get().rum.add(tilPos, land)
                Log.d("Lande", "Flyttet: ${Model.get().rum}")
                rumadapter.notifyItemMoved(position, tilPos)
                return true // false hvis rykket ikke skal foretages
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                val position = viewHolder.adapterPosition
                Model.get().rum.removeAt(position)
                Log.d("Lande", "Slettet: ${Model.get().rum}")
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