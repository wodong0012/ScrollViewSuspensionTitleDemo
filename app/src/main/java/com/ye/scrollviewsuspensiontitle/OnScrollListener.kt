package com.ye.scrollviewsuspensiontitle

/**
 * Create Time : 2020-06-27
 * Author : WoDong
 * Desc : MyScrollView滚动回调接口
 */
interface OnScrollListener {
    /**
     * 回调方法， 返回MyScrollView滑动的Y方向距离
     */
    fun onScroll(scrollY:Int)
}