CC = gcc
CFLAGS = -Wall
LDFLAGS =
OBJS = bfish.o
BINNAME = bfishTest

all : $(OBJS) $(BINNAME)

clean :
	rm -f $(OBJS) $(BINNAME)

bfishTest : $(OBJS) bfishTest.c
	${CC} $(CFLAGS) $(LDFLAGS) $(OBJS) bfishTest.c -o $(BINNAME)

bfish.o : bfish.c

test :

run : 
	./$(BINNAME)
