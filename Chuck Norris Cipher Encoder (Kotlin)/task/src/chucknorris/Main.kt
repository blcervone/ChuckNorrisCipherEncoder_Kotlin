package chucknorris

import java.lang.IndexOutOfBoundsException

var prev = ' '
var count = 0
var result = ""
var encoded = ""

fun encodeString() {
    println("Input string:")
    val input = readln()
    for (i in input) {
        val charToBinary = Integer.toBinaryString(i.code).padStart(7, '0')
        for (num in charToBinary) {
            when (num) {
                prev -> encoded += "0"
                '1' -> encoded += " 0 0"
                '0' -> encoded += " 00 0"
            }
            prev = num
        }
    }
    println("Encoded string:")
    println(encoded)
}

fun decodeString() {
    println("Input encoded string:")
    val input = readln()
    val inputAsList: MutableList<String> = input.split(' ').toMutableList()
    if (!isValidInput(inputAsList)) {
        return
    } else {
        for ((index, element) in inputAsList.withIndex()) {
            if (index % 2 == 0) {
                when (element) {
                    "0" -> {
                        val nextCell = try {
                            inputAsList[index + 1]
                        } catch (e: IndexOutOfBoundsException) {
                            break
                        }
                        count = nextCell.count()
                        repeat(count) {
                            result += "1"
                        }
                    }

                    "00" -> {
                        val nextCell = try {
                            inputAsList[index + 1]
                        } catch (e: IndexOutOfBoundsException) {
                            break
                        }
                        count = nextCell.count()
                        repeat(count) {
                            result += "0"
                        }
                    } else -> continue
                }
            } else continue
        }
    }
    val resultToList = result.chunked(7).toList()
    if (result.count() % 7 != 0) {
        println("Encoded string is not valid.")
    } else {
        println("Decoded string:")
        for (i in resultToList) {
            print(i.toInt(2).toChar())
        }
        println()
    }
}

fun isValidInput(inputAsList: MutableList<String>): Boolean {
    for ((index, element) in inputAsList.withIndex()) {
        return if (index % 2 == 0) {
            if (element == "0" || element == "00") {
                continue
            } else {
                println("Encoded string is not valid.")
                false
            }
        } else if (!element.contains('0')) {
            println("Encoded string is not valid.")
            false
        } else continue
    }
    return if ((inputAsList.lastIndex + 1) % 2 != 0) {
        println("Encoded string is not valid.")
        false
    } else true
}

fun main() {
    while (true) {
        println("Please input operation (encode/decode/exit):")
        when (val menuSelection = readln()) {
            "encode" -> {
                encodeString()
                println()
            }
            "decode" -> {
                decodeString()
                println()
            }
            "exit" -> {
                println("Bye!")
                break
            }
            else -> {
                println("There is no '$menuSelection' operation")
                println()
            }
        }
    }
}