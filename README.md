# SuspensionTitleScrollviewDemo
 *上滑停靠顶端的悬浮框*
  思路：自定义ScrollView类
       >>使用一个变量记录手指没有触摸屏幕后的Y值
       >>重写ScrollView的onTouchEvent方法，获取scrollY的值，并传入接口中
       在main中使用的是addView和removeView两个方法来添加和删除布局
   ```kotlin
    // MainActivity.kt核心代码
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
```
```kotlin
//MyScrollView核心代码
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
```

    
    
    
![](https://github.com/wodong0012/ScrollViewSuspensionTitleDemo/blob/master/demo.gif)
