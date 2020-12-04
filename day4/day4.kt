fun main() {
    val passports = input
        .split("\n\n")
        .map {
            it.replace("\n", " ")
                .split(" ")
                .map { val pair = it.split(":"); Pair(pair[0], pair[1]) }
                .toMap()
        }



    val validators: Map<String, ((String) -> (Boolean))> = mapOf(
        "byr" to { (it.toIntOrNull() in 1920..2002) },
        "iyr" to { (it.toIntOrNull() in 2010..2020) },
        "eyr" to { (it.toIntOrNull() 2020..2030) },
        "ecl" to { listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth").contains(it) },
        "pid" to { it.count() == 9 && it.filter { it.isDigit() }.count() == 9 },
        "cid" to { _ -> true },
        "hgt" to {
            if (it.endsWith("cm")) (it.replace("cm", "").toIntOrNull() in 150..193)
            else if (it.endsWith("in")) (it.replace("in", "").toIntOrNull() in 59..76)
            else false
        },
        "hcl" to {
            if (!it.startsWith("#")) false else {
                val valids = listOf('a','b','c','d','e','f','0','1','2','3','4','5','6','7','8','9')
                var isValid = true
                for (char in it.removePrefix("#").toList()) { if (!valids.contains(char)) { isValid = false; break } }
                isValid
            }
        }
    )

    val requiredFields: List<String> get() = validators.keys.toList().sorted()

    val validA = passports.fold(0) { sum, passport ->
        sum + if (passport.keys.toList().filter { it != "cid" }.sorted() == requiredFields) 1 else 0
    }

    val validB = passports.fold(0) { sum, passport ->
        val keys = passport.keys.toList().filter { it != "cid" }.sorted()
        sum + if ((keys == requiredFields) && (!keys.map { validators[it]?.invoke(passport[it]!!) }.contains(false))) 1 else 0
    }

    println("Valid Passports A:" + validA)
    println("Valid Passports B:" + validB)
}
