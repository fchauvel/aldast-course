def selection_sort(array)
  for index in 0 .. array.length-2
     minimum = find_minimum(array, index)
     swap(array, index, minimum)
  end
end

def find_minimum(array, start)
  minimum = start
  for index in start+1 .. array.length-1
    if array[index] < array[minimum]
      minimum = index
    end
  end
  return minimum
end



def swap(array, left, right)
   tmp = array[right]
   array[right] = array[left]
   array[left] = tmp
end
