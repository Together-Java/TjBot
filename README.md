# Together Java Moderation Bot

## About
The intention of this bot is to make moderation easier and provide useful commands to manage the server.

## Contributing
Before you contribute to this Repository consider taking a look at the [CONTRIBUTING.md](https://github.com/Together-Java/TjBot/blob/master/CONTRIBUTING.md).

## Running the bot
1. Copy `src/main/resources/sample-config.yml` and use it to structure your bot config.
2. Start the bot with following arguments: `-c path/to/your/config` 

## Running the bot in docker
### On Linux
1. Run `make build-docker USER_ID=<your wanted user id>` to build the image.
   The user id is needed, as the container does not run as root and needs to read the
   config file you mount in.
2. Run the docker image. The `WORKDIR` of the image is `/home/tjbot/`.
   ```sh
   sudo docker run                          \ # Run it
    --rm                                    \ # Delete it on exit
    -it                                     \ # Wire up std in and out (for testing)
    -v "your config dir:/home/tjbot/config" \ # Mount in your config dir
    tjbot                                   \ # Name of the image
    -c config/sample-config.yml             \ # See 'Running the bot', Step 2.
   ```

## Notice
Please keep your private bot token secret. **Do not include your bot token in any committed or pushed file.**
