import util.Input
import util.readInputFile
import util.splitLast

fun day4() {
    val input = readInputFile(4)
    val input2 = Input("""
        ecl:gry pid:860033327 eyr:2020 hcl:#fffffd
        byr:1937 iyr:2017 cid:147 hgt:183cm

        iyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884
        hcl:#cfa07d byr:1929

        hcl:#ae17e1 iyr:2013
        eyr:2024
        ecl:brn pid:760753108 byr:1931
        hgt:179cm

        hcl:#cfa07d eyr:2025 pid:166559648
        iyr:2011 ecl:brn hgt:59in
    """.trimIndent())

    val passports = parseInput(input)

    println(passports)

    val answer = passports.count(Passport::valid)
    println("The answer to part 1 is ... $answer")

    val answer2 = -1
    println("The answer to part 2 is ... $answer2")
}


// Data

private data class Passport(
    val birthYear: String?,
    val issueYear: String?,
    val expirationYear: String?,
    val height: String?,
    val hairColor: String?,
    val eyeColor: String?,
    val passportId: String?,
    val countryId: String?,
) {
    companion object {
        fun parse(str: String): Passport = str
            .split("\n")
            .flatMap { it.split(" ") }
            .map { it.splitLast(":") }
            .toMap()
            .let { parts ->
                Passport(
                    birthYear = parts["byr"],
                    issueYear = parts["iyr"],
                    expirationYear = parts["eyr"],
                    height = parts["hgt"],
                    hairColor = parts["hcl"],
                    eyeColor = parts["ecl"],
                    passportId = parts["pid"],
                    countryId = parts["cid"],
                )
            }
    }

    val valid: Boolean = birthYear != null &&
            issueYear != null &&
            expirationYear != null &&
            height != null &&
            hairColor != null &&
            eyeColor != null &&
            passportId != null
}

private fun parseInput(input: Input): List<Passport> {
    return input.inputStr.split("\n\n")
        .map(Passport.Companion::parse)
}