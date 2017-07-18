# Garrett Scholtes
# Build the java classes
# 
# Tested with jdk1.8.0_131

if [ "$1" = "clean" ]; then
    rm *.class;
    echo "Clean done";
else 
    javac Set1Chapter1.java;
    javac BasicCrypto.java;
    echo "Build done";
fi

if [ "$1" ]; then
    echo "Running..."
    java Set1Chapter1;
fi