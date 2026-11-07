# Institutional report source

`main.tex` is the completed report based on the supplied `CY_devops_report.zip` template. It preserves the institutional banner, certificate wording, component headings, evaluator tables, font sizes and page order. The student details are Sourav Kumar Sahu, USN 1MS24CY063. The title uses the template's fifth semester and course code 24CYAEC59.

The student instruction pages and boxes have been removed. Both components contain the actual repository code, environment versions, explanations of the observed results, and eleven evidence images. Signatures and evaluator marks are intentionally blank.

The final PDF is [CY_devops_report_Sourav_Kumar_Sahu.pdf](../CY_devops_report_Sourav_Kumar_Sahu.pdf). The earlier standalone component reports are retained as supporting write-ups; this combined report follows the institutional format.

To compile locally with a standard TeX Live installation, run from this directory:

```sh
pdflatex -interaction=nonstopmode -halt-on-error main.tex
pdflatex -interaction=nonstopmode -halt-on-error main.tex
```

Images are found in `../evidence/`. For Overleaf, upload `main.tex`, `ramaiah_logo.jpg` and the eleven PNG files from that directory to the project root. The packaged Overleaf ZIP contains that complete set of files.
