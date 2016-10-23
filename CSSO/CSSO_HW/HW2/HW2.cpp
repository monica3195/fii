// HW2.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"
#include <windows.h>
#include <TlHelp32.h>
#include <iostream>
#include <vector>
TCHAR memFileName[] = TEXT("PROCESS_PIDS");

#define PRINT_LOGS TRUE

void listProcesses(){
	PROCESSENTRY32 processEntry;
	HANDLE hCurrentProcess;
	HANDLE hProcessSnapshot;
	HANDLE hFileMappedProcess;	
	DWORD *lpVoidArrayPids;
	DWORD *pDwordMemFileBuf;
	unsigned int counter = 1;

	processEntry.dwSize = sizeof(PROCESSENTRY32);	

	hProcessSnapshot = CreateToolhelp32Snapshot(
		TH32CS_SNAPPROCESS,
		NULL
		);

	if (INVALID_HANDLE_VALUE == hProcessSnapshot) {
#ifdef PRINT_LOGS
		std::cout << "[ERROR] :CreateToolhelp32Snapshot" << GetLastError() << std::endl;
#endif
		return;
	}

	if (FALSE == Process32First(hProcessSnapshot, &processEntry)) {

#ifdef PRINT_LOGS
		std::cout << "[ERROR] : Process32First" << GetLastError() << std::endl;
#endif
		CloseHandle(hProcessSnapshot);
		return;
	}
	else {
		lpVoidArrayPids = (DWORD*)malloc((counter+1)*sizeof(DWORD));
	}
	

	do {
		
		//Get handle for each process
		if (NULL == (hCurrentProcess = OpenProcess(PROCESS_QUERY_LIMITED_INFORMATION, TRUE, processEntry.th32ProcessID))) { // PROCESS_QUERY_INFORMATION access denied
#ifdef PRINT_LOGS
			std::cout << "[ERROR] OpenProcess for PID : " << processEntry.th32ProcessID << " - error : " << GetLastError() << std::endl;
#endif

		}
		else {
			//
			lpVoidArrayPids = (DWORD*)realloc(lpVoidArrayPids, (counter + 1)*sizeof(DWORD));												

			if (processEntry.cntThreads > 2) {

				std::cout << "PID : " << processEntry.th32ProcessID << std::endl;
				std::cout << "Threads : " << processEntry.cntThreads << std::endl;

				lpVoidArrayPids[counter] = processEntry.th32ProcessID;
				counter++;
			}
		}
	
	} while (FALSE != Process32Next(hProcessSnapshot, &processEntry));

	CloseHandle(hProcessSnapshot);
#ifdef PRINT_LOGS
	std::cout << "Numer of processes : " << counter-1 << std::endl;
#endif

	hFileMappedProcess = CreateFileMapping(INVALID_HANDLE_VALUE, NULL, PAGE_READWRITE, 0, sizeof(lpVoidArrayPids), memFileName);
	if (NULL == hFileMappedProcess) {
#ifdef PRINT_LOGS
		std::cout << "[EROOR] CreateFileMapping " << GetLastError() << std::endl;
#endif
		return;
	}
	//Get view of mapped file
	pDwordMemFileBuf = (DWORD*)MapViewOfFile(hFileMappedProcess, FILE_MAP_WRITE, 0, 0, 0);
	if (NULL == pDwordMemFileBuf) {
#ifdef PRINT_LOGS
		std::cout << "[ERROR] MapViewOfFile " << GetLastError() << std::endl;
#endif
		CloseHandle(hFileMappedProcess);
		return;
	}
	lpVoidArrayPids[0] = counter;

	CopyMemory((PVOID)pDwordMemFileBuf, lpVoidArrayPids, counter*sizeof(DWORD));	
#ifdef PRINT_LOGS
	for (int i = 0; i < counter; i++) {
		std::cout << "PID " << lpVoidArrayPids[i] << std::endl;
	}
#endif 		
	UnmapViewOfFile(pDwordMemFileBuf);
	system("pause");
	CloseHandle(hFileMappedProcess);

	free((VOID*)lpVoidArrayPids);
}

int main()
{
	listProcesses();	
	system("pause");
    return 0;
}
