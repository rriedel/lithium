@echo off

SET "MAIN_PRJ_DIR=%~dp0"

echo [INFO] Setting up backend database container
cd "%MAIN_PRJ_DIR%\backend\src\setup"
docker-compose -p lithium_backend up -d || goto :ERROR
