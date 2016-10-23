// HW3.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"
#include <windows.h>
#include <random>
#include <iostream>

//#define USE_CRITICAL_SECTION
#ifndef USE_CRITICAL_SECTION
#define USE_EVENTS
#endif

#define NO_OF_NUMS 256

DWORD a, b;
std::random_device rd;
std::mt19937 gen(rd());
std::uniform_int_distribution<> dis(1, 1024);

#ifdef USE_CRITICAL_SECTION
CRITICAL_SECTION critical_section;
#endif

#ifdef USE_EVENTS
HANDLE hEventGenerateNumbers;
HANDLE hEventValidateNumbers;
#endif


DWORD WINAPI generateNumbers(LPVOID lpParams) {
	for (DWORD i = 0; i < NO_OF_NUMS; i++) {
#ifdef USE_CRITICAL_SECTION
		EnterCriticalSection(&critical_section);
#endif
#ifdef USE_EVENTS
		static DWORD hwWaitEventGenerateResult;
		hwWaitEventGenerateResult = WaitForSingleObject(hEventGenerateNumbers, INFINITE);
		switch (hwWaitEventGenerateResult)
		{
		case WAIT_OBJECT_0:
#endif // USE_EVENTS

			a = dis(gen);
			b = 2 * a;
			std::cout << "Generated " << a << " " << b << std::endl;
#ifdef USE_EVENTS
			if (FALSE == ResetEvent(hEventGenerateNumbers)) {
				std::cout << "Failed to ResetEvent hEventGenerateNumbers" << " " << GetLastError() << std::endl;
			}
			if (FALSE == SetEvent(hEventValidateNumbers)) {
				std::cout << "Failed to SetEvent hEventValidateNumbers" << " " << GetLastError() << std::endl;
			}
			break;

		}
#endif // USE_EVENTS

#ifdef USE_CRITICAL_SECTION
		LeaveCriticalSection(&critical_section);
#endif		
	}

	return 0;
}

DWORD WINAPI validateNumbers(LPVOID lpParams) {
	for (DWORD i = 0; i < NO_OF_NUMS; i++) {
#ifdef USE_CRITICAL_SECTION
		EnterCriticalSection(&critical_section);
#endif
#ifdef USE_EVENTS
		static DWORD dwWaitEventResult = WaitForSingleObject(hEventValidateNumbers, INFINITE);
		switch (dwWaitEventResult)
		{
		case WAIT_OBJECT_0:
#endif // USE_EVENTS
			if (b == a * 2) {
				std::cout << "OK " << a << " " << b << std::endl;
			}
			else {
				std::cout << "NOT OK " << a << " " << b << std::endl;
			}
#ifdef USE_EVENTS
			if (FALSE == ResetEvent(hEventValidateNumbers)) {
				std::cout << "Failed to ResetEvent hEventValidateNumbers" << " " << GetLastError() << std::endl;
			}
			if (FALSE == SetEvent(hEventGenerateNumbers)) {
				std::cout << "Failed to SetEvent hEventGenerateNumbers" << " " << GetLastError() << std::endl;
			}
			break;
		default:
			std::cout << "WaitForSingleObject(hEventValidateNumbers returned" << dwWaitEventResult  << " " << GetLastError() << std::endl;
			break;
		}
#endif
#ifdef USE_CRITICAL_SECTION
		LeaveCriticalSection(&critical_section);
#endif
	}
	return 0;
}

int main()
{
	HANDLE hThreadGenerateNumbers, hThreadValidateNumbers;

#ifdef USE_CRITICAL_SECTION
	//Init Critical Section
	InitializeCriticalSectionAndSpinCount(&critical_section, 1000);
#endif
#ifdef USE_EVENTS
#define THREAD_COUNT 2
	hEventGenerateNumbers = CreateEvent(NULL, FALSE, TRUE, TEXT("GenerateNumbersEvent"));
	hEventValidateNumbers = CreateEvent(NULL, FALSE, FALSE, TEXT("ValidateNumbersEvent"));

	if (INVALID_HANDLE_VALUE == hEventGenerateNumbers){
		std::cout << "Failed to create hEventGenerateNumbers " << GetLastError() << std::endl;
		return 1;
	}

	if (INVALID_HANDLE_VALUE == hEventValidateNumbers) {
		std::cout << "Failed to create hEventValidateNumbers " << GetLastError() << std::endl;
		return 2;
	}

	HANDLE hThreadsArray[THREAD_COUNT];
	hThreadsArray[0] = CreateThread(NULL, NULL, generateNumbers, NULL, NULL, NULL);
	hThreadsArray[1] = CreateThread(NULL, NULL, validateNumbers, NULL, NULL, NULL);

	DWORD dwWaitMultipleObjectsResult = WaitForMultipleObjects(THREAD_COUNT, hThreadsArray, TRUE, INFINITE);

	switch (dwWaitMultipleObjectsResult)
	{
		case WAIT_OBJECT_0:
			std::cout << "Done" << std::endl;
			break;

		default:
			std::cout << "Failed WaitForMultipleObjects hTreadsArray " << GetLastError() << std::endl;
			return 1;
	}
	CloseHandle(hEventGenerateNumbers);
	CloseHandle(hEventValidateNumbers);
	CloseHandle(hThreadsArray[0]);
	CloseHandle(hThreadsArray[1]);
#endif // USE_EVENTS
	

#ifdef USE_CRITICAL_SECTION
	hThreadGenerateNumbers = CreateThread(NULL, NULL, generateNumbers, NULL, NULL, NULL);
	hThreadValidateNumbers = CreateThread(NULL, NULL, validateNumbers, NULL, NULL, NULL);
	WaitForSingleObject(hThreadGenerateNumbers, INFINITE);
	WaitForSingleObject(hThreadValidateNumbers, INFINITE);
	DeleteCriticalSection(&critical_section);
#endif
	system("pause");
    return 0;
}

