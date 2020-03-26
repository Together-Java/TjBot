USER_ID ?= 1000

build: target/TjBot.jar
	echo "Build done!"

target/TjBot.jar: $(shell find -type f -iname '*.java' -or -iname '*sql') pom.xml
	mvn package

clean:
	rm -rf .docker
	mvn clean

.PHONY: clean

.docker:
	mkdir .docker

build-docker: build .docker
	cp target/TjBot.jar .docker
	cp Dockerfile .docker
	(cd .docker && sudo docker build -t tjbot --build-arg USER_ID=$(USER_ID) .)
