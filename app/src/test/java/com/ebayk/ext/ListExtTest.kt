package com.ebayk.ext

import org.junit.Assert
import org.junit.Test

class ListExtTest {

    @Test
    fun `divide to pairs, a list has 0 items, the result has 0 items`() {
        val initialList = listOf<Int>()

        val dividedList = initialList.divideToPairs()

        Assert.assertEquals(dividedList.size, 0)
    }

    @Test
    fun `divide to pairs, a list has 4 items, the result has a half of that`() {
        val initialList = listOf(1, 2, 3, 4)

        val dividedList = initialList.divideToPairs()

        Assert.assertEquals(dividedList.size, 2)
        Assert.assertEquals(dividedList[0], 1 to 2)
        Assert.assertEquals(dividedList[1], 3 to 4)
    }

    @Test
    fun `divide to pairs, a list has 5 items, the result has 3 items, the second part of the last item is null`() {
        val initialList = listOf(1, 2, 3, 4, 5)

        val dividedList = initialList.divideToPairs()

        Assert.assertEquals(dividedList.size, 3)
        Assert.assertEquals(dividedList[0], 1 to 2)
        Assert.assertEquals(dividedList[1], 3 to 4)
        Assert.assertEquals(dividedList[2], 5 to null)
    }
}