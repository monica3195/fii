// HW4.cpp : Defines the entry point for the console application.
//

#define CRTDBG_MAP_ALLOC
#include <stdlib.h>
#include <crtdbg.h>

#include "stdafx.h"
#include <Windows.h>
#include <WinInet.h>
#include <iostream>
#include <fstream>
#include <string.h>
#pragma comment(lib, "wininet.lib")
#pragma warning(disable : 4996)

#define DEBUG TRUE
#define _CRT_SECURE_NO_WARNINGS

typedef struct Credentials {
	char* address;
	char* user;
	char* pass;
	char* filePath;
	char* fileName;
	void free_members() {
		free(user);
		free(address);
		free(pass);
		free(filePath);
	}
}Credentials;

Credentials* ftpCredentials = NULL;

Credentials* RequestHttpCredentials() {
	HINTERNET hInternetOpen = NULL;
	HINTERNET hInternetConnect = NULL;
	DWORD dwTypeOfService = 1;

	hInternetOpen = InternetOpen(
		_T("WIN10"),
		INTERNET_OPEN_TYPE_PRECONFIG,
		NULL,
		NULL,
		0
		);
	if (NULL == hInternetOpen) {
#ifdef DEBUG
		std::cout << "Error InternetOpen() " << GetLastError() << std::endl;
#endif // DEBUG
		return NULL;
	}

#ifdef DEBUG
	std::cout << "InternetOpen ok \n";
#endif // DEBUG


	//############################################################
	hInternetConnect = InternetConnect(hInternetOpen,
		_T("85.122.23.145"),
		INTERNET_DEFAULT_HTTP_PORT,
		NULL,
		NULL,
		INTERNET_SERVICE_HTTP,
		0,
		0
		);
	if (NULL == hInternetConnect) {
#ifdef DEBUG
		std::cout << "Error InternetConnect() " << GetLastError() << std::endl;
#endif // DEBUG
		CloseHandle(hInternetOpen);
		return NULL;
	}
#ifdef DEBUG
	std::cout << "InternetConnect ok \n";
#endif

	//###### Create HTTP Request Packet
	HINTERNET hHttpOpenRequest = HttpOpenRequest(
		hInternetConnect,
		TEXT("GET"),
		TEXT("/~ionut.pirghie/info.txt"),
		_T("HTTP/1.1"),
		NULL,
		NULL,
		INTERNET_FLAG_RELOAD | INTERNET_FLAG_EXISTING_CONNECT,
		dwTypeOfService
		);

	if (NULL == hHttpOpenRequest) {
#ifdef DEBUG
		std::cout << "[ERROR] HttpOpenRequest \n" << GetLastError() << std::endl;
		CloseHandle(hInternetConnect);
		CloseHandle(hInternetOpen);
		return NULL;
#endif

	}
	if (HttpSendRequest(hHttpOpenRequest, NULL, 0, NULL, 0)) {
		char *chArray = nullptr;// (char*)malloc(sizeof(char) * 1024);
		char *token, *origin;
		DWORD flag = 0;
		DWORD dwBytesRead, dwBytesAvailable;
		origin = chArray;
		ftpCredentials = (Credentials*)malloc(sizeof(Credentials));

		if (TRUE == InternetQueryDataAvailable(hHttpOpenRequest, &dwBytesAvailable, 0, 0)) {
			chArray = (char*)malloc(sizeof(char)* dwBytesAvailable);
		}

		while (InternetReadFile(hHttpOpenRequest, chArray, dwBytesAvailable, &dwBytesRead)) {
			if (dwBytesRead < 1) {
				break;
			}
			else {

				//TODO: Refactor ! 
				token = strchr(chArray, '\n');
				// Address
				ftpCredentials->address = (char*)malloc((token - chArray + 1) * sizeof(char));
				strncpy(ftpCredentials->address, chArray, (unsigned int)(token - chArray));
				ftpCredentials->address[token - chArray] = 0;
				std::cout << ftpCredentials->address;
				// File
				chArray = token + 1;
				token = strchr(chArray, '\n');
				ftpCredentials->filePath = (char*)malloc((token - chArray + 1) * sizeof(char));
				strncpy(ftpCredentials->filePath, chArray, (unsigned int)(token - chArray));
				ftpCredentials->filePath[token - chArray] = 0;
				std::cout << ftpCredentials->filePath;
				//
				chArray = token + 1;
				unsigned int reverseCounterUntilDelim = 0;
				while (*token != '\\') {
					token--;
					reverseCounterUntilDelim++;
				}
				ftpCredentials->fileName = (char*)malloc(sizeof(char)* reverseCounterUntilDelim);
				strncpy(ftpCredentials->fileName, token, reverseCounterUntilDelim);
				ftpCredentials->fileName[reverseCounterUntilDelim] = 0;
				// User
				//chArray = token + 1;
				token = strchr(chArray, '\n');
				ftpCredentials->user = (char*)malloc((token - chArray + 1) * sizeof(char));
				strncpy(ftpCredentials->user, chArray, (unsigned int)(token - chArray));
				ftpCredentials->user[token - chArray] = 0;
				std::cout << ftpCredentials->user;
				// Password
				chArray = token + 1;
				token = strchr(chArray, '\n');
				ftpCredentials->pass = (char*)malloc((token - chArray + 1) * sizeof(char));
				strncpy(ftpCredentials->pass, chArray, (unsigned int)(token - chArray));
				ftpCredentials->pass[token - chArray] = 0;
				std::cout << ftpCredentials->pass;
			}

		}
		free(origin);
	}
	return ftpCredentials;
	//Close Internet Handlers
	//############################################################
	if (TRUE != CloseHandle(hInternetConnect)) {
#ifdef DEBUG
		std::cout << "CloseHandle(hInternetOpenURL) " << GetLastError() << std::endl;
#endif // DEBUG
	}
	if (TRUE != CloseHandle(hInternetOpen)) {
#ifdef DEBUG
		std::cout << "CloseHandle(hInternetOpen) " << GetLastError() << std::endl;
#endif // DEBUG

	}
	//############################################################
}

void uploadFTP() {
	HINTERNET hInternetOpen = NULL;
	HINTERNET hInternetConnect = NULL;
	DWORD dwTypeOfService = 1;

	hInternetOpen = InternetOpen(
		_T("WIN10"),
		INTERNET_OPEN_TYPE_PRECONFIG,
		NULL,
		NULL,
		0
		);
	if (NULL == hInternetOpen) {
#ifdef DEBUG
		std::cout << "Error InternetOpen() " << GetLastError() << std::endl;
#endif // DEBUG
		return;
	}

#ifdef DEBUG
	std::cout << "InternetOpen ok \n";
#endif // DEBUG


	//############################################################
	hInternetConnect = InternetConnectA(hInternetOpen,
		ftpCredentials->address,
		INTERNET_DEFAULT_FTP_PORT,
		ftpCredentials->user,
		ftpCredentials->pass,
		INTERNET_SERVICE_FTP,
		0,
		0
		);
	if (NULL == hInternetConnect) {
#ifdef DEBUG
		std::cout << "Error InternetConnect() " << GetLastError() << std::endl;
#endif // DEBUG
		CloseHandle(hInternetOpen);
		return;
	}
#ifdef DEBUG
	std::cout << "InternetConnect ok \n";
#endif
	if (!FtpPutFileA(hInternetConnect, ftpCredentials->filePath, ftpCredentials->fileName, FTP_TRANSFER_TYPE_BINARY, 0))
	{
		std::cout << "Error: FtpPutFileA " << GetLastError() << std::endl;
	}
	else {
		std::cout << "FtpPutFileA" << ftpCredentials->fileName << " ok \n";
	}
	CloseHandle(hInternetConnect);
	CloseHandle(hInternetOpen);
}

int main()
{
	RequestHttpCredentials();
	//ftpCredentials->free_members();
	if (nullptr != ftpCredentials) {
		uploadFTP();
		ftpCredentials->free_members();
		free(ftpCredentials);
	}
	_CrtDumpMemoryLeaks();
	return 0;
}

