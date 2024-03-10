package com.spitzer.common

import com.spitzer.common.result.asResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue


class ResultTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testScope = TestScope(UnconfinedTestDispatcher())

    @Test
    fun flowToResult_empty() = testScope.runTest {
        val flowable = flow<Int> {}

        assertTrue(
            flowable.asResult().first() is com.spitzer.common.result.Result.Loading
        )
    }

    @Test
    fun flowToResult_oneElement_success() = testScope.runTest {
        val value1 = 1
        val flowable = flow { emit(value1) }

        assertTrue(
            flowable.asResult().first() is com.spitzer.common.result.Result.Loading
        )

        assertTrue(
            flowable.asResult().drop(1).first() is com.spitzer.common.result.Result.Success
        )
        assertEquals(
            expected = value1,
            actual = (flowable.asResult().drop(1)
                .first() as com.spitzer.common.result.Result.Success).data,
        )
    }

    @Test
    fun flowToResult_oneElement_Error() = testScope.runTest {
        val exceptionMessage = "Test exception message"
        val flowable = flow<Int> {
            throw RuntimeException(exceptionMessage)
        }

        assertTrue(
            flowable.asResult().first() is com.spitzer.common.result.Result.Loading
        )

        assertTrue(
            flowable.asResult().drop(1).first() is com.spitzer.common.result.Result.Error
        )
        assertEquals(
            expected = exceptionMessage,
            actual = (flowable.asResult().drop(1)
                .first() as com.spitzer.common.result.Result.Error).exception.message,
        )
    }

    @Test
    fun flowToResult_multipleElements_successAndError() = testScope.runTest {
        val exceptionMessage = "Test exception message"
        val value1 = 1
        val value2 = 2
        val value3 = 3
        val flowable = flow {
            this.emit(value1)
            this.emit(value2)
            this.emit(value3)
            throw RuntimeException(exceptionMessage)
        }

        assertTrue(
            flowable.asResult().first() is com.spitzer.common.result.Result.Loading
        )

        assertTrue(
            flowable.asResult().drop(1).first() is com.spitzer.common.result.Result.Success
        )
        assertEquals(
            expected = value1,
            actual = (flowable.asResult().drop(1)
                .first() as com.spitzer.common.result.Result.Success).data,
        )

        assertTrue(
            flowable.asResult().drop(2).first() is com.spitzer.common.result.Result.Success
        )
        assertEquals(
            expected = value2,
            actual = (flowable.asResult().drop(2)
                .first() as com.spitzer.common.result.Result.Success).data,
        )

        assertTrue(
            flowable.asResult().drop(3).first() is com.spitzer.common.result.Result.Success
        )
        assertEquals(
            expected = value3,
            actual = (flowable.asResult().drop(3)
                .first() as com.spitzer.common.result.Result.Success).data,
        )

        assertTrue(
            flowable.asResult().drop(4).first() is com.spitzer.common.result.Result.Error
        )
        assertEquals(
            expected = exceptionMessage,
            actual = (flowable.asResult().drop(4)
                .first() as com.spitzer.common.result.Result.Error).exception.message,
        )
    }
}
