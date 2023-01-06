package com.main.core.communication

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

interface Communication {

    interface Observe<T> {
        fun observe(owner: LifecycleOwner, observer: Observer<T>)
    }

    interface Mutate<T> {
        fun map(value: T)
    }

    interface Value<T> {
        fun value(): T?
    }

    interface Mutable<T> : Observe<T>, Mutate<T>, Value<T>

    abstract class Abstract<T>(
        protected val liveData: MutableLiveData<T> = MutableLiveData()
    ) : Mutable<T> {
        override fun observe(owner: LifecycleOwner, observer: Observer<T>) {
            liveData.observe(owner, observer)
        }
        override fun value(): T? {
            return liveData.value
        }
    }

    abstract class Ui<T: Any>(
        liveData: MutableLiveData<T> = MutableLiveData()
    ) : Abstract<T>(liveData) {
        override fun map(value: T) {
            liveData.value = value
        }
    }

    abstract class Post<T: Any>(
        liveData: MutableLiveData<T> = MutableLiveData()
    ) : Abstract<T>(liveData) {
        override fun map(value: T) = liveData.postValue(value)
    }

    abstract class SingleUi<T: Any> : Ui<T>(SingleLiveEvent())
    abstract class SinglePost<T: Any> : Post<T>(SingleLiveEvent())
}