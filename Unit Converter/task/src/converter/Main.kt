package converter

val centimetersToMeters = Pair(145.0, 1.45)
val milesToKilometers = Pair(2.0, 3.2187)
val inchesToMillimeters = Pair(5.5, 139.7)
val celsiusToFahrenheit = Pair(12.0, 53.6)
val poundsToKilograms = Pair(3.0,1.360776)

fun main() {
    println("Enter a number and a measure:")
    val input = readln().split(" ")
    val amount = input[0].toDouble()
    val unit = input[1].lowercase()

    val convertedAmount: Double = when (unit) {
        "cm","centimeters" -> convert(centimetersToMeters, amount)
        "m","meters" -> convert(centimetersToMeters, amount, true)
        "mi","miles" -> convert(milesToKilometers, amount)
        "km","kilometers" -> convert(milesToKilometers, amount)
        "in","inches" -> convert(inchesToMillimeters, amount)
        "mm","millimeters" -> convert(inchesToMillimeters, amount, true)
        "c","celsius" -> convert(celsiusToFahrenheit, amount)
        "f","fahrenheit" -> convert(celsiusToFahrenheit, amount, true)
        "lb","pounds" -> convert(poundsToKilograms, amount)
        "kg","kilograms" -> convert(poundsToKilograms, amount, true)
        else -> 0.0
    }

}

fun convert(pair: Pair<Double, Double>, amount: Double, firstToSecond: Boolean = true): Double {
    val factor = if (firstToSecond) pair.first/pair.second else pair.second/pair.first
    return amount*factor
}