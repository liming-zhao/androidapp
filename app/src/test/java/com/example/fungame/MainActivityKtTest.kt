package com.example.fungame

import org.junit.Assert
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

private const val FAKE_STRING = "HELLO WORLD"

@RunWith(MockitoJUnitRunner::class)
class MainActivityKtTest {


    @Test
    fun generateRandomIntegers() {

        var res = generateRandomIntegers(5);
        Assert.assertTrue(res.size == 5);
    }

    @Test
    fun generateSuit() {

        var res = generateSuit(5);
        Assert.assertTrue(res[0] in arrayOf("clubs","diamonds","hearts", "spades"))
    }

    @Test
    fun generateRandomCardVal() {
        var res = generateRandomCardVal(5);
        Assert.assertTrue(res[0].suit in arrayOf("clubs","diamonds","hearts", "spades"))
        //Assert.assertTrue(res[0].name in arrayOf(1,2,3,4))

    }
}