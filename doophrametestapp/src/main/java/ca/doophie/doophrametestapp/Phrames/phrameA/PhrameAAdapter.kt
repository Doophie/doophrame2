package ca.doophie.doophrametestapp.phrames.phrameA

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ca.doophie.doophrametestapp.R
import ca.doophie.doophrametestapp.models.PhrameObject
import kotlinx.android.synthetic.main.list_item_a.view.*

class PhrameAAdapter(
        val data: List<PhrameObject>,
        val itemSelected: (TextView, ImageView, PhrameObject)->Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        // so that the itemView wont be cut off when transitioning back into focus
        parent.clipChildren = false

        return PhrameAView(LayoutInflater.from(parent.context).inflate(R.layout.list_item_a, parent, false), itemSelected)
    }

    override fun getItemCount(): Int {
        return data.count()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val phrame = holder as PhrameAView

        phrame.phrameObject = data[position]
        phrame.bindToPosition(position)
    }
}

class PhrameAView(item: View,
                  val itemSelected: (TextView, ImageView, PhrameObject)->Unit
) : RecyclerView.ViewHolder(item) {

    lateinit var phrameObject: PhrameObject

    fun bindToPosition(position: Int) {
        itemView.listItemText.text = "This is item number $position"
        itemView.listItemImage.setColorFilter(phrameObject.color)

        // for smooth recycler view transitions set a unique name for each
        // item in the view
        itemView.listItemText.transitionName = "t$position"
        itemView.listItemImage.transitionName = "i$position"

        this.itemView.setOnClickListener {
           itemSelected(this.itemView.listItemText, this.itemView.listItemImage, phrameObject)
        }
    }
}