package converter

val lengthUnits = arrayOf("m","meter","meters","km","kilometer","kilometers","cm","centimeter","centimeters","mm","millimeter","millimeters","mi","mile","miles","yd","yard","yards","ft","foot","feet","in","inch","inches")
val massUnits = arrayOf("g","gram","grams","kg","kilogram","kilograms","mg","milligram","milligrams","lb","pound","pounds","oz","ounce","ounces")


fun main() {
    showMenu()
}

fun showMenu() {
    while (true) {
        println("Enter what you want to convert (or exit): ")
        val input = readln().split(" ")
        if (input[0] == "exit") break
        val amount = input[0].toDouble()
        var inputUnit = input[1].lowercase()
        var outputUnit = input[3].lowercase()

        if (lengthUnits.contains(inputUnit)) {
            if (lengthUnits.contains(outputUnit)) {
                convertLength(inputUnit,amount,outputUnit)
            } else {
                if (massUnits.contains(outputUnit)) {
                    println("Conversion from ${makePlural(inputUnit)} to ${makePlural(outputUnit)} is impossible")
                } else println("Wrong output. Unknown unit $outputUnit")
            }
        } else {
            if (massUnits.contains(inputUnit)) {
                if (massUnits.contains(outputUnit)) {
                    convertMass(inputUnit,amount,outputUnit)
                } else if (lengthUnits.contains(outputUnit)) {
                    println("Conversion from ${makePlural(inputUnit)} to ${makePlural(outputUnit)} is impossible")
                } else println("Wrong output. Unknown unit $outputUnit")
            }
            else println("Wrong input. Unknown unit $inputUnit")
        }
    }
}

fun convertLength(_inputUnit: String, inputAmount: Double, _outputUnit: String) {
    val inputUnit = clarifyUnitName(_inputUnit,inputAmount)
    val amountInMeters = convertToMeters(inputAmount,inputUnit)
    val convertedAmount = convertFromMeters(amountInMeters, _outputUnit)
    val outputUnit = clarifyUnitName(_outputUnit,convertedAmount)
    println("$inputAmount $inputUnit is $convertedAmount $outputUnit")
}

fun convertMass(_inputUnit: String, inputAmount: Double, _outputUnit: String) {
    val inputUnit = clarifyUnitName(_inputUnit,inputAmount)
    val amountInGrams = convertToGrams(inputAmount,inputUnit)
    val convertedAmount = convertFromGrams(amountInGrams, _outputUnit)
    val outputUnit = clarifyUnitName(_outputUnit,convertedAmount)
    println("$inputAmount $inputUnit is $convertedAmount $outputUnit")
}

fun convertToMeters(amount: Double, unit: String): Double {
    return when (unit) {
        "meter","meters" -> amount
        "kilometer","kilometers" -> amount*1000
        "centimeter","centimeters" -> amount*0.01
        "millimeter","millimeters" -> amount*0.001
        "mile","miles" -> amount*1609.35
        "yard","yards" -> amount*0.9144
        "foot","feet" -> amount*0.3048
        "inch","inches" -> amount*0.0254
        else -> 0.0
    }
}

fun convertFromMeters(amount: Double, unit: String): Double {
    return when (unit) {
        "meter","meters" -> amount
        "kilometer","kilometers" -> amount/1000
        "centimeter","centimeters" -> amount/0.01
        "millimeter","millimeters" -> amount/0.001
        "mile","miles" -> amount/1609.35
        "yard","yards" -> amount/0.9144
        "foot","feet" -> amount/0.3048
        "inch","inches" -> amount/0.0254
        else -> 0.0
    }
}

fun convertToGrams(amount: Double, unit: String): Double {
    return when (unit) {
        "gram","grams" -> amount
        "kilogram","kilograms" -> amount*1000
        "milligram","milligrams" -> amount*0.001
        "pound","pounds" -> amount*453.592
        "ounce","ounces" -> amount*28.3495
        else -> 0.0
    }
}

fun convertFromGrams(amount: Double, unit: String): Double {
    return when (unit) {
        "gram","grams" -> amount
        "kilogram","kilograms" -> amount/1000
        "milligram","milligrams" -> amount/0.001
        "pound","pounds" -> amount/453.592
        "ounce","ounces" -> amount/28.3495
        else -> 0.0
    }
}

fun clarifyUnitName(unit: String, amount: Double): String {
    var newUnit = when (unit) {
        "m", "meter", "meters" -> "meter"
        "km", "kilometer", "kilometers" -> "kilometer"
        "cm", "centimeter", "centimeters" -> "centimeter"
        "mm", "millimeter", "millimeters" -> "millimeter"
        "mi", "mile", "miles" -> "mile"
        "yd", "yard", "yards" -> "yard"
        "ft", "foot", "feet" -> "foot"
        "in", "inch", "inches" -> "inch"

        "g", "gram", "grams" -> "gram"
        "kg","kilogram", "kilograms" -> "kilogram"
        "mg", "milligram", "milligrams" -> "milligram"
        "lb", "pound", "pounds" -> "pound"
        "oz", "ounce", "ounces" -> "ounce"

        else -> unit
    }
    if (amount != 1.0) {
        newUnit = makePlural(newUnit)
    }
    return newUnit
}

fun makePlural(input: String): String {
    var output = input
    if (output != "feet" && output != "inches" && output.last() != 's') {
        when (output) {
            "foot" -> output = "feet"
            "inch" -> output += "es"
            else -> output += 's'
        }
    }
    return output
}