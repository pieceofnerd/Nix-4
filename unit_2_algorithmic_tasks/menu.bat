chcp 1251
@echo off
echo Please enter the number of task that you want to fulfil. ^If you want to ^exit PRESS 0;
echo 1 - TASK_1
echo 2 - TASK_2
echo 3 - TASK_3
echo 0 - EXIT
set /p Input=Enter:


if "%Input%"=="1" ( goto 1 )
if "%Input%"=="2" ( goto 2 )
if "%Input%"=="3" ( goto 3 )
if "%Input%"=="0" ( goto 0 ) else ( goto 4 )


:1
cd task_1
call run_task_1.bat
cd ..
call menu.bat

:2
cd task_2
call run_task_2.bat
cd ..
call menu.bat

:3
cd task_3
call run_task_3.bat
cd ..
call menu.bat

:0
echo Thank you ^for using our program
exit

:4
echo ----------------------------------
echo PLEASE INPUT A CORRECT NUMBER
echo ----------------------------------
call menu.bat