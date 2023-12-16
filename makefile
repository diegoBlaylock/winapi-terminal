CC = cl
SRC = src/main/c/*.cpp
OUT = bin/terminal.dll
OBJ = build/
INC = /I "$(JAVA_HOME)\include" /I "$(JAVA_HOME)\include\win32" /I src/main/c

# Make sure to add the appropriate windows sdk libs and include to INCLUDE and LIB env-vars


all: build

rebuild: clean build

clean:
	rm -f bin/*
	rm -f build/*

build: $(SRC)
	$(CC) $(INC) /Fo$(OBJ) $(SRC) /link /OUT:$(OUT) /DLL