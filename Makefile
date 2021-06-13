# Makefile for the aldast course

STATIC = static


.PHONY: all publish publish_no_init

all: publish

publish: publish.el
	@echo "Publishing... with current Emacs configurations."
	emacs --batch -f toggle-debug-on-error --load publish.el --funcall org-publish-all


clean:
	@echo "Cleaning up.."
	@rm -rvf *.elc
	@rm -rvf ${STATIC}
	@rm -rvf ~/.org-timestamps/*
