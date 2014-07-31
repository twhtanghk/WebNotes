#!/bin/sh

root=%HOMEPATH%/prod/WebNotes

set PORT=3000

cd %root%

.\manage.py syncdb
.\manage.py runserver %PORT%