FROM openjdk:11-slim
LABEL "tjbot"="true"

ARG USER_ID

# The user id is injected at build time
RUN useradd --uid $USER_ID tjbot

# Expose the config dir
VOLUME ["/home/tjbot/config"]

# We should be able to place temp data in there
# Not *strictly* needed though
RUN chown -R tjbot:tjbot /home/tjbot

USER tjbot

WORKDIR /home/tjbot

# Include backend files
COPY TjBot.jar /home/tjbot/TjBot.jar

# Execute the server, needs the path to the configuration file passed as an argument
ENTRYPOINT ["java", "-jar", "/home/tjbot/TjBot.jar"]
