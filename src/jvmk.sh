# Garrett Scholtes
# Build the java classes
# 
# Tested with jdk1.8.0_131

if [ "$1" = "clean" ]; then
    rm *.class;
    echo "Clean done";
else 
    javac Set1Chapter1.java;
    javac Set1Chapter2.java;
    javac Set1Chapter3.java;
    javac Set1Chapter4.java;
    javac BasicCrypto.java;
    echo "Build done";
fi

if [ "$1" = "run" ]; then
    echo "Running...";
    if [ "$2" = "1" ]; then
        if [ "$3" = "1" ]; then
            java Set1Chapter1;
        elif [ "$3" = "2" ]; then
            java Set1Chapter2;
        elif [ "$3" = "3" ]; then
            java Set1Chapter3;
        elif [ "$3" = "4" ]; then   
            java Set1Chapter4;
        fi
    fi
fi