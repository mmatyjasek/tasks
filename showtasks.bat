call runcrud.bat
if "%ERRORLEVEL%" == "0" goto startbrowser
echo.
echo compliation error - breaking work
goto fail

:startbrowser
start http://localhost:8080/crud/v1/task/getTasks
if "%ERRORLEVEL%" == "0" goto end
echo Cannot start page
goto fail

:fail
echo.
echo There were errors in showtasks

:end
echo.
echo Work is finished(showtasks).