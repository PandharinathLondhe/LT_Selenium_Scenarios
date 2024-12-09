FROM gitpod/workspace-full:latest

# Install Java 11
RUN sudo apt-get update && sudo apt-get install -y openjdk-11-jdk

# Install Maven
RUN sudo apt-get install -y maven

# Set JAVA_HOME environment variable
ENV JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64
ENV PATH=$JAVA_HOME/bin:$PATH

# Install Selenium WebDriver
RUN wget -q -O /tmp/chromedriver.zip https://chromedriver.storage.googleapis.com/91.0.4472.101/chromedriver_linux64.zip && \
    sudo unzip /tmp/chromedriver.zip chromedriver -d /usr/local/bin/ && \
    rm /tmp/chromedriver.zip

# Install Chrome Browser
RUN sudo apt-get install -y google-chrome-stable

# Install Firefox Browser
RUN sudo apt-get install -y firefox

# Note: Internet Explorer is not supported on Linux
