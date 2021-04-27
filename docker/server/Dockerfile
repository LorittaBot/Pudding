# We use Amazon Corretto because OpenJDK only provides Early Access builds
FROM amazoncorretto:15-alpine-jdk

# Copy all required files
COPY files /home/pudding/

# Expose the port
EXPOSE 8080/tcp

# And that's it!
ENTRYPOINT ["/home/pudding/start.sh"]