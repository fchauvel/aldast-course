        .entry main
        .data
        end     WORD    100
        sum     WORD    0
        byTwo   WORD    2

        .code
main:   LOAD    1
        ADD     end
        MUL     end
        DIV     byTwo
        STORE   sum
        PRINT   sum
        HALT
