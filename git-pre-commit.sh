./mvnw test
if [ $? -eq 0 ]
then
    echo "OK"
    exit 0
else
    echo "KO"
    exit 1
fi