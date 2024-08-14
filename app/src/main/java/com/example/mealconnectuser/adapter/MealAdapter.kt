package com.example.mealconnectuser.adapter


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.mealconnectuser.databinding.MealListAdapterBinding
import com.example.mealconnectuser.utils.PartnerData
import com.example.mealconnectuser.viewmodel.MainViewModel


class MealAdapter(private val mainViewModel: MainViewModel):RecyclerView.Adapter<MealAdapter.MealViewHolder>() {

    private var meallist:List<PartnerData> = emptyList()


    class MealViewHolder(private val binding:MealListAdapterBinding):RecyclerView.ViewHolder(binding.root) {

        fun bindMeal(partnerData: PartnerData){
            binding.userdata=partnerData
            binding.executePendingBindings()

        }
        fun addToCart(partnerData: PartnerData, mainViewModel: MainViewModel) {
            binding.addtocartbtn.setOnClickListener {
                mainViewModel.addToCartOperation(partnerData,true)
                Toast.makeText(binding.root.context, "Added ${partnerData.mealname}", Toast.LENGTH_SHORT).show()
            }
        }
        

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        return MealViewHolder(MealListAdapterBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
       return meallist.size
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {

        val currentmeal = meallist[position]
        holder.bindMeal(currentmeal)
        holder.addToCart(currentmeal, mainViewModel)
    }

    fun setMeal(partnerData:List<PartnerData>){
        meallist=partnerData
        notifyDataSetChanged()
        Log.d("mealist",meallist.toString())
    }



}