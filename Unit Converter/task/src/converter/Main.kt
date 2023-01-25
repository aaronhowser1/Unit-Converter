package converter

fun main() {
    println("Enter a number and a measure:")
    val input = readln().split(" ")
    val amount = input[0].toDouble()
    val unit = clarifyUnitName(input[1].lowercase(),amount)

    val convertedAmount = convertToMeters(amount,unit)
    val convertedUnit = clarifyUnitName("meter",convertedAmount)

    println("$amount $unit is $convertedAmount $convertedUnit")

}

fun convertToMeters(amount: Double, unit: String): Double {
    return when (unit) {
        "m","meter","meters" -> amount
        "km","kilometer","kilometers" -> amount*1000
        "cm","centimeter","centimeters" -> amount*0.01
        "mm","millimeter","millimeters" -> amount*0.001
        "mi","mile","miles" -> amount*1609.35
        "yd","yard","yards" -> amount*0.9144
        "ft","foot","feet" -> amount*0.3048
        "in","inch","inches" -> amount*0.0254
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
        else -> ""
    }
    if (amount != 1.0) {
        if (newUnit == "foot") {
            newUnit = "feet"
        } else {
            newUnit += 's'
        }
    }
    return newUnit
}