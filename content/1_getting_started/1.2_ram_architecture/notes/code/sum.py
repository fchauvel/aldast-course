def sum(x: str, y: str) -> str:
    result = ""
    length = max(len(x), len(y))
    index = 0
    added = 0
    carry = 0
    while index < length or carry == 1:
        added = digitAt(x, index) + digitAt(y, index) + carry
        carry = 0
        if added > 9:
            carry = 1
            added = added - 10
        result = str(added) + result
        index = index + 1
    return result

def digitAt(text, index):
    if index >= len(text):
        return 0
    back = len(text) - 1 - index
    return int(text[back])
