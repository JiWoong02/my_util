@echo off

:: mvn 클린, 인스톨
echo mvn clean install...
call mvn -f "D:\eGovFrameDev-4.2.0-64bit\workspace\constructbim\pom.xml" clean install

:: mvn clean install 종료 후, 제대로 실행되었는지 확인
IF %ERRORLEVEL% NEQ 0 (
    echo "mvn build failed, exiting..."
    exit /b %ERRORLEVEL%
)


:: 빌드된 폴더로 이동, 파일 수정
cd /d D:\eGovFrameDev-4.2.0-64bit\workspace\constructbim\target && ren constructbim-1.0.0.war ROOT.war 

echo "File renamed successfully"


:: .ssh키를 사용해 서버에 접속하여 기존 ROOT.war 백업생성
echo connect SSH...
ssh -i "D:\JWssh\bim\id_ed25519" root@112.217.190.205 "cd /home/apache-tomcat-9.0.95/webapps && mv ROOT.war ROOT_%DATE:~0,4%%DATE:~5,2%%DATE:~8,2%_%TIME:~0,2%%TIME:~3,2%%TIME:~6,2%.backup"


:: 종료 메시지
echo success make backup

:: .ssh키를 사용해 war파일 서버로 전송
echo send war to server ....
scp -i "D:\JWssh\bim\id_ed25519" "D:\eGovFrameDev-4.2.0-64bit\workspace\constructbim\target\ROOT.war" root@112.217.190.205:/home/apache-tomcat-9.0.95/webapps

echo send success

ssh -i "D:\JWssh\bim\id_ed25519" root@112.217.190.205 "systemctl restart tomcat"

echo success deploy

pause
