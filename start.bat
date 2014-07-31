set root=%HOMEDRIVE%%HOMEPATH%\prod\WebNotes
set PORT=3000

cd /d %root%
.\manage.py syncdb
.\manage.py runserver %PORT%