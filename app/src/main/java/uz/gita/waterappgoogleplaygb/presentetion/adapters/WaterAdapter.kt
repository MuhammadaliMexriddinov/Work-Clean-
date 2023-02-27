package uz.gita.waterappgoogleplaygb.presentetion.adapters

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.gita.waterappgoogleplaygb.R
import uz.gita.waterappgoogleplaygb.data.local.entity.WaterEntity
import uz.gita.waterappgoogleplaygb.databinding.ListItemWaterBinding
import uz.gita.waterappgoogleplaygb.utils.extensions.inflate

class WaterAdapter : ListAdapter<WaterEntity, WaterAdapter.ViewHolder>(waterItemCallback) {

    private var itemMenuClickListener: ((WaterEntity, View) -> Unit)? = null

    fun setOnItemMenuListener(block: (WaterEntity,View) -> Unit) {
        itemMenuClickListener = block
    }

    inner class ViewHolder(private val binding: ListItemWaterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.imageMenu.setOnClickListener {
                itemMenuClickListener?.invoke(getItem(absoluteAdapterPosition),it)
            }
        }

        @SuppressLint("SetTextI18n")
        fun onBind() {
            val data = getItem(absoluteAdapterPosition)
            binding.tvWaterTrackerTime.text = data.time
            binding.tvWaterBottle.text = "${data.drink} ml"

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ListItemWaterBinding.bind(parent.inflate(R.layout.list_item_water))
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.onBind()
}

private val waterItemCallback = object : DiffUtil.ItemCallback<WaterEntity>() {

    override fun areItemsTheSame(oldItem: WaterEntity, newItem: WaterEntity) =
        oldItem.id == oldItem.id

    override fun areContentsTheSame(oldItem: WaterEntity, newItem: WaterEntity) =
        oldItem.drink == newItem.drink && oldItem.time == newItem.time && oldItem.date == newItem.date

}