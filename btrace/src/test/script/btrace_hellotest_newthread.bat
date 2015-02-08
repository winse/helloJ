echo off

set PATH=%PATH%;C:\cygwin\bin;C:\cygwin\usr\local\bin

set BTRACE_HOME=E:\local\opt\btrace-bin
set CUR_ROOT=%cd%\..\..\..
set SCRIPT=%CUR_ROOT%\src\main\java\com\github\winse\btrace\HelloWorld.java
set SCRIPT=%CUR_ROOT%\target\classes\com\github\winse\btrace\HelloWorld.class

jps -m  | findstr HelloTest | gawk '{print $1}' | xargs -I {} %BTRACE_HOME%\bin\btrace.bat {} %SCRIPT%

pause

