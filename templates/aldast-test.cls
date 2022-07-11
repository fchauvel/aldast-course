\NeedsTeXFormat{LaTeX2e}
\ProvidesClass{idata2302}[2022/03/07 NTNU IDATA 2302 Lecture Notes]

\LoadClass[a4paper,10pt,twoside]{article}

\RequirePackage{xcolor}

\RequirePackage[utf8]{inputenc}

\RequirePackage[inner=2.5cm, outer=7cm, marginparsep=0.75cm, marginparwidth=5cm]{geometry}

\RequirePackage{booktabs}

\RequirePackage{caption}
\DeclareCaptionFormat{custom}
{%
    \small\textbf{#1#2}\textit{#3}
}
\captionsetup{format=custom}

\RequirePackage{marginfix}
\RequirePackage{sidenotes}

\RequirePackage{graphicx}

\RequirePackage{algorithm2e}

\RequirePackage{hyperref}
\hypersetup{
    colorlinks=true,
    linkcolor=blue,
    filecolor=magenta,      
    urlcolor=cyan
  }

% Tikz
\RequirePackage{tikz}
\usetikzlibrary{positioning, calc, shapes.geometric, arrows.meta}  

\RequirePackage[mode=buildnew]{standalone}

\RequirePackage{numprint}
\npthousandsep{\,}

\def\documentType#1{\def\@documentType{#1}}

\def\documentNumber#1{\def\@documentNumber{#1}}

\RequirePackage{fancyhdr}
\pagestyle{fancy}
\fancyhead[LE,RO]{NTNU \textbullet\ IDATA 2302}
\fancyhead[RE,LO]{\@documentType\ \@documentNumber}

\RequirePackage{minted}
\setminted{
  xleftmargin=3em,
  numbersep=1em,
  fontsize=\small,
  baselinestretch=1,
  linenos=true,
  autogobble,
}

\RequirePackage{amsmath}
\RequirePackage{amsfonts}
\RequirePackage{latexsym}


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


\RenewDocumentCommand\sidenotetext{ o o +m }{%      
    \IfNoValueOrEmptyTF{#1}{%
        \@sidenotes@placemarginal{#2}{\textsuperscript{\thesidenote}{}~\footnotesize#3}%
        \refstepcounter{sidenote}%
    }{%
        \@sidenotes@placemarginal{#2}{\textsuperscript{#1}~#3}%
    }%
}


\RequirePackage{xcolor}
\RequirePackage[skins]{tcolorbox}

\newcounter{takeawayCount}
\setcounter{takeawayCount}{0}
\newenvironment{takeaway}
{\refstepcounter{takeawayCount}\begin{tcolorbox}[
    enhanced,
    sharp corners,
    boxrule=0pt,
    colback=red!5!white,
    colframe=red!75!black,
    borderline west = {2pt}{0pt}{red!75!black}
  ] {\color{red!75!black} {\bfseries Takeaway \thetakeawayCount\ }}}
{\end{tcolorbox}}

\definecolor{cadmiumorange}{rgb}{0.93, 0.53, 0.18}
\definecolor{tangerine}{rgb}{0.86, 0.52, 0.0}


\newcounter{exerciseCount}
\setcounter{exerciseCount}{0}
\newenvironment{exercise}[1][\unskip]
{\refstepcounter{exerciseCount}\begin{tcolorbox}[
    enhanced,
    sharp corners,
    boxrule=0pt,
    colback=tangerine!5!white,
    colframe=tangerine!75!black,
    borderline west = {3pt}{0pt}{tangerine} 
  ] \par\noindent{\color{tangerine} {\bfseries Exercise \theexerciseCount: #1}} \vspace{2mm} \\}
{\end{tcolorbox}}


%
\newif\ifShowSolution
\ShowSolutionFalse
\newcommand{\showSolution}{\ShowSolutiontrue}


\newcounter{questionCount}
\setcounter{questionCount}{0}
\newenvironment{question}
{
  \def\title#1{\def\@questionTile{#1}}
}
{

}