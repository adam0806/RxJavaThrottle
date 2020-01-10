package com.example.lenovo.throttle

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*
/**
 * @author Adam
 * @date 2020/1/10
 * GitHub: https://github.com/adam0806
 * jianshu: https://www.jianshu.com/u/83868141c938
 * Email: adam0806@gmail
 */
class MainActivity : AppCompatActivity() {
    val mPeriod:Long = 1000;
    var tvShow:TextView ?= null;
    var tvShow2:TextView ?= null;
    var str:StringBuilder = StringBuilder();
    var str2:StringBuilder = StringBuilder();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tv = findViewById<TextView>(R.id.tv)
        val tv2 = findViewById<TextView>(R.id.tv2)
        val tvClear = findViewById<TextView>(R.id.tv_clear)
        tvShow = findViewById<TextView>(R.id.tv_show)
        tvShow2 = findViewById<TextView>(R.id.tv_show2)

        tv.setOnClickListener({
            str.append("click: "+SimpleDateFormat("mm:ss:SSS").format(Date(System.currentTimeMillis()))+ "\n")
            tvShow?.setText(str)
            Throttle.Builder<String>("1")
                .throttleFirst(mPeriod)
                .subscribe(object : Throttle.Observable{
                    override fun onSubscribe() {
                        str.append("change: "+ SimpleDateFormat("mm:ss:SSS").format(Date(System.currentTimeMillis())) +"\n")
                        tvShow?.setText(str)
                    }
                })
            })
        tv2.setOnClickListener({
            str2.append("click: "+SimpleDateFormat("mm:ss:SSS").format(Date(System.currentTimeMillis()))+ "\n")
            tvShow2?.setText(str2)
            Throttle.Builder<Int>(tv.hashCode()+1)
                    .throttleLast(mPeriod)
                    .subscribe(object : Throttle.Observable{
                        override fun onSubscribe() {
                            str2.append("change: "+ SimpleDateFormat("mm:ss:SSS").format(Date(System.currentTimeMillis())) +"\n")
                            tvShow2?.setText(str2)
                        }
                    })
        })
        tvClear.setOnClickListener({
            str.delete(0, str.length)
            tvShow?.setText("");
            str2.delete(0, str2.length)
            tvShow2?.setText("");
        })
    }
}