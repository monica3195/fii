cmake_minimum_required(VERSION 3.3)
project(GPC)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11")

set(SOURCE_FILES hw7.cpp)
add_executable(GPC ${SOURCE_FILES})
target_link_libraries(GPC GL GLU glut)