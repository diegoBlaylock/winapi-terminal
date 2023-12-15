cmake_minimum_required(VERSION 3.26)
project(temp_c C CXX)

set(CMAKE_C_STANDARD 11)
include_directories(C:/Program\ Files/Java/jdk-21/include)
include_directories(/mnt/c/Program\ Files/Java/jdk-21/include)
include_directories(C:/Program\ Files/Java/jdk-21/include/win32)


include_directories(.)

add_library(temp_c SHARED
        edu_blaylock_jni_impl_TerminalImpl.h
        edu_blaylock_jni_impl_TerminalImpl.cpp
        base_structs.cpp
        event_record.cpp
)
