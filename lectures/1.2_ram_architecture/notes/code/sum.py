def add(x, y):
    grid = setupGrid(x, y)
    column = rightMostColumn(grid)
    while not isEmpty(column):
        carry, xd, yd = read(grid, column)
        s, c = carry + xd + yd
        write(grid, column, 4, s)
        column = nextOnLeft(grid, column)
        write(grid, column, 1, c)
    return lastRow(grid)

def setupGrid(x, y): pass
def rightMostColumn(grid): pass
def isEmpty(grid, column): pass
def read(column, grid): pass
def write(grid, column, rowIndex, value): pass
def nextOnLeft(grid, column): pass
def lastRow(grid): pass
