# Build the PDF version from the LaTeX spurces

LATEX_CLASS=aldast.cls
LATEX_MAIN=main.tex

pdf: ${LATEX_CLASS} ${LATEX_MAIN}
	pdflatex ${LATEX_MAIN} -shell-escape
	bibtex $(basename ${LATEX_MAIN})
	pdflatex ${LATEX_MAIN}
