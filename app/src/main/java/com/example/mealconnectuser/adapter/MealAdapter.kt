package com.example.mealconnectuser.adapter


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.mealconnectuser.databinding.MealListAdapterBinding
import com.example.mealconnectuser.room.MealEntity
import com.example.mealconnectuser.utils.PartnerData
import com.example.mealconnectuser.viewModel.MainViewModel


class MealAdapter(private val mainViewModel: MainViewModel):RecyclerView.Adapter<MealAdapter.MealViewHolder>() {

    private var meallist:List<PartnerData> = emptyList()

    class MealViewHolder(private val binding:MealListAdapterBinding):RecyclerView.ViewHolder(binding.root) {

        fun bindMeal(partnerData: PartnerData){
            binding.userdata=partnerData
            binding.executePendingBindings()

        }
        fun addToCart(meal: MealEntity,mainViewModel: MainViewModel){
            binding.addtocartbtn.setOnClickListener {
                mainViewModel.insertMeal(meal)
                Toast.makeText(binding.root.context,"Added ${meal.meal.mealname} To Cart",Toast.LENGTH_SHORT).show()
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
        holder.addToCart(MealEntity(0,currentmeal),mainViewModel)
    }

    fun setMeal(partnerData:List<PartnerData>){
        meallist=partnerData
        notifyDataSetChanged()
        Log.d("mealist",meallist.toString())
    }

}