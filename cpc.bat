@echo off
set var=task
copy .\build\WEB-INF\classes\%var%\*.class .\WEB-INf\classes\%var%\.
goto done
:done
