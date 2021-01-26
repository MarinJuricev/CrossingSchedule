package com.example.crossingschedule.feature.schedule.data.factory

import com.example.crossingschedule.feature.schedule.domain.model.Shop
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test

class DefaultShopFactoryImplTest {

    private lateinit var sut: DefaultShopFactory

    @Before
    fun setUp() {
        sut = DefaultShopFactoryImpl()
    }

    @Test
    fun `generate should generate a list containing NooksCranny, AbleSisters, Museum`(){
        val actualResult = sut.generate()
        val expectedResult =  listOf(
            Shop("NooksCranny"),
            Shop("AbleSisters"),
            Shop("Museum")
        )

        assert(actualResult == expectedResult)
    }
}