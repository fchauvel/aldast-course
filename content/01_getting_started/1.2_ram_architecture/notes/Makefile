# Build the PDF version from the LaTeX spurces

INKSCAPE=/Applications/Inkscape.app/Contents/MacOS/inkscape

LATEX_CLASS=aldast.cls
LATEX_MAIN=main.tex

SVG_IMAGES=$(shell find ./images -type f -name "*.svg")
PDF_IMAGES=$(patsubst %.svg,%.pdf,$(SVG_IMAGES))

%.pdf: %.svg
	mkdir -p images
	${INKSCAPE} $< --batch-process --export-type=pdf --export-filename=$@

pdf: ${LATEX_CLASS} ${LATEX_MAIN} ${PDF_IMAGES}
	echo ${PDF_IMAGES}
	pdflatex --shell-escape ${LATEX_MAIN}
	bibtex $(basename ${LATEX_MAIN})
	pdflatex --shell-escape ${LATEX_MAIN}
	pdflatex --shell-escape ${LATEX_MAIN}

clean:
	rm *.aux *.bbl *.blg *.out *.log
	rm ${PDF_IMAGES}
	rm *.pdf
