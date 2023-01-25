package converter

val lengthUnits = arrayOf("m","meter","meters","km","kilometer","kilometers","cm","centimeter","centimeters","mm","millimeter","millimeters","mi","mile","miles","yd","yard","yards","ft","foot","feet","in","inch","inches")
val massUnits = arrayOf("g","gram","grams","kg","kilogram","kilograms","mg","milligram","milligrams","lb","pound","pounds","oz","ounce","ounces")
val temperatureUnits = arrayOf("degree celsius","degrees celsius","celsius","dc","c","degree fahrenheit","degrees fahrenheit","fahrenheit","df","f","kelvin","kelvins","k")

fun main() {
    showMenu()
}

fun showMenu() {
    while (true) {
        println("Enter what you want to convert (or exit): ")
        val input = readln().split(" ")
        if (input[0] == "exit") break
        val amount = input[0].toDouble()

        var inputUnit = ""
        var outputUnit = ""
        if (input[1].contains("degree")) {
            inputUnit = "${input[1]} ${input[2]}"
            if (input[4].contains("degree")) {
                outputUnit = "${input[4]} ${input[5]}"
            } else {
                outputUnit = input[4]
            }
        } else {
            inputUnit = input[1].lowercase()
            outputUnit = input[3].lowercase()
        }

        if (isLength(inputUnit) && isLength(outputUnit)) {
            convertLength(inputUnit, amount, outputUnit)
        } else if (isMass(inputUnit) && isMass(outputUnit)) {
            convertMass(inputUnit, amount, outputUnit)
        } else if (isTemp(inputUnit) && isTemp(outputUnit)) {
            convertTemperature(inputUnit, amount, outputUnit)
        } else {
            handleUnitMismatch(inputUnit, outputUnit)
        }
    }
}

fun isLength(unit: String): Boolean {
    return lengthUnits.contains(unit)
}
fun isMass(unit: String): Boolean {
    return massUnits.contains(unit)
}
fun isTemp(unit: String): Boolean {
    return temperatureUnits.contains(unit)
}
fun isAny(unit: String): Boolean {
    return (isMass(unit) || isLength(unit) || isTemp(unit))
}

fun convertLength(_inputUnit: String, inputAmount: Double, _outputUnit: String) {
    val inputUnit = clarifyUnitName(_inputUnit,inputAmount)
    var outputUnit = clarifyUnitName(_outputUnit,1.0)
    val amountInMeters = convertToMeters(inputAmount,inputUnit)
    val convertedAmount = convertFromMeters(amountInMeters, outputUnit)
    outputUnit = clarifyUnitName(outputUnit,convertedAmount)
    println("$inputAmount $inputUnit is $convertedAmount $outputUnit")
}

fun convertMass(_inputUnit: String, inputAmount: Double, _outputUnit: String) {
    val inputUnit = clarifyUnitName(_inputUnit,inputAmount)
    var outputUnit = clarifyUnitName(_outputUnit,1.0)
    val amountInGrams = convertToGrams(inputAmount,inputUnit)
    val convertedAmount = convertFromGrams(amountInGrams, outputUnit)
    outputUnit = clarifyUnitName(outputUnit,convertedAmount)
    println("$inputAmount $inputUnit is $convertedAmount $outputUnit")
}

fun convertTemperature(_inputUnit: String, inputTemperature: Double, _outputUnit: String) {
    val inputUnit = clarifyTempUnitName(_inputUnit,1.0)
    val outputUnit = clarifyTempUnitName(_outputUnit, 1.0)

    val convertedTemperature: Double = when (inputUnit) {
        "degree Celsius" -> {
            when (outputUnit) {
                "degree Fahrenheit" -> celsiusFahrenheitConversion(inputTemperature)                            //C->F
                "Kelvin", -> celsiusKelvinConversion(inputTemperature)                                          //C->K
                else -> inputTemperature                                                                        //C->C
            }
        }
        "degree Fahrenheit" -> {
            when (outputUnit) {
                "degree Celsius" -> celsiusFahrenheitConversion(inputTemperature,true)         //F->C
                "Kelvin" -> kelvinFahrenheitConversion(inputTemperature,true)                   //F->K
                else -> inputTemperature                                                                        //F->F
            }
        }
        "Kelvin" -> {
            when (outputUnit) {
                "degree Fahrenheit" -> kelvinFahrenheitConversion(inputTemperature)                             //K -> F
                "degree Celsius" -> celsiusKelvinConversion(inputTemperature,true)                //K -> C
                else -> inputTemperature                                                                        // K -> K
            }
        }
        else -> 0.0
    }

    println("$inputTemperature ${clarifyTempUnitName(inputUnit,inputTemperature)} is $convertedTemperature ${clarifyTempUnitName(outputUnit,convertedTemperature)}")
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

fun celsiusFahrenheitConversion(temp: Double, fahrenheitToCelsius: Boolean = false): Double {
    return if (fahrenheitToCelsius) {
        (temp - 32) * (5.0/9.0)
    } else {
        temp * (9.0/5.0) + 32
    }
}

fun celsiusKelvinConversion(temp: Double, kelvinToCelsius: Boolean = false): Double {
    return if (kelvinToCelsius) {
        temp - 273.15
    } else {
        temp + 273.15
    }
}


fun kelvinFahrenheitConversion(temp: Double, fahrenheitToKelvin: Boolean = false): Double {
    return if (fahrenheitToKelvin) {
        (temp + 459.67) * (5.0/9.0)
    } else {
        temp*(9.0/5.0) - 459.67
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

fun clarifyTempUnitName(unit: String, amount: Double): String {
    var newUnit = when (unit) {
        "degree celsius","degrees celsius","celsius","dc","c" -> "degree Celsius"
        "degree fahrenheit","degrees fahrenheit","fahrenheit","df","f" -> "degree Fahrenheit"
        "kelvin","kelvins","k" -> "Kelvin"
        else -> unit
    }
    if (amount != 1.0) {
        newUnit = when (newUnit) {
            "degree Celsius" -> "degrees Celsius"
            "degree" -> "degrees Fahrenheit"
            else -> newUnit
        }
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

fun handleUnitMismatch(inputUnit: String, outputUnit: String) {
    if (isLength(inputUnit)) {
        if (isMass(outputUnit)) {
            println("Conversion from ${clarifyUnitName(inputUnit,0.0)} to ${clarifyUnitName(outputUnit,0.0)} is impossible")
        } else if (isTemp(outputUnit)) {
            println("Conversion from ${clarifyUnitName(inputUnit,0.0)} to ${clarifyTempUnitName(outputUnit,0.0)} is impossible")
        } else {
            println("Conversion from ${clarifyUnitName(inputUnit,0.0)} to ??? is impossible")
        }
    } else if (isMass(inputUnit)) {
        if (isLength(outputUnit)) {
            println("Conversion from ${clarifyUnitName(inputUnit,0.0)} to ${clarifyUnitName(outputUnit,0.0)} is impossible")
        } else if (isTemp(outputUnit)) {
            println("Conversion from ${clarifyUnitName(inputUnit,0.0)} to ${clarifyTempUnitName(outputUnit,0.0)} is impossible")
        } else {
            println("Conversion from ${clarifyUnitName(inputUnit,0.0)} to ??? is impossible")
        }
    } else if (isTemp(inputUnit)) {
        if (isMass(outputUnit) || isLength(outputUnit)) {
            println("Conversion from ${clarifyTempUnitName(inputUnit,0.0)} to ${clarifyUnitName(outputUnit,0.0)} is impossible")
        } else {
            println("Conversion from ${clarifyTempUnitName(inputUnit,0.0)} to ??? is impossible")
        }
    } else if (!isAny(inputUnit)) {
        if (isLength(outputUnit) || isMass(outputUnit)) {
            println("Conversion from ??? to ${clarifyUnitName(outputUnit,0.0)} is impossible")
        } else if (isTemp(outputUnit)) {
            println("Conversion from ??? to ${clarifyTempUnitName(outputUnit,0.0)} is impossible")
        } else {
            println("Conversion from ??? to ??? is impossible")
        }
    }
}