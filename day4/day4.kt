fun main() {
    val passports = input
        .split("\n\n")
        .map {
            it.replace("\n", " ")
                .split(" ")
                .map { val pair = it.split(":"); Pair(pair[0], pair[1]) }
                .toMap()
        }

    val requiredFields = listOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid").sorted()

    val validators: Map<String, ((String) -> (Boolean))> = mapOf(
        "byr" to { str ->
            val int = str.toIntOrNull()
            if (int == null) false
            else (int in 1920..2002)
        },
        "iyr" to { str ->
            val int = str.toIntOrNull()
            if (int == null) false
            else (int in 2010..2020)
        },
        "eyr" to { str ->
            val int = str.toIntOrNull()
            if (int == null) false
            else (int in 2020..2030)
        },
        "hgt" to { str ->
            if (str.endsWith("cm")) {
                val int = str.replace("cm", "").toIntOrNull()
                if (int == null) false
                else (int in 150..193)
            } else if (str.endsWith("in")) {
                val int = str.replace("in", "").toIntOrNull()
                if (int == null) false
                else (int in 59..76)
            } else false
        },
        "hcl" to { str ->
            if (!str.startsWith("#")) false else {
                val valids = listOf('a', 'b', 'c', 'd', 'e', 'f', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9')
                var isValid = true

                for (char in str.removePrefix("#").toList()) {
                    if (!valids.contains(char)) {
                        isValid = false
                        break
                    }
                }
                isValid
            }
        },
        "ecl" to { str ->
            listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth").contains(str)
        },
        "pid" to { str ->
            str.count() == 9 && str.filter { it.isDigit() }.count() == 9
        },
        "cid" to { _ -> true }
    )

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
