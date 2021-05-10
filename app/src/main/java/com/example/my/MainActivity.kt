package com.example.my

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.function.LongFunction

class MainActivity : AppCompatActivity(),TextWatcher,View.OnFocusChangeListener {
    //左边手臂
    //升起
    private val leftArmAnimatorSet:AnimatorSet by lazy {
        val rotate=ObjectAnimator.ofFloat(leftArm,"rotation",
            145f)
        val transX = ObjectAnimator.ofFloat(leftArm,"translationX",
        dp2px(50f))
        val transY = ObjectAnimator.ofFloat(leftArm,"translationY",
        dp2px(-45f))
        AnimatorSet().apply {
            duration = 500
            playTogether(rotate,transX,transY)
        }
    }
    //降落
    private val leftArmAnimatorSetDown:AnimatorSet by lazy {
        val rotate=ObjectAnimator.ofFloat(leftArm,"rotation",
                0f)
        val transX = ObjectAnimator.ofFloat(leftArm,"translationX",
                dp2px(0f))
        val transY = ObjectAnimator.ofFloat(leftArm,"translationY",
                dp2px(0f))
        AnimatorSet().apply {
            duration = 500
            playTogether(rotate,transX,transY)
        }
    }
    //右边手臂
    //升起
    private val rightArmAnimator:AnimatorSet by lazy {
        val rotate=ObjectAnimator.ofFloat(rightArm,"rotation",
                -145f)
        val transX = ObjectAnimator.ofFloat(rightArm,"translationX",
                dp2px(-50f))
        val transY = ObjectAnimator.ofFloat(rightArm,"translationY",
                dp2px(-45f))
        AnimatorSet().apply {
            duration = 500
            playTogether(rotate,transX,transY)
        }
    }
    //降落
    private val rightArmAnimatorDown:AnimatorSet by lazy {
        val rotate=ObjectAnimator.ofFloat(rightArm,"rotation",
                0f)
        val transX = ObjectAnimator.ofFloat(rightArm,"translationX",
                dp2px(0f))
        val transY = ObjectAnimator.ofFloat(rightArm,"translationY",
                dp2px(0f))
        AnimatorSet().apply {
            duration = 500
            playTogether(rotate,transX,transY)
        }
    }
   //左手掌手掌
    private val lefttAnimatorhandUp:ObjectAnimator by lazy {
        ObjectAnimator.ofFloat(lefthand,"translationY",dp2px(0f))
   }
    private val leftAnimatorhandDown:ObjectAnimator by lazy {
        ObjectAnimator.ofFloat(lefthand,"translationY",dp2px(40f))
    }
   //右手掌
   private val righttAnimatorhandUp:ObjectAnimator by lazy {
       ObjectAnimator.ofFloat(righthand,"translationY",dp2px(0f))
   }
    private val rightAnimatorhandDown:ObjectAnimator by lazy {
        ObjectAnimator.ofFloat(righthand,"translationY",dp2px(40f))
    }

    private var isopen = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        edittext_password.onFocusChangeListener = this
        edittext_password.setOnClickListener {
            hideOropenBoard_view(it)
        }
        edittext_name.setOnClickListener {
            hideOropenBoard_view(it)
        }

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }

    override fun afterTextChanged(s: Editable?) {

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event?.action){
            MotionEvent.ACTION_DOWN->{
            hideKeyboard()
            }
        }
        return super.onTouchEvent(event)
    }
    //隐藏键盘
    private fun hideKeyboard(){
        val keyboard =getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        //this.currentFocus?.windowToken 是获取当前activity的windowToken
        keyboard.hideSoftInputFromWindow(this.currentFocus?.windowToken,0)
    }
    //对特定视图键盘的调用
    private fun hideOropenBoard_view(v:View){

        val keyboard = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        //keyboard.isActive是键盘的状态，若是打开的则返回true否则返回false
        //
        if (isopen){
            Log.v("lfl","关闭键盘")
            //强制关闭
            keyboard.hideSoftInputFromWindow(v.windowToken,0)
            isopen = !isopen
        }else{
            Log.v("lfl","打开键盘")
            //打开
            keyboard.showSoftInput(v,InputMethodManager.SHOW_FORCED)
           isopen = !isopen
        }
    }
    //若键盘是隐藏的则打开，若是打开的则隐藏
    private fun hideOropenKeyboard(){
        val keyboard = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        keyboard.toggleSoftInput(0,InputMethodManager.HIDE_NOT_ALWAYS)
    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
       if(v == edittext_password){
           if (hasFocus){
               //焦点在该视图上,蒙上眼睛
                   AnimatorSet().apply {
                       play(leftArmAnimatorSet)
                               .with(rightArmAnimator)
                               .after(leftAnimatorhandDown)
                               .after(rightAnimatorhandDown)
                   }.start()
           }else{
               //焦点没有在该视图上，打开眼睛
                   AnimatorSet().apply {
                       play(leftArmAnimatorSetDown)
                           .with(rightArmAnimatorDown)
                           .after(lefttAnimatorhandUp)
                           .after(righttAnimatorhandUp)
                   }.start()
           }
       }
    }
    //将dp值转换为相应的像素值
    private fun dp2px(dp:Float):Float{
        return resources.displayMetrics.density*dp
    }
}

