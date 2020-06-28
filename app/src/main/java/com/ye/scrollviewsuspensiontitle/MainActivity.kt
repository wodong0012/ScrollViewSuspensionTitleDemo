package com.ye.scrollviewsuspensiontitle

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnScrollListener {

    /**
     *
     */
    private var searchLayoutTop = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initUi()
    }

    private fun initUi() {
//        rlayout = findViewById(R.id.rlayout)
        myScrollView.setOnScrollListener(this)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            //获取searchLayout的顶部位置
            searchLayoutTop = rlayout.bottom
        }
    }


    override fun onScroll(scrollY: Int) {
        if(scrollY >= searchLayoutTop){
            if (search_edit.getParent()!=search01) {
                search02.removeView(search_edit)
                search01.addView(search_edit)
            }
        }else{
            if (search_edit.getParent()!=search02) {
                search01.removeView(search_edit)
                search02.addView(search_edit)
            }
        }
    }
}
