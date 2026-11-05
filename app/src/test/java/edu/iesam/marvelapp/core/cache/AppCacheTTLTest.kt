package edu.iesam.marvelapp.core.cache

import android.content.Context
import android.content.SharedPreferences
import edu.iesam.marvelapp.core.time.AppTime
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import kotlin.math.exp

class AppCacheTTLTest {
    private val context: Context = mockk()
    private val sharedPref: SharedPreferences = mockk()
    private val editor: SharedPreferences.Editor = mockk(relaxed = true)
    private val appTime: AppTime = mockk()

    private lateinit var appCache: AppCacheTTL

    @Before
    fun setup(){
        every {context.getString(any())} returns "app_cache"
        every {context.getSharedPreferences("app_cache", Context.MODE_PRIVATE)} returns sharedPref
        every {sharedPref.edit()} returns editor
        every {editor.putLong(any(), any())} returns editor

        appCache = AppCacheTTL(context, appTime)
    }

    @Test
    fun `updateTime guarda la hora actual con la clave indicada`(){
        //given
        every { appTime.currentTimeToLong() } returns 1_000L

        //when
        appCache.updateTime("heroes")

        //then
        verify(exactly = 1) { editor.putLong("heroes", 1_000L) }
        verify(exactly = 1) { editor.apply() }
    }

    @Test
    fun `isExpired devuelve false si no ha pasado el ttl`(){
        //given
        every { sharedPref.getLong("heroes", -1) } returns 1_000L
        every { appTime.currentTimeToLong() } returns 1_000L

        //when
        val result = appCache.isExpired("heroes", ttl = 1_000L)

        //then
        assertFalse(result)
    }
}