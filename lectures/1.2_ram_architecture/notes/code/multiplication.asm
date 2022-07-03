        .data
        x       WORD    0
        y       WORD    0
        product WORD    0
        counter WORD    0

        .code
main:   READ    x               ;
        READ    y               ;
loop:   LOAD    0
        ADD     counter
        SUB     y
        JUMP    done            ; while (counter < y)
        LOAD    0               ; do
        ADD     product         ;
        ADD     x               ;
        STORE   product         ;    product <- product + x
        LOAD    1               ;
        ADD     counter         ;
        STORE   counter         ;    counter <- counter + 1
        LOAD    0               ;
        JUMP    loop            ; done
done:   PRINT   product
        HALT
