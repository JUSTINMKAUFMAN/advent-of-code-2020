struct Rule {
    let char: String
    let min: Int
    let max: Int
}

let array: [(ValidationRule, String)] = text.components(separatedBy: "\n").map { entry in
    let parse = entry.components(separatedBy: " ")
    let ints = parsed[0].components(separatedBy: "-").map { Int($0)! }
    return (
        ValidationRule(char: parse[1].dropLast().first!, min: ints[0], max: ints[1]),
        parse[2]
    )
}

let resultA = array.reduce(0) { sum, entry in
    sum + (entry.0.isValidA(entry.1) ? 1 : 0)
}

let resultB = array.reduce(0) { sum, entry in
    sum + (entry.0.isValidB(entry.1) ? 1 : 0)
}

print("[A] \(resultA) passwords are valid")
print("[B] \(resultB) passwords are valid")
