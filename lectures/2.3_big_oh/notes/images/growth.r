require(tikzDevice);

tikz('growths.tikz', standAlone = TRUE, width=3, height=3);

limits <- seq(1, 50, 0.5);

plot(limits, sapply(limits, function (n) { n }),
     col="goldenrod1",
     lty=1,
     type="l",
     xlab="Input Size",
     ylab="Efficiency",
     bty="n"
     );
lines(limits, sapply(limits, function (n) { 1 }),
      col="darkolivegreen4",
      lty=2
      );
lines(limits, sapply(limits, function (n) { log2(n) }),
      col="darkolivegreen3",
      lty=3,
      );
lines(limits, sapply(limits, function (n) { sqrt(n) }),
      col="darkolivegreen2",
      lty=4,
      );
lines(limits, sapply(limits, function (n) { n * log2(n) }),
      col="darkorange", lty=6
      );
lines(limits, sapply(limits, function (n) { n^2 }),
      col="firebrick", lty=7
      );
lines(limits, sapply(limits, function (n) { 2^n }),
      col="firebrick3",
         lty=8,
      );
lines(limits, sapply(limits, factorial),
      col="darkred",
      lty=5
      );
legend("right", inset=0.05,
       legend=c("linear", "constant", "logarithmic", "n-root", "log-linear", "polynomial", "exponential", "factorial"),
       box.lty=0,
       col=1:8,
       lty=1:8,
       cex=0.8);

dev.off();
