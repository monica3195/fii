// CSSO_HW1.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"
#include <Windows.h>
#include <iostream>
#include <vector>

#define BUFFER_LENGTH 255
#define MAX_KEY_VALUE_SIZE 16383

struct KeyValue {
	DWORD keyType;
	LPBYTE keyValue;
	LPSTR keyName;
	KeyValue(LPSTR kN, DWORD kT, LPBYTE kV) :keyName(kN), keyType(kT), keyValue(kV) {}
	KeyValue(LPSTR kN, DWORD kT) : keyName(kN), keyType(kT) {}

	void printKeyValue() {
		_tprintf(TEXT("\t Name %s, type %d, string value %s \n"), keyName, keyType, keyValue);
	}
};

class DumpKey {

private :
	LPTSTR _keyName = NULL;
	HKEY _keyHandler;
	HKEY _parentKey = HKEY_CURRENT_USER;

	//InfoKey
	DWORD counterSubKeys = 0;
	TCHAR keyName[BUFFER_LENGTH];
	DWORD keyNameC;
	DWORD keyValuesCounter;
	DWORD dwMaxValueLen;
	DWORD dwMaxValueNameLen;
public:
	std::vector<KeyValue> keyValues;
	std::vector<DumpKey*> vectorSubKeys;	

	DumpKey() {}

	DumpKey(const char* keyNameP) {
		_keyName = new char(*keyNameP);
	}

	DumpKey(const char* keyNameP, HKEY parentKey) {
		_keyName = (char*)keyNameP;
		_parentKey = parentKey;
	}

	LPTSTR getRegName() {
		return _keyName;
	}
		

	LONG openKey() {		

		   DWORD retCode = RegOpenKeyEx(
			_parentKey,			
			_T(_keyName), //TODO ? LPCTSTR Var doesn't work why ? 
			0,
			KEY_READ,
			&_keyHandler);

			return retCode;
	}

	LONG  closeKey() {
		return RegCloseKey(_keyHandler);
	}

	LONG readAndAddKeyValue(HKEY keyHandler, DWORD keyIndex) {

		LPTSTR lptcharValueName = new TCHAR[dwMaxValueNameLen+1];
		LPBYTE lpByteData = new BYTE[dwMaxValueLen];

		DWORD dwCounterCharValueName = dwMaxValueNameLen+1;
		DWORD dwCounterValueRead = dwMaxValueLen;
		DWORD dwKeyType;

		DWORD retCode = RegEnumValue(keyHandler,
			keyIndex,
			lptcharValueName,
			&dwCounterCharValueName,
			NULL,
			&dwKeyType,
			lpByteData,
			&dwCounterValueRead
			);
		KeyValue crKeyValue = KeyValue(lptcharValueName, dwKeyType, lpByteData);
		keyValues.push_back(crKeyValue);
		return retCode;
	}

	LONG fillInfoKey() {
			DWORD retCode = RegQueryInfoKey(
			_keyHandler,
			keyName,
			&keyNameC,
			NULL,
			&counterSubKeys,
			NULL,
			NULL,
			&keyValuesCounter,
			&dwMaxValueNameLen,
			&dwMaxValueLen,
			NULL,
			NULL
			);
						 
			for (unsigned int i = 0; i < counterSubKeys;) { //deal with new subkey
				
				TCHAR* tcharSubKeyName = new TCHAR[BUFFER_LENGTH];
				tcharSubKeyName[0] = '\0';
				DWORD dwSubKeyNameRead = BUFFER_LENGTH;
				DWORD dwSubKeyIndex = i;
				i++;
				
				retCode |= 
					RegEnumKeyEx(_keyHandler,
					dwSubKeyIndex,
					tcharSubKeyName,
					&dwSubKeyNameRead,
					NULL,
					NULL,
					NULL,
					NULL
					);
				DumpKey* newKey = new DumpKey(tcharSubKeyName, _keyHandler);
				printf("Check subKey %s \n", tcharSubKeyName);
				retCode |= newKey->openKey();
				retCode |= newKey->fillInfoKey();
				retCode |= newKey->closeKey();
				vectorSubKeys.push_back(newKey);
			}
		
			for (DWORD dwKeyIndex = 0; dwKeyIndex < keyValuesCounter; dwKeyIndex++) { //get key values
				retCode |= readAndAddKeyValue(_keyHandler, dwKeyIndex);
			}			

		return retCode;
	}
	void printKeyInfo() {
		_tprintf(TEXT("Key name : %s, values counter : %d \n"), _keyName, keyValuesCounter);
		_tprintf(TEXT("Key max value length %d bytes\n"), dwMaxValueLen);
		_tprintf(TEXT("Key max name value length %d bytes\n"), dwMaxValueNameLen);
		for (DWORD i = 0; i < keyValues.size(); i++) {			
			keyValues.at(i).printKeyValue();
		}
		for (DWORD i = 0; i < vectorSubKeys.size(); i++) {
			vectorSubKeys.at(i)->printKeyInfo();
		}
		_tprintf(TEXT("\n"));
	}

	void deallocateMemory() {} //TODO: 
};

class WriteRegistersOnDisk {
private:
	DumpKey dumpKey;
	LPTSTR	diskPath;
public:
	WriteRegistersOnDisk(DumpKey d, LPTSTR dP) {
		dumpKey		= d;
		diskPath = new TCHAR[255];
		lstrcpy(diskPath, dP);		
	}

	WriteRegistersOnDisk() {}

	void setRegisters(DumpKey dk) {
		dumpKey = dk;
	}

	void setDiskPath(LPTSTR dP) {
		diskPath = dP;
	}

	void crtDir(LPTSTR orginPath, LPTSTR newDir) {
		TCHAR newPath[256];
		lstrcpy((LPTSTR)newPath, orginPath);
		lstrcat((LPTSTR)newPath, newDir);
		BOOL result = CreateDirectory((LPTSTR)newPath, nullptr);
		_tprintf(TEXT("Create directory %s  %s !\n"), newPath, (TRUE == result)?"Ok":"Error");
	}
	void writeKeyToFile(HANDLE FileHandle, KeyValue keyValue) {		
		DWORD sizeOfKeyData = sizeof(keyValue.keyValue) + 10;
		TCHAR* buffer = new TCHAR[sizeOfKeyData];		
		buffer[0] = '\0';
		
		switch (keyValue.keyType)
		{
		case REG_SZ:
			lstrcpyA(buffer, TEXT("REG_SZ"));
			break;
		case REG_DWORD:
			lstrcpyA(buffer, TEXT("REG_DWORD"));
			break;
		case REG_QWORD:
			lstrcpyA(buffer, TEXT("REG_QWORD"));
			break;
		case REG_BINARY	:
			lstrcpyA(buffer, TEXT("REG_BINARY"));
			break;
		case REG_EXPAND_SZ:
			lstrcpyA(buffer, TEXT("REG_EXPAND_SZ"));
			break;
		case REG_MULTI_SZ:
			lstrcpyA(buffer, TEXT("REG_MULTI_SZ"));
			break;
		default:
			lstrcpyA(buffer, TEXT("REG_UNKNOWN"));
			break;
		}
		lstrcat(buffer, "\n");
		lstrcat(buffer, (LPSTR)keyValue.keyValue);
		_tprintf(TEXT("Write %s \n"), buffer);
		BOOL result = WriteFile(FileHandle, (LPCVOID)buffer, strlen(buffer), NULL, NULL);
		printf("Ok\n");		
	}

	void writeToDisk() {		
		_tprintf(TEXT("Write at : %s \n"), diskPath);
		crtDir(diskPath, LPTSTR(dumpKey.getRegName()));
		lstrcat(diskPath, LPTSTR(dumpKey.getRegName()));
		lstrcat(diskPath, TEXT("\\"));

		/*for (DWORD i = 0; i < dumpKey.vectorSubKeys.size(); i++) {
			LPTSTR newDirPath = new TCHAR[255];
			LPSTR newDirName = dumpKey.vectorSubKeys.at(i)->getRegName();
			lstrcpy(newDirPath, diskPath);						
			lstrcat(newDirPath, TEXT("\\"));						
			crtDir(newDirPath, newDirName);
		}*/

		
		HANDLE currentFileHandle;

		// Write Keys
		for (DWORD i = 0; i < dumpKey.keyValues.size(); i++) {			
			
			LPSTR filePath = new TCHAR[255];
			LPSTR fileName = dumpKey.keyValues.at(i).keyName;

			lstrcpy(filePath, diskPath);						
			lstrcat(filePath, fileName);
			
			currentFileHandle = CreateFile(filePath, GENERIC_WRITE, 0, NULL, OPEN_ALWAYS, FILE_ATTRIBUTE_NORMAL, NULL);
			_tprintf(TEXT("Create file %s  %s %d\n"), filePath, (INVALID_HANDLE_VALUE == currentFileHandle)? "Error" : " Ok", GetLastError());
			writeKeyToFile(currentFileHandle, dumpKey.keyValues.at(i));			
		}
		for (DWORD i = 0; i < dumpKey.vectorSubKeys.size(); i++) {
			WriteRegistersOnDisk wRd(*dumpKey.vectorSubKeys.at(i), diskPath);
			wRd.writeToDisk();
		}
		
	}
};
int main()
{	

	DumpKey key = DumpKey(TEXT("Dimitrie"), HKEY_CURRENT_USER);
	//DumpKey key = DumpKey(TEXT("System"), HKEY_CURRENT_USER);

	if (ERROR_SUCCESS != key.openKey()) {
		printf("Error open key\n");
	}

	if (ERROR_SUCCESS != key.fillInfoKey()) {		
		printf("Error fill key\n");
	}
	if (ERROR_SUCCESS != key.closeKey()) {
		printf("Error close key\n");
	}
	printf("#########################################\n");
	key.printKeyInfo();
	printf("#########################################\n");
	system("pause");
	WriteRegistersOnDisk wRd(key, TEXT("c:\\Users\\Dimitrie\\Desktop\\CSSO_TEST\\"));
	wRd.writeToDisk();
	system("pause");
    return 0;
}