package com.dk.picasaw

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.dk.picasaw.adapters.BannerAdapter
import com.dk.picasaw.adapters.ImageAdapter
import com.dk.picasaw.remote.ApiManager
import com.dk.picasaw.remote.Repository
import com.dk.picasaw.remote.UnsplashApi
import com.dk.picasaw.viewmodel.ImageViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.math.abs

class MainActivity : AppCompatActivity() {
    lateinit var progressBar:ProgressBar
    lateinit var recyclerView:RecyclerView
    lateinit var imgAdapter:ImageAdapter
    lateinit var viewModel:ImageViewModel
    lateinit var filter:ImageView
    var currOrient:String="portrait"
    lateinit var repository:Repository
    lateinit var unsplashApi:UnsplashApi
    lateinit var bannerAdapter:BannerAdapter
    lateinit var bannerView: ViewPager2
    lateinit var handler:Handler
    lateinit var refresh:FloatingActionButton
    lateinit var search:FloatingActionButton
    var isLoading = true
    //internal lateinit var mainBg:NestedScrollView
    companion object {
        lateinit var mainBg:NestedScrollView
        lateinit var viewpagerBG:ViewPager2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (supportActionBar != null) {
            supportActionBar?.hide();
        }
        val list=resources.getStringArray(R.array.list_values)
        findViewById<Spinner>(R.id.tags).adapter=ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,list)
        filter=findViewById(R.id.filter_view)
        progressBar=findViewById(R.id.progressBar)
        recyclerView=findViewById(R.id.recyclerView)
        bannerView=findViewById(R.id.bannerView)
        mainBg=findViewById(R.id.mainBG)
        viewpagerBG=bannerView
        refresh=findViewById(R.id.refresh)
        search=findViewById(R.id.search_view)


        setupRecyclerView()
        setupBanner()
        filter.setOnClickListener(View.OnClickListener {changeOrientation()})
        refresh.setOnClickListener(View.OnClickListener{refreshPage()})
        search.setOnClickListener(View.OnClickListener { searchImages() })

    }

    private fun searchImages() {

    }

    private fun refreshPage() {
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager
        imgAdapter=ImageAdapter()
        unsplashApi = ApiManager.getInstance().create(UnsplashApi::class.java)
        repository=Repository(unsplashApi)
        viewModel = ViewModelProvider(this)[ImageViewModel::class.java]
        viewModel.requestRandomImages(30,currOrient,repository)
        viewModel.randomImages.observe(this) {
            if(it.isEmpty()){
                Toast.makeText(this,repository.apiError+"",Toast.LENGTH_LONG).show()
            }
            else {
                imgAdapter.setImages(it)
                recyclerView.adapter = imgAdapter
            }
        }
    }

    private fun setupBanner() {
        handler= Handler(Looper.myLooper()!!)
        viewModel.requestBannerImages(repository)
        viewModel.bannerImages.observe(this) {
            if (it.isEmpty()) {
                Toast.makeText(this, repository.apiError + "", Toast.LENGTH_LONG).show()
            } else {
                bannerAdapter = BannerAdapter(it, bannerView)
                bannerView.adapter = bannerAdapter
                bannerView.offscreenPageLimit = 3
                bannerView.clipToPadding = false
                bannerView.clipChildren = false
                bannerView.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            }
            setupTransform()
            bannerView.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    handler.removeCallbacks(runnable)
                    handler.postDelayed(runnable, 4000)
                }
            })
        }
    }
    val runnable= Runnable { bannerView.currentItem=bannerView.currentItem+1 }

    private fun setupTransform() {
        val transformer=CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer { page, position ->
            val r = 1-abs(position)
            page.scaleY = 0.85f + r * 0.15f
        }
        bannerView.setPageTransformer(transformer)
    }


    private fun changeOrientation() {
        if(currOrient == "landscape")
        {
            filter.setImageResource(R.drawable.portrait)
            currOrient="portrait"
            viewModel.requestRandomImages(30,currOrient,repository)
        } //squarish
        else
        {
            filter.setImageResource(R.drawable.landscape)
            currOrient="landscape"
            viewModel.requestRandomImages(30,currOrient,repository)
        }


    }

    override fun onStart() {
        super.onStart()
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }

    override fun onPostResume() {
        super.onPostResume()
        handler.postDelayed(runnable,4000)
    }
}


//check recyclerView last item and update more images
//        recyclerView.addOnScrollListener(object:RecyclerView.OnScrollListener(){
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//                val visibleItemCount = layoutManager.childCount
//                val totalItemCount=layoutManager.itemCount
//                val firstVisibleItems = layoutManager.findFirstVisibleItemPositions(null)
//                val lastVisibleItem = firstVisibleItems.max() ?: 0
//
//                if (!isLoading && lastVisibleItem + visibleItemCount >= totalItemCount){
//                    isLoading = true
//                    recyclerView.post { addMoreImages(totalItemCount) }
//                    isLoading=false
//                }
//            }
//        })

//    private fun addMoreImages(totalItemCount: Int) {
//        viewModel.requestRandomImages(repository)
//        viewModel.randomImages.observe(this) {
//            imgAdapter.setImages(it)
//            imgAdapter.notifyDataSetChanged()
//        }
//    }