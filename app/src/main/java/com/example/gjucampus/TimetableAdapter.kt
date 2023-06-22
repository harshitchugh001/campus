import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gjucampus.R
import com.example.gjucampus.TimetableItem

class TimetableAdapter(private var timetableList: List<TimetableItem>) :
    RecyclerView.Adapter<TimetableAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_lecture, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val timetableItem = timetableList[position]
        holder.bind(timetableItem)
    }

    override fun getItemCount(): Int {
        return timetableList.size
    }

    fun setData(newList: List<TimetableItem>) {
        timetableList = newList
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val timeTextView: TextView = itemView.findViewById(R.id.timeTextView)
        private val roomTextView: TextView = itemView.findViewById(R.id.roomTextView)
        private val subjectTextView: TextView = itemView.findViewById(R.id.subjectTextView)
        private val teacherTextView: TextView = itemView.findViewById(R.id.teacherTextView)
        private val typeTextView: TextView = itemView.findViewById(R.id.typeTextView)

        fun bind(timetableItem: TimetableItem) {
            timeTextView.text = timetableItem.time
            roomTextView.text = timetableItem.room
            subjectTextView.text = timetableItem.subject
            teacherTextView.text = timetableItem.teacher
            typeTextView.text = timetableItem.type
        }
    }
}
