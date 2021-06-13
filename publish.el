;; org-publish configuration


(require 'package)
(add-to-list 'package-archives
             '("melpa" . "https://melpa.org/packages/") t)

(unless package-archive-contents
  (package-initialize)
  ;;(package-refresh-contents))
)
(use-package org-ref
  :ensure t)

(org-babel-do-load-languages
 'org-babel-load-languages
 '((python . t)
   (C . t)
   (R . t)
   (shell . t)
   (screen . t)))

(setq python-indent-guess-indent-offset t)
(setq python-indent-guess-indent-offset-verbose nil)

(require 'org)
(require 'org-ref)
(require 'ox-html)
(require 'htmlize)
(require 'ox-publish)

(setq org-publish-project-alist
      '(
        ("org-content"
         :base-directory "./content"
         :base-extension "org"
         :publishing-directory "site/"
         :recursive t
         :publishing-function org-html-publish-to-html
         :headline-levels 4             ; Just the default for this project.
         :auto-preamble t
         :sitemap-filename "index.org"
         :author "Franck Chauvel"
         :email "franck.chauvel@gmail.com"
         :html-link-home  "index.html"
         :with-creator t
         :with-properties t
         :with-tags t
         :html-postamble "
      <footer>

         <p>Written by F. Chauvel</p>
         <p>Licensed under <a rel=\"license\" href=\"http://creativecommons.org/licenses/by-sa/4.0/\">CC-BY-SA 4.0</a></p>
         <p>Created with %c</p>
         <p>Last updated on %C<p/>
      </footer>"
         )
        ("org-assets"
         :base-directory "./assets"
         :base-extension "css\\|js\\|png\\|jpg\\|gif\\|pdf\\|mp3\\|ogg\\|swf"
         :publishing-directory "site/assets"
         :recursive t
         :publishing-function org-publish-attachment
         )
        ("org" :components ("org-content" "org-assets"))
        ))

(setq org-html-htmlize-output-type 'css
      org-html-validation-link nil
      org-confirm-babel-evaluate nil)
