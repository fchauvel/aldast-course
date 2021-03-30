function findPathTo(domElement) {
    path = [];
    if (domElement.nodeName.toLowerCase() == "section") {
        path.push(domElement.getAttribute("name"));
    }
    if (domElement.parentNode != null) {
        path = path.concat(findPathTo(domElement.parentNode));
    }
    return path;
};

function formatPath(path, prefixes) {
    text = "IDATA2302";
    for (const eachPrefix of prefixes) {
        text += ` / ${eachPrefix}`;
    }
    for (const [index, element] of path.reverse().entries()) {
        if (index < path.length - 1) {
            text += ` / ${element}`;
        } else {
            text += ` / <em>${element}</em>`;
        }
    }
    return text;
}

function updateBreadcrumbs() {
    const currentSlide = Reveal.getCurrentSlide();
    const path = findPathTo(currentSlide);
    const formattedPath = formatPath(path,
                                     ["Week 1",  "Lecture 1"]);
    document
        .getElementById("fc-breadcrumbs")
        .innerHTML = formattedPath;
}

function updateSlideNumber() {
    const slideCount = Reveal.getTotalSlides();
    const currentSlide = 1 + Reveal.getSlidePastCount();
    const text = `<em>${currentSlide}</em> / ${slideCount}`;
    document
        .getElementById("fc-slidenumber")
        .innerHTML = text;
}

Reveal.addEventListener('slidechanged', (event) => {
    updateBreadcrumbs();
    updateSlideNumber();
});
