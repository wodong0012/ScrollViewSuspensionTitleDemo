package com.ye.scrollviewsuspensiontitle

import android.content.Context
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.ScrollView


/**
 * Create Time : 2020-06-27
 * Author : WoDong
 * Desc : 自定义ScrollView监听控件滑动时Y的坐标
 */
class MyScrollView : ScrollView {

    private lateinit var onScrollListener: OnScrollListener

    constructor(context: Context?) : super(context,null)
    constructor(context: Context?,attrs:AttributeSet?):super(context,attrs,0)
    constructor(context: Context?,attrs: AttributeSet?,defStyle:Int):super(context, attrs,defStyle)
    private val TAG = javaClass.simpleName
    /**
     * 用于记录用户手指离开屏幕后，屏幕还在滑动的值
     */
    private var lastScrollY = 0

    /**
     * 设置滚动接口
     */
    fun setOnScrollListener(listener:OnScrollListener){
        this.onScrollListener = listener

    }

    private val handler = object : Handler(){
        override fun handleMessage(msg: Message) {
            var scrollY:Int = this@MyScrollView.scrollY
            Log.e(TAG,"$scrollY")

            //此时的距离和记录的距离不相等，隔5毫秒给handler发消息
            if (lastScrollY != scrollY) {
                lastScrollY = scrollY
                sendMessageDelayed(this.obtainMessage(),5)
            }
            if (onScrollListener != null) {
                onScrollListener.onScroll(scrollY)
            }

        }
    }

    /**
     * 重onTouchEvent，当用户手指在MyScrollView上时直接将MyScrollView滑动的Y传递给OnScroll回调
     * 当用户抬起手指时，MyScrollView可能还在滑动，所以间隔20毫秒给handler发消息 ，在handler处理MyScrollView的滑动距离
     */
    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        if (onScrollListener != null) {
            lastScrollY = this.scrollY
            onScrollListener.onScroll(lastScrollY)
        }

        when (ev?.action) {
            MotionEvent.ACTION_UP -> {
                handler.sendMessageDelayed(handler.obtainMessage(), 20)
            }
        }
        return super.onTouchEvent(ev)
    }
}
