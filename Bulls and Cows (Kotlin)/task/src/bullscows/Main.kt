package bullscows


import java.util.*

fun main() {

    val scanner = Scanner(System.`in`)

    print("Input the length of the secret code:")
    var length = 0
    try {
        length = scanner.nextInt()
    } catch (e: Exception) {
        println("Error: $e")
        return
    }

    print("Input the number of possible symbols in the code:")
    val numberOfSymbols = readLine()?.toIntOrNull() ?: 0

    if (length > numberOfSymbols) {
        println("Error: it's not possible to generate a code with a length of $length with $numberOfSymbols unique symbols.")
        return
    }

    if (numberOfSymbols > 36) {
        println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).")
        return
    }

    if (length <= 0 || numberOfSymbols <= 0) {
        println("Error: both the length and number of possible symbols must be greater than 0.")
        return
    }


    val secretNumber = generateSecretNumber(length, numberOfSymbols)
    println(secretNumber)

    val stars = printStars(length)
    val range = printRange(numberOfSymbols)

    println("The secret is prepared: $stars $range.")

    println("Okay, let's start a game!")

    var bulls = 0
    var cows = 0
    var turnCount = 1

    while (bulls != length) {
        println("Turn $turnCount:")
        val gamersInput = readLine()!!
        bulls = 0
        cows = 0
        for (i in 0 until length) {
            if (gamersInput[i] == secretNumber[i]) {
                bulls++
                continue
            } else {
                for (j in 0 until length) {
                    if (gamersInput[i] == secretNumber[j] && i != j) {
                        cows++
                    }
                    continue
                }
            }
        }
        turnCount++

        if (bulls == 0 && cows == 0) {
            println("Grade: None")
        } else if (cows == 0) {
            println("Grade: $bulls bull(s)")
        } else if (bulls == 0) {
            println("Grade: $cows cow(s)")
        } else {
            println("Grade: $bulls bull(s) and $cows cow(s)")
        }
    }

    println("Congratulations! You guessed the secret code.")

}


fun generateSecretNumber(length: Int, numberOfSymbols: Int): String {
    val secretNumber = StringBuilder()
    val random = Random()
    val random1 = random.nextInt(numberOfSymbols)
    val charToInsert = returnNumberOrChar(random1)
    secretNumber.append(charToInsert)
    while (secretNumber.length < length) {
        val random2 = random.nextInt(numberOfSymbols)
        val charToInsert2 = returnNumberOrChar(random2)
        var flag = true
        for (i in secretNumber.indices) {
            if (secretNumber[i].toString() == charToInsert2) flag = false
        }
        if (flag) secretNumber.append(charToInsert2)
        else continue
    }
    return secretNumber.toString()
}

fun returnNumberOrChar(number: Int): String {
    return if (number in 0..9) {
        number.toString()
    } else {
        ('a' + number - 10).toString()
    }
}

fun printStars(length: Int): String {
    var str = StringBuilder()
    for (i in 0 until length) {
        str.append("*")
    }
    return str.toString()
}

fun printRange(length: Int): String {
    if (length < 10) {
        return "(0-$length)"
    } else {
        val ch = returnNumberOrChar(length - 1)
        return "(0-9, a-$ch)"
    }
}