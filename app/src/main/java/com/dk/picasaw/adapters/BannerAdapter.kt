package com.dk.picasaw.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.dk.picasaw.MainActivity
import com.dk.picasaw.R
import com.dk.picasaw.models.BlurHashDecoder
import com.dk.picasaw.models.RandomImg
import com.squareup.picasso.Picasso

class BannerAdapter(imgList:List<RandomImg>, bannerView:ViewPager2):RecyclerView.Adapter<BannerAdapter.BannerViewHolder>() {
    var bannerImgList = mutableListOf<RandomImg>()
    var bannerViewImg=bannerView
    init {
        bannerImgList=imgList.toMutableList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        return BannerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.bannerview, parent, false))
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        holder.bindImg(bannerImgList[position])
        if (position==bannerImgList.size-1){
            bannerViewImg.post(runnable)
        }
    }
    private val runnable= Runnable {
        bannerImgList.addAll(bannerImgList)
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return bannerImgList.size
    }

    class BannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView=itemView.findViewById<ImageView>(R.id.banner)
        lateinit var randomImg: RandomImg
        fun bindImg(randomImg: RandomImg) {
            this.randomImg=randomImg
            Picasso.get().load(randomImg.urls.small).into(imageView)
        }
        init {
            imageView.setOnClickListener(View.OnClickListener {
                //MainActivity.mainBg.background=BlurHashDecoder.blurHashBitmap(itemView.resources,randomImg)
                Toast.makeText(itemView.context,randomImg.id, Toast.LENGTH_LONG).show()
            })
        }
    }
}