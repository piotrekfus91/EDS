ADMIN="postgres"
HOST="localhost"
PORT=5432
DB="eds"
USER="eds"
PASS="eds@2"
SCHEMA="ledge_hello_world"

if [ $# -eq 0 ] ; then
    echo "Uruchomienie: $0 [-i|-b]"
    echo "-i -> interactive - pytanie o atrybuty połączenia"
    echo "-b -> batch - atrybuty domyślne dla większości postgresów i domyślnej konfiguracji aplikacji (możesz ustawić je w tym skrypcie)"
    exit 0
fi


if [ $1 == "-i" ] ; then
    echo -n "admin [$ADMIN]: "
    read admin
    if [ ! -z $admin ] ; then ADMIN=$admin ; fi

    echo -n "host [$HOST]: "
    read host
    if [ ! -z $host ] ; then HOST=$host ; fi


    echo -n "port [$PORT]: "
    read port
    if [ ! -z $port ] ; then PORT=$port ; fi

    echo -n "DB [$DB]: "
    read db
    if [ ! -z $db ] ; then DB=$db ; fi

    echo -n "user [$USER]: "
    read user
    if [ ! -z $user ] ; then USER=$user ; fi

    echo -n "PASS [$PASS]: "
    read pass
    if [ ! -z $pass ] ; then PASS=$pass ; fi

    echo -n "schema [$SCHEMA]: "
    read schema
    if [ ! -z $schema ] ; then SCHEMA=$schema ; fi
fi

echo admin: $ADMIN
echo host: $HOST
echo port: $PORT
echo DB: $DB
echo user: $USER
echo pass: $PASS
echo schema: $SCHEMA

echo 

echo -n "Usuwanie istniejących schematów..."
su $ADMIN_USER -c psql 
