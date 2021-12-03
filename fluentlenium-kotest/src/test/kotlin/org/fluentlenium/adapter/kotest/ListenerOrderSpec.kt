package org.fluentlenium.adapter.kotest

import io.kotest.core.extensions.Extension
import io.kotest.core.listeners.AfterEachListener
import io.kotest.core.listeners.AfterTestListener
import io.kotest.core.listeners.BeforeEachListener
import io.kotest.core.listeners.BeforeTestListener
import io.kotest.core.spec.style.StringSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult

class ListenerOrderSpec : StringSpec() {

    override fun beforeEach(testCase: TestCase) {
        println("f beforeEach")
    }

    override fun beforeAny(testCase: TestCase) {
        println("f beforeAny")
    }

    override fun beforeTest(testCase: TestCase) {
        println("f beforeTest")
    }

    override fun afterEach(testCase: TestCase, result: TestResult) {
        println("f afterEach")
    }

    override fun afterAny(testCase: TestCase, result: TestResult) {
        println("f afterAny")
    }

    override fun afterTest(testCase: TestCase, result: TestResult) {
        println("f afterTest")
    }

    override fun extensions(): List<Extension> = listOf(beforeTestListener("extensions()"))

    init {

        beforeEach {
            println("d beforeEach")
        }

        beforeAny {
            println("d beforeAny")
        }

        beforeTest {
            println("d beforeTest")
        }

        register(beforeTestListener("init"))

        extension(beforeTestListener("extension"))

        register(object : AfterTestListener, AfterEachListener {
            override suspend fun afterAny(testCase: TestCase, result: TestResult) {
                println("e afterAny")
            }

            override suspend fun afterTest(testCase: TestCase, result: TestResult) {
                println("e afterTest")
            }

            override suspend fun afterEach(testCase: TestCase, result: TestResult) {
                println("e afterEach")
            }
        })



        "test" {
            println("run test")
        }

        afterTest {
            println("d afterTest")
        }

        afterAny {
            println("d afterAny")
        }

        afterEach {
            println("d afterEach")
        }
    }

    private fun beforeTestListener(suffix: String = "") = object : BeforeTestListener, BeforeEachListener {
        override suspend fun beforeAny(testCase: TestCase) {
            println("e beforeAny $suffix")
        }

        override suspend fun beforeTest(testCase: TestCase) {
            println("e beforeTest $suffix")
        }

        override suspend fun beforeEach(testCase: TestCase) {
            println("e beforeEach $suffix")
        }
    }
}