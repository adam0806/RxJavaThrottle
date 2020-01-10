# Throttle
Help easy to use throttle include throttle-first and last

![image](https://github.com/adam0806/Throttle/blob/master/throttle_demo_small.gif)

Introduction:(no need RxJava)
1. Support Builder-Pattern
2. Only one class, just copy it to your project, it's done.

Use:
1. choose throttleFirst or throttleLast with millisecon period
2. subscribe(alrealy on Main-Thread)

```Kotlin
Throttle.Builder<String>("1")
                .throttleFirst(mPeriod)
                .subscribe(object : Throttle.Observable{
                    override fun onSubscribe() {
                        str.append("change: "+ SimpleDateFormat("mm:ss:SSS").format(Date(System.currentTimeMillis())) +"\n")
                        tvShow?.setText(str)
                    }
                })
            })
```
