set TETDM_HOME=%~dp0
cd /d %TETDM_HOME%
java -Xmx1024m -jar TETDM.jar %1
pause on