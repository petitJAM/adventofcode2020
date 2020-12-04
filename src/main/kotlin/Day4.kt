import util.Input
import util.printList
import util.readInputFile
import util.splitLast

fun day4() {
    val input = readInputFile(4)
    val input2 = Input(
        """
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
    """.trimIndent()
    )

    val input3 = Input(
        """
        eyr:1972 cid:100
        hcl:#18171d ecl:amb hgt:170 pid:186cm iyr:2018 byr:1926

        iyr:2019
        hcl:#602927 eyr:1967 hgt:170cm
        ecl:grn pid:012533040 byr:1946

        hcl:dab227 iyr:2012
        ecl:brn hgt:182cm pid:021572410 eyr:2020 byr:1992 cid:277

        hgt:59cm ecl:zzz
        eyr:2038 hcl:74454a iyr:2023
        pid:3556412378 byr:2007

        pid:087499704 hgt:74in ecl:grn iyr:2012 eyr:2030 byr:1980
        hcl:#623a2f

        eyr:2029 ecl:blu cid:129 byr:1989
        iyr:2014 pid:896056539 hcl:#a97842 hgt:165cm

        hcl:#888785
        hgt:164cm byr:2001 iyr:2015 cid:88
        pid:545766238 ecl:hzl
        eyr:2022

        iyr:2010 hgt:158cm hcl:#b6652a ecl:blu byr:1944 eyr:2021 pid:093154719
    """.trimIndent()
    )

    val passports = parseInput(input)

//    printList(passports)

//    passports.forEach {
//        println("${it.invalidReason} -- $it")
//    }

    val answer = passports.count(Passport::isValid)
    println("The answer to part 1 is ... $answer")

    val answer2 = passports.count(Passport::isStrictValid)
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

        private val VALID_EYE_COLORS = listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
    }

    val isStrictValid: Boolean
        get() = isBirthYearValid &&
                isIssueYearValid &&
                isExpirationYearValid &&
                isHeightValid &&
                isHairColorValid &&
                isEyeColorValid &&
                isPassportIdValid

    val isValid: Boolean
        get() = birthYear != null &&
                issueYear != null &&
                expirationYear != null &&
                height != null &&
                hairColor != null &&
                eyeColor != null &&
                passportId != null

    val invalidReason: String
        get() {
            var reason = ""
            if (!isBirthYearValid) reason += "BirthYear "
            if (!isIssueYearValid) reason += "IssueYear "
            if (!isExpirationYearValid) reason += "ExpirationYear "
            if (!isHeightValid) reason += "Height "
            if (!isHairColorValid) reason += "HairColor "
            if (!isEyeColorValid) reason += "EyeColor "
            if (!isPassportIdValid) reason += "PassportId "

            return reason.trim()
        }

    private val isBirthYearValid: Boolean
        get() = birthYear != null &&
                birthYear.toIntOrNull() in 1920..2002

    private val isIssueYearValid: Boolean
        get() = issueYear != null &&
                issueYear.toIntOrNull() in 2010..2020

    private val isExpirationYearValid: Boolean
        get() = expirationYear != null &&
                expirationYear.toIntOrNull() in 2020..2030

    private val isHeightValid: Boolean
        get() = height != null &&
                height.let {
                    Regex("^(\\d+)(cm|in)$").find(it)
                        ?.groupValues
                        ?.let { values ->
                            Pair(values[1], values[2])
                        }
                        ?.let { (measure, unit) ->
                            when (unit) {
                                "cm" -> measure.toIntOrNull() in 150..193
                                "in" -> measure.toIntOrNull() in 59..76
                                else -> false
                            }
                        }
                        ?: false
                }

    private val isHairColorValid: Boolean
        get() = hairColor != null &&
                hairColor.let {
                    Regex("^#[a-f0-9]{6}$").matches(it)
                }

    private val isEyeColorValid: Boolean
        get() = eyeColor != null &&
                eyeColor in VALID_EYE_COLORS

    private val isPassportIdValid: Boolean
        get() = passportId != null &&
                passportId.let {
                    Regex("^\\d{9}$").matches(it)
                }
}

private fun parseInput(input: Input): List<Passport> {
    return input.inputStr.split("\n\n")
        .map(Passport.Companion::parse)
}