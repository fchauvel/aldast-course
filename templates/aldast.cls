\NeedsTeXFormat{LaTeX2e}
\ProvidesClass{idata2302}[2022/03/07 NTNU IDATA 2302 Lecture Notes]

\LoadClass[a4paper,10pt,twoside]{article}

\RequirePackage{xcolor}

\RequirePackage[utf8]{inputenc}

\RequirePackage[inner=2.5cm, outer=7cm, marginparwidth=5.5cm]{geometry}

\RequirePackage{booktabs}

\RequirePackage{sidenotes}

\RequirePackage{graphicx}

\RequirePackage{hyperref}
\hypersetup{
    colorlinks=true,
    linkcolor=blue,
    filecolor=magenta,      
    urlcolor=cyan
  }
  

\RequirePackage[skins]{tcolorbox}

\def\documentType#1{\def\@documentType{#1}}

\def\documentNumber#1{\def\@documentNumber{#1}}

\RequirePackage{fancyhdr}
\pagestyle{fancy}
\fancyhead[LE,RO]{NTNU IDATA 2302}
\fancyhead[RE,LO]{\@documentType\ \@documentNumber}


\renewcommand{\maketitle}{
  \thispagestyle{plain}
  \vspace*{2cm}
  {\par\noindent{\small NTNU \textbullet\ IDATA 2302: Algorithms \& Data Structures}} %
  \vspace{0.5cm}
  {\flushleft
    {\LARGE\bfseries \@title} \\ %
    \vspace{2mm}
    {\normalsize\@documentType\ \@documentNumber} \\
    \vspace{1mm}
    {\scriptsize Updated on \today\ by \@author}
  } \\
  \par\medskip
}

\renewenvironment{abstract}
{\par\leftskip1em\rightskip1em\noindent\footnotesize\textbf{\abstractname.}\ \ignorespaces}
{\par\bigskip}


\let\oldSidenote\sidenote
\renewcommand{\sidenote}[1]{{\oldSidenote{\raggedright\footnotesize #1}}}
