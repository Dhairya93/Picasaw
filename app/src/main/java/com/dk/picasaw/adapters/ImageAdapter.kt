package com.dk.picasaw.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.dk.picasaw.MainActivity
import com.dk.picasaw.R
import com.dk.picasaw.SingleImageView
import com.dk.picasaw.models.BlurHashDecoder
import com.dk.picasaw.models.RandomImg
import com.squareup.picasso.Picasso

class ImageAdapter: RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    var imgList=mutableListOf<RandomImg>()

    fun setImages(imgList:List<RandomImg>) {
        this.imgList=imgList.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.image_recycler,parent,false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bindData(imgList[position],imgList)
    }

    override fun getItemCount():Int =imgList.size

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgview=itemView.findViewById<ImageView>(R.id.imglist)
        lateinit var imageData:RandomImg
        fun bindData(imageData:RandomImg, bannerImgList: MutableList<RandomImg>){
            this.imageData=imageData
            Picasso.get().load(imageData.urls.small).into(imgview)
            if(bannerImgList[0].id==imageData.id) {
                val bg=BlurHashDecoder.blurHashBitmap(itemView.resources, bannerImgList[0])
                MainActivity.viewpagerBG.background = bg
                MainActivity.mainBg.background = bg
            }
        }

        init {
            imgview.setOnClickListener(View.OnClickListener {
                //Toast.makeText(itemView.context,imageData.id,Toast.LENGTH_LONG).show()
                val img=imageData.urls.regular
                val intent=Intent(itemView.context, SingleImageView::class.java)
                intent.putExtra("Imgurl",img)
                itemView.context.startActivity(intent)
            })
        }
    }
}