rem *******************************Code Start*****************************
@echo off
set "Ymd=%date:~,4%%date:~5,2%%date:~8,2%%time:~,2%%time:~3,2%"
set "Ymd=%Ymd: =%"
set "filename=matchfee_%Ymd%"
"D:\mysql-5.6.13-win32\bin\mysqldump" --default-character-set=utf8 --opt -R -u root --password=root matchfee >D:\backup\%filename%.sql --hex-blob
C:\7-Zip\7z a D:\backup\%filename%.7z D:\backup\%filename%.sql
del D:\backup\%filename%.sql

copy D:\backup\%filename%.7z z:\matchfee_backup\db\
xcopy D:\matchfee z:\matchfee_backup\attach /s/e/i/y

@echo on
rem *******************************Backup End*****************************

@echo off
rem �Զ�ɾ��2��֮ǰ���ݵ�sql�ļ�
rem /p ָʾɨ��·��
rem /d -14ָʾɨ������ڣ�-14��ʾ14��ǰ(�����������ָ�ļ�����޸�����)
rem /m ָʾɨ���ļ�����: *.sql��ʾ����sql�ļ�
rem /c ������Ҫִ�е��������ݣ���˫������������cmd /c ������������ݣ�@path��ɨ�赽�İ����ļ�����ȫ·��
forfiles /p D:\backup /d -14 /m *.7z /c "cmd /c del /f @path"

@echo on
rem *******************************Clean End*****************************
