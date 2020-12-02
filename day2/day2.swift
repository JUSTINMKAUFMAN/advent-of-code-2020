struct ValidationRule {
    let char: Character
    let min: Int
    let max: Int

    func isValidA(_ password: String) -> Bool {
        let count = password.filter { $0 == char }.count
        return count >= min && count <= max
    }
    
    func isValidB(_ password: String) -> Bool {
        let a = password[safe: min - 1] == char
        let b = password[safe: max - 1] == char
        return (a && !b) || (!a && b)
    }
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
