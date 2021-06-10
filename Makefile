PROJ=$(shell dirname $(realpath $(firstword $(MAKEFILE_LIST))))
PROG=rey-trace
LIBS=$(PROJ)/lib/main
BUILD=$(PROJ)/build
SRC=$(PROJ)/src/main
INSTALL=/opt/$(PROG)
CLASSPATH=$(shell echo $(SRC):$(wildcard $(LIBS)/**/*.jar) | sed -Ee 's#\s#:#g')
MAINCLASS=cli.Main

all: $(shell find $(SRC) -type f)
	mkdir -p $(BUILD)
	cd $(BUILD); find $(LIBS)/** -name "*.jar" -exec jar -xf {} \;
	javac -d $(BUILD) -cp $(CLASSPATH) $(SRC)/**/*.java
	echo 'Main-Class: $(MAINCLASS)' > $(BUILD)/manifest.tmp
	cd $(BUILD); jar cfm ../$(PROG).jar manifest.tmp */

docs: $(shell find $(SRC) -type f)
	javadoc -d docs -cp $(CLASSPATH) $(shell find $(SRC) -iname '*.java')

fresh: clean all

install: rey-trace.jar
	mkdir -p $(INSTALL)
	echo '#!/bin/bash' > $(INSTALL)/launch.sh
	echo 'java -jar $(INSTALL)/$(PROG).jar "$$@"' >> $(INSTALL)/launch.sh
	cp $(PROJ)/$(PROG).jar $(INSTALL)/
	chmod +x $(INSTALL)/launch.sh
	ln -sf $(INSTALL)/launch.sh /usr/bin/$(PROG)

clean:
	rm -rf $(BUILD)