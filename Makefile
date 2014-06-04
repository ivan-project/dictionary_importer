JCC = javac
JVM = java

ifeq (run,$(firstword $(MAKECMDGOALS)))
  RUN_ARGS := $(wordlist 2,$(words $(MAKECMDGOALS)),$(MAKECMDGOALS))
  $(eval $(RUN_ARGS):;@:)
endif

default: DictExport.class

DictExport.class: DictExport.java
	$(JCC) -classpath ".:mongo-2.10.1.jar" DictExport.java


CLASSES = DictExport.java
MAIN = DictExport

run: $(MAIN).class
	$(JVM) -classpath ".:mongo-2.10.1.jar" $(MAIN) $(RUN_ARGS)

clean: 
	$(RM) *.class
