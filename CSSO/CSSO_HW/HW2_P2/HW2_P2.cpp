// HW2_P2.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"
#include <windows.h>
#include <iostream>
//#pragma comment(lib, "user32.lib")
#define PRINT_LOGS TRUE

TCHAR memFileName[] = TEXT("PROCESS_PIDS");
DWORD *lpArrayPids;
DWORD *lpArrayPidsForFree;

BOOL terminateProcessByIdentifier(const DWORD& dwProcessId) {
	HANDLE hProcess = OpenProcess(PROCESS_TERMINATE, FALSE, dwProcessId);

	if (NULL == hProcess) {
#ifdef PRINT_LOGS
		std::cout << "[ERROR] terminateProcessByIdentifier -> OpenProcess " << GetLastError() << std::endl;
#endif
		return FALSE;
	}

	if (FALSE == TerminateProcess(hProcess, 0)) { //TODO: use exit code ?
		return FALSE;
	}

	CloseHandle(hProcess);
	return TRUE;
}

VOID ProcessesFromFileMemory(BOOL terminate = FALSE) {
	HANDLE hMapFile;
	
	DWORD *pDwordMemFileBuf;

	DWORD fileMemArrayPidsSize = 0;

	hMapFile = OpenFileMapping(FILE_MAP_READ, FALSE, memFileName);

	if (NULL == hMapFile) {
#ifdef PRINT_LOGS
		std::cout << "ERROR: OpenFileMapping " << GetLastError() << std::endl;
#endif
		return;
	}

	//Get first element of DWORD FileMapArray to get sizeof pids array
	pDwordMemFileBuf = (DWORD*)MapViewOfFile(hMapFile, FILE_MAP_READ, 0, 0,  sizeof(DWORD));

	if (NULL == pDwordMemFileBuf) {
#ifdef PRINT_LOGS
		std::cout << "[ERROR] : MapViewOfFile " << GetLastError() << std::endl;
#endif
		CloseHandle(hMapFile);
		return;
	}
#ifdef PRINT_LOGS
	std::cout << "Number of pids " <<  pDwordMemFileBuf[0] << std::endl;
#endif
	fileMemArrayPidsSize = pDwordMemFileBuf[0];

	UnmapViewOfFile(pDwordMemFileBuf);

	pDwordMemFileBuf = (DWORD*)MapViewOfFile(hMapFile, FILE_MAP_READ, 0, 0, fileMemArrayPidsSize*sizeof(DWORD));
	if (NULL == pDwordMemFileBuf) {
		std::cout << "[ERROR] : MapViewOfFile " << GetLastError() << std::endl;
		CloseHandle(hMapFile);
		return;
	}
	else {
		lpArrayPids = (DWORD*)malloc((fileMemArrayPidsSize-1)*sizeof(DWORD));
		lpArrayPidsForFree = lpArrayPidsForFree;
	}

	CopyMemory(lpArrayPids, pDwordMemFileBuf, fileMemArrayPidsSize * sizeof(DWORD));

	for (int i = 1; i < fileMemArrayPidsSize; i++) {
#ifdef PRINT_LOGS
		std::cout << "PID " << lpArrayPids[i] << std::endl;
#endif
		if (TRUE == terminate) {
			if (TRUE == terminateProcessByIdentifier(lpArrayPids[i])) {
#ifdef PRINT_LOGS
				std::cout << "PID " << lpArrayPids[i] << " terminated " << std::endl;
#endif
			}
		}
	}

	CloseHandle(hMapFile);
	free((VOID*)lpArrayPidsForFree);
}

BOOL SetPrivilege(HANDLE hAccessToken, LPCTSTR lpszPrivilege, BOOL bEnablePrivilege) {
	LUID luid;
	TOKEN_PRIVILEGES tPrivileges;

	if (FALSE == LookupPrivilegeValue(NULL, lpszPrivilege, &luid)) {
#ifdef PRINT_LOGS
		std::cout << "[ERROR] SetPrivilege -> LookupPrivilegeValue" << GetLastError() << std::endl;
#endif
		return FALSE;
	}

	tPrivileges.PrivilegeCount = 1;
	tPrivileges.Privileges[0].Luid = luid;

	if (bEnablePrivilege) {
		tPrivileges.Privileges[0].Attributes = SE_PRIVILEGE_ENABLED;
	}
	else {
		tPrivileges.Privileges[0].Attributes = 0;
	}

	if (FALSE == AdjustTokenPrivileges(hAccessToken, FALSE, &tPrivileges, sizeof(TOKEN_PRIVILEGES), (PTOKEN_PRIVILEGES)NULL, (PDWORD)NULL)) {
#ifdef PRINT_LOGS
		std::cout << "[ERROR] SetPrivilege -> AdjustTokenPrivileges" << GetLastError() << std::endl;
#endif
		return FALSE;
	}

	if (GetLastError() == ERROR_NOT_ALL_ASSIGNED) {
#ifdef PRINT_LOGS
		std::cout << "The token does not have the specified privilege. \n";
#endif
		return FALSE;
	}
	return TRUE;
}

int main()
{
	HANDLE processToken;	
	if (FALSE == OpenProcessToken(GetCurrentProcess(), TOKEN_ADJUST_PRIVILEGES | TOKEN_QUERY, &processToken)) {
	//if (FALSE == OpenProcessToken(GetCurrentProcess(), TOKEN_ALL_ACCESS, &processToken)) {
#ifdef PRINT_LOGS
		std::cout << "[ERROR] main -> OpenProcessToken " << GetLastError() << std::endl;
#endif
		return 1;
	}
	else {
		if (FALSE == SetPrivilege(processToken, TEXT("SeDebugPrivilege"), TRUE)) {
#ifdef PRINT_LOGS
			std::cout << "[ERROR] main -> SetPrivilege " << GetLastError() << std::endl;
#endif
			system("pause");		
		}
		else {
#ifdef PRINT_LOGS
			std::cout << "Privilege SeDebugPrivilege set for current process\n";
#endif
		}
	}
	
	ProcessesFromFileMemory(TRUE);	
	system("pause");
    return 0;
}

