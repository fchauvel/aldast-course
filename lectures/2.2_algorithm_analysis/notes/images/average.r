require(tikzDevice);

## pdf(file = "average.pdf",
##     width = 5,
##     height = 5);


tikz('average.tikz', standAlone = TRUE, width=3, height=3);

time <- function(n) { 5*n + 3}

sizes <- seq(1, 50);
plot(sizes,
     sapply(sizes, time),
     type="l",
     col="red",
     xlab="Length of input sequence",
     ylab="Runtime");

grid(nx = NULL,
     ny = NULL,
     lty = 2,      # Grid line type
     col = "gray", # Grid line color
     lwd = 1);

dev.off();
