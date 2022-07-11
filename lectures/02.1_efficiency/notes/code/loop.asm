        .entry main
        .data
        end     WORD    100
        sum     WORD    0
        any     WORD    0

        .code
main:   LOAD 0
        ADD any
        SUB end
        JUMP done
        LOAD 0
        ADD sum
        ADD any
        STORE sum
        LOAD 1
        ADD any
        STORE any
        LOAD 0
        JUMP main
done:   PRINT sum
        HALT
