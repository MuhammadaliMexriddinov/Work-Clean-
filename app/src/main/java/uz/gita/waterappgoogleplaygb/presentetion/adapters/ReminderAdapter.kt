package uz.gita.waterappgoogleplaygb.presentetion.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.gita.waterappgoogleplaygb.R
import uz.gita.waterappgoogleplaygb.data.local.entity.ReminderEntity
import uz.gita.waterappgoogleplaygb.databinding.ListItemReminderBinding

class ReminderAdapter :
    ListAdapter<ReminderEntity, ReminderAdapter.ViewHolder>(itemReminderCallback) {

    private var itemDeleteListener: ((ReminderEntity) -> Unit)? = null

    fun setOnDeleteClickListener(block: (ReminderEntity) -> Unit) {
        itemDeleteListener = block
    }

    inner class ViewHolder(private val binding: ListItemReminderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.imageDelete.setOnClickListener {
                itemDeleteListener?.invoke(getItem(absoluteAdapterPosition))
            }
        }

        fun onBind() {
            binding.apply {
                tvReminder.text = getItem(absoluteAdapterPosition).time
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ListItemReminderBinding.bind(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_reminder, parent, false)
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.onBind()
}

private val itemReminderCallback = object : DiffUtil.ItemCallback<ReminderEntity>() {
    override fun areItemsTheSame(oldItem: ReminderEntity, newItem: ReminderEntity): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: ReminderEntity, newItem: ReminderEntity): Boolean =
        oldItem.id == newItem.id && oldItem.time == newItem.time

}