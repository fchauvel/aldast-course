require(tikzDevice);

## pdf(file = "count-even.pdf",
##     width = 5,
##     height = 5);




worst <- function(n) { 7*n + 3}
average <- function(n, k) { 6*n + 3 }
best <- function(n) { 5*n + 3}

sizes <- seq(1, 50);

tikz('count-even.tikz', standAlone = TRUE, width=3, height=3);

plot(sizes,
     sapply(sizes, worst),
     type="l",
     col="red",
     xlab="Length of input $n$",
     ylab="$time(n)$");
lines(sizes, sapply(sizes, best), col="darkgreen");
legend("topleft", inset=0.05,
       legend=c("worse case", "best case"),
       box.lty=0,
       col=c("darkred", "darkgreen"),
       pch=c(NA, NA),
       lty=c(1, 1),
       box.col = "white")
dev.off();


tikz('count-even-full.tikz', standAlone = TRUE, width=5, height=5);

plot(sizes,
     sapply(sizes, worst),
     type="l",
     col="red",
     xlab="Length of input $n$",
     ylab="$time(n, K)$");
lines(sizes, sapply(sizes, best), col="darkgreen");
lines(sizes, sapply(sizes, average), col="orange");

grid(nx = NULL,
     ny = NULL,
     lty = 2,      # Grid line type
     col = "gray", # Grid line color
     lwd = 1);

runtime <- function(n, K) { 5*n + 2*K + 3}

sizes <- runif(200, 0, 50);

points(sizes, sapply(sizes, function(s) {runtime(s, runif(1, 0, s))}),
       pch=4,
       col="cyan3");

legend("topleft", inset=0.05,
       legend=c("worse case $K=n$", "best case $K=0$", "average case $K\\approx\\frac{n}{2}$", "random samples"),
       box.lty=0,
       col=c("darkred", "darkgreen", "orange", "cyan3"),
       pch=c(NA, NA, NA, 4),
       lty=c(1, 1, 1, NA),
       box.col = "white")

dev.off();
