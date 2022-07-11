require(tikzDevice);

tikz('bounds.tikz', standAlone = TRUE, width=3, height=3);

model <- function (n) { (n^2 + 9*n + 6) / 2}
upper <- function (n) {n^2}
lower <- function (n) {n^2/2}

sizes <- seq(1, 20);

plot(sizes, sapply(sizes, model),
     col="blue",
     type="l",
     xlab="Input size $n$",
     ylab="Efficiency (runtime)");

lines(sizes, sapply(sizes, upper),
      col="blue",
      type="l",
      lty=2);

lines(sizes, sapply(sizes, lower),
      col="blue",
      type="l",
      lty=4);

grid(nx = NULL,
     ny = NULL,
     lty = 3,      # Grid line type
     col = "gray", # Grid line color
     lwd = 1);


marker <- 1/2 * (9 + sqrt(105));
segments(x0=marker, y0=0, x1=marker, y1=upper(marker), col="red");
points(x=marker, y=upper(marker), col="red", pch=18);
#points(x=marker, y=online(marker), col="darkgreen", pch=18);

text(marker, 50,
     "$n_0 = \\frac{9 + \\sqrt{105}}{2}$",
     col="red",
     pos=4
     );

legend(
    "topleft",
    inset=0.05,
    legend=c("model", "upper: $n^2$", "lower: $\\frac{n^2}{2}$"),
    box.lty=0,
    col=c("blue", "blue", "blue"),
    lty=c(1,2,4)
);


dev.off();
