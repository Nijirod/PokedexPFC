package com.example.pokedexapp.data


import androidx.annotation.StringRes

import com.example.pokedexapp.data.Status.SUCCESS
import com.example.pokedexapp.data.Status.ERROR
import com.example.pokedexapp.data.Status.LOADING
import com.example.pokedexapp.data.Status.NONE

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
</T> */
data class Resource<out T>(val status: Status, val data: T?, val message: String? = null, @StringRes val messageResId: Int? = 0) {

    companion object {

        fun <T> success(data: T?): Resource<T> {
            return Resource(SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(ERROR, data, msg)
        }

        fun <T> error(@StringRes msgResId: Int, data: T?): Resource<T> {
            return Resource(ERROR, data, null, msgResId)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(LOADING, data, null)
        }

        fun <T> none(data: T?): Resource<T> {
            return Resource(NONE, data, null)
        }
    }
}

