def convert(array):
    if len(array) == 0:
        return []
    if len(array) == 1:
        return [1, array[0]]
    result = []
    run_length = 1
    run_value = array[0]
    current = 1
    while current < len(array):
        if array[current] == array[current-1]:
            run_length += 1
        else:
            result.append(run_length)
            result.append(run_value)
            run_length = 1
            run_value = array[current]
        current += 1
    result.append(run_length)
    result.append(run_value)
    return result


print(convert([]))
print(convert([1]))
print (convert([1, 1, 1, 1]))
print (convert([1, 1, 2, 2]))
print (convert([1, 1, 2, 2, 3, 3, 3, 3, 3, 3]))
print (convert([1, 1, 5, 2, 2, 5, 3, 3, 3, 3, 3, 3]))
